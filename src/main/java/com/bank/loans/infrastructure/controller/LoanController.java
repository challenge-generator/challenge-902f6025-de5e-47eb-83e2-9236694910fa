package com.bank.loans.infrastructure.controller;

import com.bank.loans.application.usecase.GetLoanUseCase;
import com.bank.loans.domain.model.Loan;
import com.bank.loans.infrastructure.cache.CacheManager;
import com.bank.loans.infrastructure.validator.LoanValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/loans")
@Validated
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private static final String CACHE_PREFIX_LOAN = "loan:";
    private static final String CACHE_PREFIX_CUSTOMER_LOANS = "customer:loans:";
    private static final long CACHE_TTL_LOAN = 1800L;
    private static final long CACHE_TTL_CUSTOMER_LOANS = 900L;

    private final GetLoanUseCase getLoanUseCase;
    private final CacheManager cacheManager;
    private final LoanValidator loanValidator;

    @Autowired
    public LoanController(
            GetLoanUseCase getLoanUseCase,
            CacheManager cacheManager,
            LoanValidator loanValidator) {
        this.getLoanUseCase = getLoanUseCase;
        this.cacheManager = cacheManager;
        this.loanValidator = loanValidator;
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(
            @PathVariable @NotNull UUID loanId,
            @RequestHeader(value = "X-Cache-Bypass", defaultValue = "false") boolean bypassCache) {

        logger.info("Solicitando préstamo con ID: {}, bypassCache: {}", loanId, bypassCache);

        String cacheKey = CACHE_PREFIX_LOAN + loanId;

        if (!bypassCache && cacheManager.isCacheEnabled()) {
            Optional<LoanResponse> cached = cacheManager.get(cacheKey, LoanResponse.class);
            if (cached.isPresent()) {
                logger.info("Préstamo {} encontrado en caché", loanId);
                return ResponseEntity.ok(cached.get());
            }
        }

        Optional<Loan> loanOpt = getLoanUseCase.findById(loanId);
        if (loanOpt.isEmpty()) {
            logger.warn("Préstamo no encontrado: {}", loanId);
            return ResponseEntity.notFound().build();
        }

        Loan loan = loanOpt.get();
        LoanResponse response = LoanResponse.fromDomain(loan);

        if (!bypassCache && cacheManager.isCacheEnabled()) {
            cacheManager.put(cacheKey, response, CACHE_TTL_LOAN, TimeUnit.SECONDS);
            logger.info("Préstamo {} almacenado en caché", loanId);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomerId(
            @PathVariable @NotNull UUID customerId,
            @RequestParam(required = false) Integer limit) {

        logger.info("Solicitando préstamos para cliente: {}, limit: {}", customerId, limit);

        String cacheKey = CACHE_PREFIX_CUSTOMER_LOANS + customerId;

        if (cacheManager.isCacheEnabled()) {
            Optional<List<LoanResponse>> cached = cacheManager.get(cacheKey, LoanResponseList.class);
            if (cached.isPresent()) {
                logger.info("Préstamos del cliente {} encontrados en caché", customerId);
                List<LoanResponse> result = cached.get();
                return limit != null && limit < result.size()
                    ? ResponseEntity.ok(result.subList(0, limit))
                    : ResponseEntity.ok(result);
            }
        }

        List<Loan> loans = getLoanUseCase.findByCustomerId(customerId);
        List<LoanResponse> responseList = loans.stream()
            .map(LoanResponse::fromDomain)
            .toList();

        if (cacheManager.isCacheEnabled() && !responseList.isEmpty()) {
            cacheManager.put(cacheKey, responseList, CACHE_TTL_CUSTOMER_LOANS, TimeUnit.SECONDS);
            logger.info("Préstamos del cliente {} almacenados en caché", customerId);
        }

        if (limit != null && limit < responseList.size()) {
            responseList = responseList.subList(0, limit);
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
        logger.info("Creando nuevo préstamo para cliente: {}", request.customerId());

        loanValidator.validateCreateRequest(request);

        Loan loan = getLoanUseCase.createLoan(
            request.customerId(),
            request.principal(),
            request.interestRate(),
            request.termMonths(),
            request.purpose()
        );

        String cacheKey = CACHE_PREFIX_LOAN + loan.id();
        if (cacheManager.isCacheEnabled()) {
            cacheManager.put(cacheKey, LoanResponse.fromDomain(loan), CACHE_TTL_LOAN, TimeUnit.SECONDS);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + request.customerId());
        logger.info("Préstamo creado: {}, caché invalidado para cliente {}", loan.id(), request.customerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(LoanResponse.fromDomain(loan));
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<LoanResponse> updateLoan(
            @PathVariable @NotNull UUID loanId,
            @Valid @RequestBody UpdateLoanRequest request) {

        logger.info("Actualizando préstamo: {}", loanId);

        Optional<Loan> existingOpt = getLoanUseCase.findById(loanId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Loan updated = getLoanUseCase.updateLoan(loanId, request.principal(), request.status());

        String cacheKey = CACHE_PREFIX_LOAN + loanId;
        if (cacheManager.isCacheEnabled()) {
            cacheManager.evict(cacheKey);
            cacheManager.put(cacheKey, LoanResponse.fromDomain(updated), CACHE_TTL_LOAN, TimeUnit.SECONDS);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + existingOpt.get().customerId());
        logger.info("Préstamo actualizado: {}, caché invalidado", loanId);

        return ResponseEntity.ok(LoanResponse.fromDomain(updated));
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable @NotNull UUID loanId) {
        logger.info("Eliminando préstamo: {}", loanId);

        Optional<Loan> existingOpt = getLoanUseCase.findById(loanId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        getLoanUseCase.deleteById(loanId);

        String cacheKey = CACHE_PREFIX_LOAN + loanId;
        if (cacheManager.isCacheEnabled()) {
            cacheManager.evict(cacheKey);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + existingOpt.get().customerId());
        logger.info("Préstamo eliminado: {}, caché invalidado", loanId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health/cache")
    public ResponseEntity<CacheHealthResponse> getCacheHealth() {
        CacheHealthResponse health = new CacheHealthResponse(
            cacheManager.isCacheEnabled(),
            cacheManager.getCacheType(),
            cacheManager.getDefaultTtl()
        );
        return ResponseEntity.ok(health);
    }

    public record CreateLoanRequest(
        @NotNull UUID customerId,
        @NotNull java.math.BigDecimal principal,
        @NotNull java.math.BigDecimal interestRate,
        @NotNull Integer termMonths,
        String purpose
    ) {}

    public record UpdateLoanRequest(
        @NotNull java.math.BigDecimal principal,
        @NotNull String status
    ) {}

    public record LoanResponse(
        UUID id,
        UUID customerId,
        java.math.BigDecimal principal,
        java.math.BigDecimal interestRate,
        Integer termMonths,
        String status,
        String purpose,
        java.time.LocalDate createdAt,
        java.time.LocalDate updatedAt
    ) {
        public static LoanResponse fromDomain(Loan loan) {
            return new LoanResponse(
                loan.id(),
                loan.customerId(),
                loan.principal(),
                loan.interestRate(),
                loan.termMonths(),
                loan.status().name(),
                loan.purpose(),
                loan.createdAt(),
                loan.updatedAt()
            );
        }
    }

    public record LoanResponseList(List<LoanResponse> loans) {}

    public record CacheHealthResponse(boolean enabled, String type, long defaultTtl) {}
}