package com.bank.loans.infrastructure.cache;

import com.bank.loans.domain.port.CachePort;
import com.bank.loans.infrastructure.adapter.MemcachedCacheAdapter;
import com.bank.loans.infrastructure.adapter.RedisCacheAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);
    private static final String CACHE_TYPE_REDIS = "redis";
    private static final String CACHE_TYPE_MEMCACHED = "memcached";

    private final RedisCacheAdapter redisCacheAdapter;
    private final MemcachedCacheAdapter memcachedCacheAdapter;

    @Value("${cache.type:redis}")
    private String cacheType;

    @Value("${cache.default-ttl:1800}")
    private long defaultTtl;

    @Value("${cache.enabled:true}")
    private boolean cacheEnabled;

    @Autowired
    public CacheManager(
            @Autowired(required = false) RedisCacheAdapter redisCacheAdapter,
            @Autowired(required = false) MemcachedCacheAdapter memcachedCacheAdapter) {
        this.redisCacheAdapter = redisCacheAdapter;
        this.memcachedCacheAdapter = memcachedCacheAdapter;
    }

    private CachePort getActiveCache() {
        if (!cacheEnabled) {
            logger.debug("Caché deshabilitado globalmente");
            return null;
        }

        if (CACHE_TYPE_REDIS.equalsIgnoreCase(cacheType)) {
            if (redisCacheAdapter != null && redisCacheAdapter.isEnabled()) {
                logger.debug("Usando Redis como proveedor de caché");
                return redisCacheAdapter;
            }
            logger.warn("Redis configurado pero no disponible, cayendo a Memcached");
        }

        if (CACHE_TYPE_MEMCACHED.equalsIgnoreCase(cacheType) || 
            (CACHE_TYPE_REDIS.equalsIgnoreCase(cacheType) && !redisCacheAdapter.isEnabled())) {
            if (memcachedCacheAdapter != null && memcachedCacheAdapter.isEnabled()) {
                logger.debug("Usando Memcached como proveedor de caché");
                return memcachedCacheAdapter;
            }
        }

        logger.warn("Ningún proveedor de caché disponible, operando sin caché");
        return null;
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return Optional.empty();
        }

        try {
            Optional<T> result = cache.get(key, type);
            if (result.isPresent()) {
                logger.debug("Cache HIT para clave: {}", key);
            } else {
                logger.debug("Cache MISS para clave: {}", key);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error al obtener del cache la clave {}: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return;
        }

        try {
            cache.put(key, value, timeout, timeUnit);
            logger.debug("Guardado en cache clave: {} con TTL: {} {}", key, timeout, timeUnit);
        } catch (Exception e) {
            logger.error("Error al guardar en cache la clave {}: {}", key, e.getMessage());
        }
    }

    public void put(String key, Object value) {
        put(key, value, defaultTtl, TimeUnit.SECONDS);
    }

    public void evict(String key) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return;
        }

        try {
            cache.evict(key);
            logger.debug("Evict del cache para clave: {}", key);
        } catch (Exception e) {
            logger.error("Error al hacer evict del cache para la clave {}: {}", key, e.getMessage());
        }
    }

    public void evictPattern(String pattern) {
        logger.info("Evict por patrón {} no implementado en esta versión", pattern);
    }

    public <T> T getOrCompute(String key, Class<T> type, Supplier<T> computeFunction, long timeout, TimeUnit timeUnit) {
        Optional<T> cached = get(key, type);
        if (cached.isPresent()) {
            return cached.get();
        }

        T value = computeFunction.get();
        if (value != null) {
            put(key, value, timeout, timeUnit);
        }
        return value;
    }

    public <T> T getOrCompute(String key, Class<T> type, Supplier<T> computeFunction) {
        return getOrCompute(key, type, computeFunction, defaultTtl, TimeUnit.SECONDS);
    }

    public String getCacheType() {
        return cacheType;
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public long getDefaultTtl() {
        return defaultTtl;
    }
}