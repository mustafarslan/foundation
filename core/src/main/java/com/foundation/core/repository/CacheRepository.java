package com.foundation.core.repository;

import java.util.Optional;

public interface CacheRepository<T> {
    void put(T key, T value);
    Optional<T> get(T key);
    void remove(T key);
}
