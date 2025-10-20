package com.example.cache.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cache")
public class CacheEntry {

    @Id
    @Column(name = "key", nullable = false, updatable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    protected CacheEntry() {
        // Default constructor required by JPA
    }

    public CacheEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
