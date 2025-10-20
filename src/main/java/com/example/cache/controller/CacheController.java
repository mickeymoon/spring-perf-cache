package com.example.cache.controller;

import java.util.Map;

import com.example.cache.dao.CacheDao;
import com.example.cache.dto.KeyValuePair;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    private final CacheDao cacheDao;

    public CacheController(@Qualifier("hashMapCacheDao") CacheDao cacheDao) {
        this.cacheDao = cacheDao;
    }

    @PostMapping("/put")
    @ResponseStatus(HttpStatus.CREATED)
    public void put(@RequestBody KeyValuePair p){
        cacheDao.put(p.getKey(), p.getValue());
    }

    @GetMapping("/get")
    public Map<String, String> get(@RequestParam String key) throws KeyNotFoundException {
        return Map.of(key, cacheDao.get(key));
    }

    @ExceptionHandler(KeyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleKeyNotFound(Exception e) {}

}
