package com.example.cache.dao;

import com.example.cache.exception.KeyNotFoundException;

public interface CacheDao {

    public void put(String key, String value);

    public String get(String key) throws KeyNotFoundException;
}
