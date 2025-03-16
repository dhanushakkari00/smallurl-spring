package com.example.smallurl.service;

import com.example.smallurl.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;



@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final StringRedisTemplate redisTemplate;
    private final CuratorFramework curatorFramework;
    private final InterProcessMutex lock;
    
    @Autowired
    public UrlService(UrlRepository urlRepository, StringRedisTemplate redisTemplate,CuratorFramework curatorFramework){
        this.curatorFramework = curatorFramework;
        this.redisTemplate = redisTemplate;
        this.lock = new InterProcessMutex(curatorFramework, "/url-shortener-lock");
        this.urlRepository = urlRepository;
    }
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;
    private final Random random = new Random();
    
}

