package com.guptaji.springboot_learning.service.impl;

import com.guptaji.springboot_learning.service.CacheInspectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheInspectionServiceImpl implements CacheInspectionService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheInspectionServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public String printCacheContents(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null){
            String cacheData = cache.getNativeCache().toString();
            LOG.info("Cache content is {}", cacheData);
            return cacheData;
        } else {
            LOG.info("Cache {} not present", cacheName);
            return "NO_CACHE";
        }
    }
}
