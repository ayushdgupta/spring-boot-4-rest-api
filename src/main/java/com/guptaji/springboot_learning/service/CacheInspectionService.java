package com.guptaji.springboot_learning.service;

public interface CacheInspectionService {

    String printCacheContents(String cacheName);
    void evictCache(String empCacheName, Long empId);
}
