package com.bank.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import com.bank.loans.infrastructure.config.CacheProperties;
import com.bank.loans.application.port.out.CachePort;
import com.bank.loans.application.service.LoanService;
import com.bank.loans.infrastructure.adapter.RedisCacheAdapter;
import com.bank.loans.infrastructure.adapter.MemcachedCacheAdapter;
import com.bank.loans.infrastructure.cache.CacheManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties(CacheProperties.class)
public class LoansApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoansApplication.class);
    private final CacheProperties cacheProperties;
    private final CachePort redisCachePort;
    private final CachePort memcachedCachePort;
    private final CacheManager cacheManager;
    private final LoanService loanService;

    public LoansApplication(CacheProperties cacheProperties,
                           RedisCacheAdapter redisCacheAdapter,
                           MemcachedCacheAdapter memcachedCacheAdapter,
                           CacheManager cacheManager,
                           LoanService loanService) {
        this.cacheProperties = cacheProperties;
        this.redisCachePort = redisCacheAdapter;
        this.memcachedCachePort = memcachedCacheAdapter;
        this.cacheManager = cacheManager;
        this.loanService = loanService;
    }

    @PostConstruct
    public void init() {
        logger.info("Initializing cache configurations...");
        cacheManager.initializeCacheStrategies(
            List.of(redisCachePort, memcachedCachePort),
            cacheProperties.getDefaultTtlSeconds()
        );
        logger.info("Cache strategies initialized with Redis and Memcached");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        try {
            var context = SpringApplication.run(LoansApplication.class, args);
            logger.info("Application started successfully");

            // Verificación de conectividad con caches
            var redisCache = context.getBean(RedisCacheAdapter.class);
            var memcachedCache = context.getBean(MemcachedCacheAdapter.class);

            if (!redisCache.isConnected()) {
                logger.warn("Redis cache connection failed - falling back to Memcached");
            }

            if (!memcachedCache.isConnected()) {
                logger.warn("Memcached connection failed - performance may be degraded");
            }

            // Validación de propiedades de caché
            if (cacheProperties.getDefaultTtlSeconds() <= 0) {
                throw new IllegalStateException("Cache TTL must be positive");
            }

            logger.info("Cache configuration validated successfully");
        } catch (Exception e) {
            logger.error("Application failed to start", e);
            System.exit(1);
        }
    }
}