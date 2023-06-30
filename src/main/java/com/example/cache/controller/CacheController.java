package com.example.cache.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.cache.dto.KeyValuePair;
import com.example.cache.exception.KeyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {
    private HashMap<String, String> map = new HashMap<>();

    @PostMapping("/put")
    @ResponseStatus(HttpStatus.CREATED)
    public void put(@RequestBody KeyValuePair p){
        map.put(p.getKey(), p.getValue());
    }

    @GetMapping("/get")
    public Map<String, String> get(@RequestParam String key) throws KeyNotFoundException {
        if(map.containsKey(key)) return Map.of(key, map.get(key));
        throw new KeyNotFoundException(key);
    }

    @ExceptionHandler(KeyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleKeyNotFound(Exception e) {}

}
