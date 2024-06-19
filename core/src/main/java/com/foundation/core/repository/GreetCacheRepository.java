package com.foundation.core.repository;

import com.foundation.core.exception.GreetCacheException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import com.foundation.core.constants.ErrorCode;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class GreetCacheRepository implements CacheRepository<String> {
    @Value("${spring.data.redis.ttl}")
    private long ttl;
    private StringRedisTemplate stringRedisTemplate;
    private ValueOperations<String, String> valueOperations;

    public GreetCacheRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        valueOperations = stringRedisTemplate.opsForValue();
    }

    @Override
    public void put(String key, String value) {
        try {
            valueOperations.set(key, value);
            stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        } catch (GreetCacheException exception) {
            throw new GreetCacheException(ErrorCode.INVALID_UPDATE);
        }
    }

    @Override
    public Optional<String> get(String key) {
        try {
            Boolean isExist = stringRedisTemplate.hasKey(key);
            if(Boolean.TRUE.equals(isExist)){
                return Optional.ofNullable(valueOperations.get(key));
            } else {
                return Optional.empty();
            }
        } catch (GreetCacheException exception) {
            throw new GreetCacheException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public void remove(String key){
        try {
            stringRedisTemplate.delete(key);
        } catch (GreetCacheException exception) {
            throw new GreetCacheException(ErrorCode.COULD_NOT_REMOVE);
        }
    }

}
