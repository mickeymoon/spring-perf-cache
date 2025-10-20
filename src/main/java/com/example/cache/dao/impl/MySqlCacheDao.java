package com.example.cache.dao.impl;

import com.example.cache.dao.CacheDao;
import com.example.cache.entity.CacheEntry;
import com.example.cache.exception.KeyNotFoundException;
import com.example.cache.repository.CacheRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MySqlCacheDao implements CacheDao {

    private final CacheRepository cacheRepository;

    public MySqlCacheDao(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    @Override
    public void put(String key, String value) {
        CacheEntry entry = cacheRepository.findById(key)
                .map(existing -> {
                    existing.setValue(value);
                    return existing;
                })
                .orElseGet(() -> new CacheEntry(key, value));
        cacheRepository.save(entry);
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        return cacheRepository.findById(key)
                .map(CacheEntry::getValue)
                .orElseThrow(() -> new KeyNotFoundException(key));
    }
}
