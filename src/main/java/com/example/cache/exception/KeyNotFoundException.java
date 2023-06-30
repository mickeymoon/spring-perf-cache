package com.example.cache.exception;

public class KeyNotFoundException extends Exception {

    private String key;

    public KeyNotFoundException(String key) {
        super(key + " not found");
        this.key = key;
    }

}
