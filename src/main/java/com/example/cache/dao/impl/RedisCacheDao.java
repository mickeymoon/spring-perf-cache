package com.example.cache.dao.impl;

import com.example.cache.dao.CacheDao;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
public class RedisCacheDao implements CacheDao {

    private final StringRedisTemplate redisTemplate;

    public RedisCacheDao(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            throw new KeyNotFoundException(key);
        }
        return value;
    }
}
