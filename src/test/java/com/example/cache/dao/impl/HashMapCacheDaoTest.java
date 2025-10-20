package com.example.cache.dao.impl;

import com.example.cache.exception.KeyNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HashMapCacheDaoTest {

    private HashMapCacheDao dao;

    @BeforeEach
    void setUp() {
        dao = new HashMapCacheDao();
    }

    @Test
    void putAndGetShouldReturnStoredValue() throws KeyNotFoundException {
        dao.put("foo", "bar");

        String value = dao.get("foo");

        assertEquals("bar", value, "Stored value should be returned when key exists");
    }

    @Test
    void getMissingKeyShouldThrowException() {
        assertThrows(KeyNotFoundException.class, () -> dao.get("missing"));
    }
}
