package com.example.cache.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.*;


@RestController
public class RequestController {
    HashMap<String, String> map = new HashMap<>();

    @PostMapping("/put")
    public void put(@RequestBody KeyValuePair p){
        map.put(p.getKey(), p.getValue());
    }

    @GetMapping("/get")
    public String get(@RequestParam String key){
        if(map.containsKey(key)) return map.get(key);
        return null;
    }
}

class KeyValuePair{
    String key, value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
