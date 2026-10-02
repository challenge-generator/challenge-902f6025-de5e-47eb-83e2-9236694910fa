package com.bank.loans.application.usecase;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.port.CachePort;
import com.bank.loans.domain.port.LoanRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GetLoanUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetLoanUseCase.class);
    private static final String CACHE_KEY_PREFIX = "loan:";
    private static final long CACHE_TIMEOUT_MINUTES = 30;

    private final LoanRepositoryPort loanRepository;
    private final CachePort cachePort;

    public GetLoanUseCase(LoanRepositoryPort loanRepository, CachePort cachePort) {
        this.loanRepository = loanRepository;
        this.cachePort = cachePort;
    }

    public Optional<Loan> execute(UUID loanId) {
        String cacheKey = buildCacheKey(loanId);
        logger.debug("Recuperando préstamo con ID: {}", loanId);

        if (cachePort.isEnabled()) {
            Optional<Loan> cachedLoan = cachePort.get(cacheKey, Loan.class);
            if (cachedLoan.isPresent()) {
                logger.info("Préstamo {} encontrado en caché", loanId);
                return cachedLoan;
            }
            logger.debug("Préstamo {} no encontrado en caché, consultando base de datos", loanId);
        }

        Optional<Loan> loan = loanRepository.findById(loanId);

        if (loan.isPresent() && cachePort.isEnabled()) {
            Loan loanData = loan.get();
            cachePort.put(cacheKey, loanData, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} almacenado en caché por {} minutos", loanId, CACHE_TIMEOUT_MINUTES);
        }

        return loan;
    }

    public Optional<Loan> executeForCustomer(UUID customerId, UUID loanId) {
        String cacheKey = buildCacheKeyForCustomer(customerId, loanId);
        logger.debug("Recuperando préstamo {} para cliente {}", loanId, customerId);

        if (cachePort.isEnabled()) {
            Optional<Loan> cachedLoan = cachePort.get(cacheKey, Loan.class);
            if (cachedLoan.isPresent()) {
                logger.info("Préstamo {} para cliente {} encontrado en caché", loanId, customerId);
                return cachedLoan;
            }
        }

        Optional<Loan> loan = loanRepository.findByCustomerIdAndId(customerId, loanId);

        if (loan.isPresent() && cachePort.isEnabled()) {
            Loan loanData = loan.get();
            cachePort.put(cacheKey, loanData, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} para cliente {} almacenado en caché", loanId, customerId);
        }

        return loan;
    }

    public Loan saveLoan(Loan loan) {
        logger.info("Guardando préstamo con ID: {}", loan.id());
        Loan savedLoan = loanRepository.save(loan);

        if (cachePort.isEnabled()) {
            String cacheKey = buildCacheKey(loan.id());
            cachePort.put(cacheKey, savedLoan, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} guardado y almacenado en caché", loan.id());
        }

        return savedLoan;
    }

    public void deleteLoan(UUID loanId) {
        logger.info("Eliminando préstamo con ID: {}", loanId);
        loanRepository.deleteById(loanId);

        if (cachePort.isEnabled()) {
            String cacheKey = buildCacheKey(loanId);
            cachePort.evict(cacheKey);
            logger.info("Préstamo {} eliminado de la base de datos y de caché", loanId);
        }
    }

    private String buildCacheKey(UUID loanId) {
        return CACHE_KEY_PREFIX + loanId.toString();
    }

    private String buildCacheKeyForCustomer(UUID customerId, UUID loanId) {
        return CACHE_KEY_PREFIX + "customer:" + customerId.toString() + ":" + loanId.toString();
    }
}