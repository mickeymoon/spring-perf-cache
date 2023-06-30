package com.example.cache.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SumController {

    @GetMapping("/add")
    public Map<String, Integer> add(@RequestParam int n1, @RequestParam int n2){
        return Map.of("sum", n1+n2);
    }
}
