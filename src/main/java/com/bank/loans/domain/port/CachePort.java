package com.bank.loans.domain.port;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface CachePort {
    <T> Optional<T> get(String key, Class<T> type);
    void put(String key, Object value, long timeout, TimeUnit timeUnit);
    void evict(String key);
    boolean isEnabled();
}