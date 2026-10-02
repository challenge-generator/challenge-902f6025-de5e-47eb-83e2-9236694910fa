package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.port.CachePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheAdapter implements CachePort {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAdapter.class);
    private static final String CACHE_PREFIX = "loan:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final boolean cacheEnabled;

    public RedisCacheAdapter(RedisTemplate<String, Object> redisTemplate,
                            ObjectMapper objectMapper,
                            boolean cacheEnabled) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Redis get for key: {}", key);
            return Optional.empty();
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object value = redisTemplate.opsForValue().get(cacheKey);
            
            if (value == null) {
                logger.debug("Cache miss for key: {}", cacheKey);
                return Optional.empty();
            }

            logger.debug("Cache hit for key: {}", cacheKey);
            
            if (type.isInstance(value)) {
                return Optional.of(type.cast(value));
            }

            String jsonValue = convertToJson(value);
            T deserialized = objectMapper.readValue(jsonValue, type);
            return Optional.of(deserialized);
            
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize cached value for key: {}", key, e);
            evict(key);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error retrieving from Redis cache for key: {}", key, e);
            return Optional.empty();
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Redis put for key: {}", key);
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object valueToStore = serializeValue(value);
            redisTemplate.opsForValue().set(cacheKey, valueToStore, timeout, timeUnit);
            logger.debug("Cached value with key: {} for {} {}", 
                cacheKey, timeout, timeUnit.name().toLowerCase());
        } catch (Exception e) {
            logger.error("Error storing in Redis cache for key: {}", key, e);
        }
    }

    @Override
    public void evict(String key) {
        if (!isEnabled()) {
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            Boolean deleted = redisTemplate.delete(cacheKey);
            if (Boolean.TRUE.equals(deleted)) {
                logger.debug("Evicted cache key: {}", cacheKey);
            } else {
                logger.debug("Cache key not found for eviction: {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error evicting from Redis cache for key: {}", key, e);
        }
    }

    @Override
    public boolean isEnabled() {
        return cacheEnabled;
    }

    private String buildCacheKey(String key) {
        return CACHE_PREFIX + key;
    }

    private Object serializeValue(Object value) {
        if (value instanceof String || value instanceof Number || 
            value instanceof Boolean || value == null) {
            return value;
        }
        
        try {
            String json = objectMapper.writeValueAsString(value);
            return json;
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize value, storing as-is: {}", value.getClass().getName());
            return value;
        }
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        if (value instanceof String) {
            return (String) value;
        }
        return objectMapper.writeValueAsString(value);
    }

    public void evictAllByPattern(String pattern) {
        if (!isEnabled()) {
            return;
        }
        
        try {
            String searchPattern = CACHE_PREFIX + pattern;
            var keys = redisTemplate.keys(searchPattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                logger.info("Evicted {} cache entries matching pattern: {}", keys.size(), searchPattern);
            }
        } catch (Exception e) {
            logger.error("Error evicting cache entries by pattern: {}", pattern, e);
        }
    }
}