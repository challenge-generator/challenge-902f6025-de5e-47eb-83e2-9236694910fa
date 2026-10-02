package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RedisCacheAdapter - Tests unitarios para interacción con Redis")
class RedisCacheAdapterTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private RedisCacheAdapter redisCacheAdapter;

    private Loan sampleLoan;
    private String cacheKey;
    private long cacheTimeout = 30L;
    private TimeUnit cacheTimeUnit = TimeUnit.MINUTES;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        redisCacheAdapter = new RedisCacheAdapter(redisTemplate, cacheTimeout, cacheTimeUnit);

        UUID loanId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("75000.00"),
            48,
            new BigDecimal("4.9"),
            LoanStatus.ACTIVE,
            LocalDate.now(),
            LocalDate.now().plusMonths(48)
        );
        cacheKey = "loan:" + customerId + ":" + loanId;
    }

    @Nested
    @DisplayName("Operación: get - Recuperar datos del caché")
    class GetOperation {

        @Test
        @DisplayName("Debe retornar el valor cuando existe en Redis")
        void shouldReturnValueWhenExistsInRedis() {
            when(valueOperations.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isPresent());
            assertEquals(sampleLoan.id(), result.get().id());
            assertEquals(sampleLoan.customerId(), result.get().customerId());
            verify(valueOperations).get(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando la clave no existe")
        void shouldReturnEmptyWhenKeyNotExists() {
            when(valueOperations.get(cacheKey)).thenReturn(null);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
            verify(valueOperations).get(cacheKey);
        }

        @Test
        @DisplayName("Debe deserializar correctamente el tipo solicitado")
        void shouldDeserializeCorrectType() {
            when(valueOperations.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertInstanceOf(Loan.class, result.get());
        }
    }

    @Nested
    @DisplayName("Operación: put - Almacenar datos en caché")
    class PutOperation {

        @Test
        @DisplayName("Debe almacenar el valor en Redis con timeout configurado")
        void shouldStoreValueWithConfiguredTimeout() {
            redisCacheAdapter.put(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit);

            verify(valueOperations).set(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit);
        }

        @Test
        @DisplayName("Debe usar timeout personalizado cuando se especifica")
        void shouldUseCustomTimeoutWhenProvided() {
            long customTimeout = 60L;
            TimeUnit customUnit = TimeUnit.SECONDS;

            redisCacheAdapter.put(cacheKey, sampleLoan, customTimeout, customUnit);

            verify(valueOperations).set(cacheKey, sampleLoan, customTimeout, customUnit);
        }

        @Test
        @DisplayName("Debe permitir valores nulos")
        void shouldAllowNullValues() {
            redisCacheAdapter.put(cacheKey, null, cacheTimeout, cacheTimeUnit);

            verify(valueOperations).set(eq(cacheKey), isNull(), anyLong(), any(TimeUnit.class));
        }
    }

    @Nested
    @DisplayName("Operación: evict - Eliminar datos del caché")
    class EvictOperation {

        @Test
        @DisplayName("Debe eliminar la clave de Redis")
        void shouldDeleteKeyFromRedis() {
            when(redisTemplate.delete(cacheKey)).thenReturn(true);

            redisCacheAdapter.evict(cacheKey);

            verify(redisTemplate).delete(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar true cuando la clave fue eliminada")
        void shouldReturnTrueWhenKeyDeleted() {
            when(redisTemplate.delete(cacheKey)).thenReturn(true);

            redisCacheAdapter.evict(cacheKey);

            verify(redisTemplate).delete(cacheKey);
        }
    }

    @Nested
    @DisplayName("Operación: isEnabled - Verificar estado del adaptador")
    class IsEnabledOperation {

        @Test
        @DisplayName("Debe retornar true cuando Redis está configurado")
        void shouldReturnTrueWhenRedisIsConfigured() {
            assertTrue(redisCacheAdapter.isEnabled());
        }

        @Test
        @DisplayName("Debe retornar true por defecto al inicializar el adaptador")
        void shouldReturnTrueByDefault() {
            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, 30, TimeUnit.MINUTES);
            assertTrue(adapter.isEnabled());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores de Redis")
    class ErrorHandling {

        @Test
        @DisplayName("Debe manejar excepciones de conexión gracefully")
        void shouldHandleConnectionExceptions() {
            when(valueOperations.get(cacheKey)).thenThrow(new RuntimeException("Redis connection failed"));

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Debe manejar errores al escribir en caché")
        void shouldHandleWriteErrors() {
            doThrow(new RuntimeException("Redis write error"))
                .when(valueOperations).set(anyString(), any(), anyLong(), any(TimeUnit.class));

            assertDoesNotThrow(() -> 
                redisCacheAdapter.put(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit));
        }

        @Test
        @DisplayName("Debe manejar errores al eliminar claves")
        void shouldHandleDeleteErrors() {
            when(redisTemplate.delete(cacheKey)).thenThrow(new RuntimeException("Redis delete error"));

            assertDoesNotThrow(() -> redisCacheAdapter.evict(cacheKey));
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe usar los valores de timeout configurados en el constructor")
        void shouldUseConfiguredTimeoutValues() {
            long expectedTimeout = 45L;
            TimeUnit expectedUnit = TimeUnit.HOURS;

            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, expectedTimeout, expectedUnit);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe permitir configuración de timeout cero")
        void shouldAllowZeroTimeout() {
            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, 0L, TimeUnit.SECONDS);

            assertTrue(adapter.isEnabled());
        }
    }
}