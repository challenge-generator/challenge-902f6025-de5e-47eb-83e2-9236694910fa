package com.bank.loans.application.usecase;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import com.bank.loans.domain.port.CachePort;
import com.bank.loans.domain.port.LoanRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetLoanUseCase - Tests unitarios para la lógica de caché")
class GetLoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @Mock
    private CachePort cachePort;

    @InjectMocks
    private GetLoanUseCase getLoanUseCase;

    private Loan sampleLoan;
    private UUID loanId;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("50000.00"),
            36,
            new BigDecimal("5.5"),
            LoanStatus.ACTIVE,
            LocalDate.now(),
            LocalDate.now().plusMonths(36)
        );
    }

    @Nested
    @DisplayName("Escenario: Caché habilitada y datos en caché")
    class CacheEnabledWithHit {

        @Test
        @DisplayName("Debe retornar datos del caché cuando están disponibles")
        void shouldReturnFromCacheWhenAvailable() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            assertEquals(loanId, result.get().id());
            assertEquals(customerId, result.get().customerId());
            verify(cachePort).get(anyString(), eq(Loan.class));
            verify(loanRepositoryPort, never()).findById(any());
            verify(loanRepositoryPort, never()).findByCustomerIdAndId(any(), any());
        }

        @Test
        @DisplayName("No debe invocar al repositorio cuando hay acierto de caché")
        void shouldNotCallRepositoryOnCacheHit() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(loanRepositoryPort, times(0)).findById(any());
            verify(loanRepositoryPort, times(0)).findByCustomerIdAndId(any(), any());
        }
    }

    @Nested
    @DisplayName("Escenario: Caché habilitada pero sin datos en caché")
    class CacheEnabledWithMiss {

        @Test
        @DisplayName("Debe consultar el repositorio cuando no hay datos en caché")
        void shouldQueryRepositoryOnCacheMiss() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            assertEquals(loanId, result.get().id());
            verify(loanRepositoryPort).findByCustomerIdAndId(customerId, loanId);
        }

        @Test
        @DisplayName("Debe almacenar en caché el resultado del repositorio")
        void shouldPopulateCacheOnRepositoryFetch() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(cachePort).put(anyString(), eq(sampleLoan), anyLong(), any(TimeUnit.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Caché deshabilitada")
    class CacheDisabled {

        @Test
        @DisplayName("Debe consultar directamente el repositorio cuando la caché está deshabilitada")
        void shouldBypassCacheWhenDisabled() {
            when(cachePort.isEnabled()).thenReturn(false);
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            verify(cachePort, never()).get(anyString(), any(Class.class));
            verify(loanRepositoryPort).findByCustomerIdAndId(customerId, loanId);
        }
    }

    @Nested
    @DisplayName("Escenario: Préstamo no encontrado")
    class LoanNotFound {

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando el préstamo no existe")
        void shouldReturnEmptyWhenLoanNotFound() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.empty());

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isEmpty());
            verify(cachePort, never()).put(anyString(), any(), anyLong(), any());
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de parámetros")
    class ParameterValidation {

        @Test
        @DisplayName("Debe lanzar excepción cuando el loanId es nulo")
        void shouldThrowExceptionWhenLoanIdIsNull() {
            assertThrows(IllegalArgumentException.class, 
                () -> getLoanUseCase.execute(null, customerId));
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando el customerId es nulo")
        void shouldThrowExceptionWhenCustomerIdIsNull() {
            assertThrows(IllegalArgumentException.class, 
                () -> getLoanUseCase.execute(loanId, null));
        }
    }

    @Nested
    @DisplayName("Escenario: Generación de clave de caché")
    class CacheKeyGeneration {

        @Test
        @DisplayName("Debe generar una clave de caché única por loan y customer")
        void shouldGenerateUniqueCacheKey() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(cachePort).get(contains(loanId.toString()), eq(Loan.class));
            verify(cachePort).get(contains(customerId.toString()), eq(Loan.class));
        }
    }
}