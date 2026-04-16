package com.guptaji.springboot_learning.controller;

import com.guptaji.springboot_learning.service.CacheInspectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/cache/v{version}", version = "1")
public class CacheController {

    private static final Logger LOG = LoggerFactory.getLogger(CacheController.class);

    @Autowired
    private CacheInspectionService cacheInspectionService;

    @GetMapping(value = "/getCacheData/{cacheName}")
    public ResponseEntity<?> getCacheData(@PathVariable("cacheName") String cacheName){
        LOG.info("Request received to get cache data {}", cacheName);
        String cacheData = cacheInspectionService.printCacheContents(cacheName);
        return new ResponseEntity<>(cacheData, HttpStatus.OK);
    }
}
