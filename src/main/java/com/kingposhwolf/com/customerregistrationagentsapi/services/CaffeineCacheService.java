package com.kingposhwolf.com.customerregistrationagentsapi.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaffeineCacheService {
    private static final Logger logger = LoggerFactory.getLogger(CaffeineCacheService.class);

    private final CacheManager cacheManager;

    @CachePut(value = "tokens", key = "#key")
    public String saveData(String key, String data) {
        logger.info("Saving data to cache with key: {}", key);
        return data;
    }

    @Cacheable(value = "tokens", key = "#key")
    public String getDataByKey(String key) {
        logger.info("Retrieving data from cache with key: {}", key);
        return null;
    }

    @CacheEvict(value = "tokens", key = "#key")
    public void evictData(String key) {
        logger.info("Evicting data from cache with key: {}", key);
    }

    @CacheEvict(value = "tokens", allEntries = true)
    public void evictAllData() {
        logger.info("Evicting all data from tokens cache");
    }

    public boolean isCacheAvailable() {
        return cacheManager.getCache("tokens") != null;
    }
}