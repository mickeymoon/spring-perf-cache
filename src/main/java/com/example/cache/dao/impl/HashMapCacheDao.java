package com.example.cache.dao.impl;

import com.example.cache.dao.CacheDao;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HashMapCacheDao implements CacheDao {

    private Map<String, String> map = new HashMap<>();

    @Override
    public void put(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        if(map.containsKey(key)) return map.get(key);
        throw new KeyNotFoundException(key);
    }
}
