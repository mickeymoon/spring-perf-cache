package com.example.cache.dao.impl;

import com.example.cache.dao.CacheDao;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MySqlCacheDao implements CacheDao {

    private final JdbcTemplate jdbcTemplate;

    public MySqlCacheDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void put(String key, String value) {
        jdbcTemplate.update(
                "INSERT INTO cache (cache_key, cache_value) VALUES (?, ?) " +
                        "ON DUPLICATE KEY UPDATE cache_value = VALUES(cache_value)",
                key,
                value
        );
    }

    @Override
    public String get(String key) throws KeyNotFoundException {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT cache_value FROM cache WHERE cache_key = ?",
                    String.class,
                    key
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new KeyNotFoundException(key);
        }
    }
}
