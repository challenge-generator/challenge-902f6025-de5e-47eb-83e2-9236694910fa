package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.port.CachePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class MemcachedCacheAdapter implements CachePort {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedCacheAdapter.class);
    private static final String CACHE_PREFIX = "loan:";

    private final MemcachedClient memcachedClient;
    private final ObjectMapper objectMapper;
    private final boolean cacheEnabled;

    public MemcachedCacheAdapter(MemcachedClient memcachedClient,
                                ObjectMapper objectMapper,
                                boolean cacheEnabled) {
        this.memcachedClient = memcachedClient;
        this.objectMapper = objectMapper;
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Memcached get for key: {}", key);
            return Optional.empty();
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object cached = memcachedClient.get(cacheKey);
            
            if (cached == null) {
                logger.debug("Cache miss for key: {}", cacheKey);
                return Optional.empty();
            }

            logger.debug("Cache hit for key: {}", cacheKey);
            
            String jsonValue = cached.toString();
            T deserialized = objectMapper.readValue(jsonValue, type);
            return Optional.of(deserialized);
            
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize cached value for key: {}", key, e);
            evict(key);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error retrieving from Memcached for key: {}", key, e);
            return Optional.empty();
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Memcached put for key: {}", key);
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            String jsonValue = objectMapper.writeValueAsString(value);
            int expiration = calculateExpiration(timeout, timeUnit);
            memcachedClient.set(cacheKey, expiration, jsonValue);
            logger.debug("Cached value with key: {} for {} {}", 
                cacheKey, timeout, timeUnit.name().toLowerCase());
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize value for caching, key: {}", key, e);
        } catch (Exception e) {
            logger.error("Error storing in Memcached for key: {}", key, e);
        }
    }

    @Override
    public void evict(String key) {
        if (!isEnabled()) {
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            memcachedClient.delete(cacheKey);
            logger.debug("Evicted cache key: {}", cacheKey);
        } catch (Exception e) {
            logger.error("Error evicting from Memcached for key: {}", key, e);
        }
    }

    @Override
    public boolean isEnabled() {
        return cacheEnabled;
    }

    private String buildCacheKey(String key) {
        return CACHE_PREFIX + key;
    }

    private int calculateExpiration(long timeout, TimeUnit timeUnit) {
        long seconds = timeUnit.toSeconds(timeout);
        if (seconds > Integer.MAX_VALUE) {
            logger.warn("Expiration time {} exceeds Memcached limit, using max value", seconds);
            return Integer.MAX_VALUE;
        }
        return (int) seconds;
    }

    public void evictAllByPattern(String pattern) {
        if (!isEnabled()) {
            return;
        }
        
        logger.info("Memcached does not support pattern-based eviction, manual iteration required");
        logger.debug("Pattern-based eviction requested: {}{}", CACHE_PREFIX, pattern);
    }

    public boolean isServerAvailable() {
        try {
            return !memcachedClient.getAvailableServers().isEmpty();
        } catch (Exception e) {
            logger.warn("Failed to check Memcached server availability", e);
            return false;
        }
    }
}