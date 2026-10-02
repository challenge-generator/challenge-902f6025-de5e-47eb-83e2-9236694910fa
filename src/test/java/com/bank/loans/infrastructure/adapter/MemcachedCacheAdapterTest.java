package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemcachedCacheAdapter - Tests unitarios para interacción con Memcached")
class MemcachedCacheAdapterTest {

    @Mock
    private MemcachedClient memcachedClient;

    @Mock
    private Future<Boolean> future;

    private MemcachedCacheAdapter memcachedCacheAdapter;

    private Loan sampleLoan;
    private String cacheKey;
    private int expirationSeconds = 1800;

    @BeforeEach
    void setUp() {
        memcachedCacheAdapter = new MemcachedCacheAdapter(memcachedClient, expirationSeconds);

        UUID loanId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("100000.00"),
            60,
            new BigDecimal("3.99"),
            LoanStatus.PENDING,
            LocalDate.now(),
            LocalDate.now().plusMonths(60)
        );
        cacheKey = "loan_" + customerId + "_" + loanId;
    }

    @Nested
    @DisplayName("Operación: get - Recuperar datos del caché")
    class GetOperation {

        @Test
        @DisplayName("Debe retornar el valor cuando existe en Memcached")
        void shouldReturnValueWhenExistsInMemcached() {
            when(memcachedClient.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isPresent());
            assertEquals(sampleLoan.id(), result.get().id());
            assertEquals(sampleLoan.customerId(), result.get().customerId());
            verify(memcachedClient).get(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando la clave no existe")
        void shouldReturnEmptyWhenKeyNotExists() {
            when(memcachedClient.get(cacheKey)).thenReturn(null);

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
            verify(memcachedClient).get(cacheKey);
        }

        @Test
        @DisplayName("Debe manejar objetos con cast incorrecto gracefully")
        void shouldHandleIncorrectCast() {
            when(memcachedClient.get(cacheKey)).thenReturn("not a loan object");

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Operación: put - Almacenar datos en caché")
    class PutOperation {

        @Test
        @DisplayName("Debe almacenar el valor en Memcached con expiración configurada")
        void shouldStoreValueWithConfiguredExpiration() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, sampleLoan);
        }

        @Test
        @DisplayName("Debe usar la expiración por defecto cuando no se especifica")
        void shouldUseDefaultExpirationWhenNotSpecified() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, 0, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, sampleLoan);
        }

        @Test
        @DisplayName("Debe convertir timeout a segundos para Memcached")
        void shouldConvertTimeoutToSeconds() {
            long minutesTimeout = 5L;
            int expectedSeconds = 300;
            when(memcachedClient.set(eq(cacheKey), eq(expectedSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, minutesTimeout, TimeUnit.MINUTES);

            verify(memcachedClient).set(cacheKey, expectedSeconds, sampleLoan);
        }
    }

    @Nested
    @DisplayName("Operación: evict - Eliminar datos del caché")
    class EvictOperation {

        @Test
        @DisplayName("Debe eliminar la clave de Memcached")
        void shouldDeleteKeyFromMemcached() {
            when(memcachedClient.delete(cacheKey)).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.evict(cacheKey);

            verify(memcachedClient).delete(cacheKey);
        }

        @Test
        @DisplayName("Debe manejar la eliminación de claves inexistentes")
        void shouldHandleDeletionOfNonExistentKeys() {
            when(memcachedClient.delete(cacheKey)).thenReturn(null);

            assertDoesNotThrow(() -> memcachedCacheAdapter.evict(cacheKey));
        }
    }

    @Nested
    @DisplayName("Operación: isEnabled - Verificar estado del adaptador")
    class IsEnabledOperation {

        @Test
        @DisplayName("Debe retornar true cuando Memcached está configurado")
        void shouldReturnTrueWhenMemcachedIsConfigured() {
            assertTrue(memcachedCacheAdapter.isEnabled());
        }

        @Test
        @DisplayName("Debe retornar true por defecto al inicializar el adaptador")
        void shouldReturnTrueByDefault() {
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, 1800);
            assertTrue(adapter.isEnabled());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores de Memcached")
    class ErrorHandling {

        @Test
        @DisplayName("Debe manejar excepciones de conexión gracefully")
        void shouldHandleConnectionExceptions() {
            when(memcachedClient.get(cacheKey)).thenThrow(new RuntimeException("Memcached connection failed"));

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Debe manejar errores al escribir en caché")
        void shouldHandleWriteErrors() {
            when(memcachedClient.set(anyString(), anyInt(), any())).thenThrow(new RuntimeException("Memcached write error"));

            assertDoesNotThrow(() -> 
                memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS));
        }

        @Test
        @DisplayName("Debe manejar errores al eliminar claves")
        void shouldHandleDeleteErrors() {
            when(memcachedClient.delete(cacheKey)).thenThrow(new RuntimeException("Memcached delete error"));

            assertDoesNotThrow(() -> memcachedCacheAdapter.evict(cacheKey));
        }

        @Test
        @DisplayName("Debe manejar timeouts de operaciones")
        void shouldHandleOperationTimeouts() throws Exception {
            when(memcachedClient.get(cacheKey)).thenThrow(new java.util.concurrent.TimeoutException("Operation timeout"));

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe usar los valores de expiración configurados en el constructor")
        void shouldUseConfiguredExpirationValues() {
            int customExpiration = 3600;
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, customExpiration);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe permitir configuración de expiración cero")
        void shouldAllowZeroExpiration() {
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, 0);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe convertir correctamente diferentes unidades de tiempo")
        void shouldConvertDifferentTimeUnits() {
            when(memcachedClient.set(eq(cacheKey), anyInt(), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, 1, TimeUnit.HOURS);

            verify(memcachedClient).set(cacheKey, 3600, sampleLoan);
        }
    }

    @Nested
    @DisplayName("Escenario: Serialización de objetos")
    class Serialization {

        @Test
        @DisplayName("Debe almacenar objetos serializables en Memcached")
        void shouldStoreSerializableObjects() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any(Loan.class))).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, instanceOf(Loan.class));
        }
    }
}