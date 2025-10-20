package com.example.cache.repository;

import com.example.cache.entity.CacheEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends JpaRepository<CacheEntry, String> {
}
