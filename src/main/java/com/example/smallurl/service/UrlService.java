package com.example.smallurl.service;

import com.example.smallurl.repository.UrlRepository;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.smallurl.model.Url;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private static final String ZK_COUNTER_PATH = "/shorturl/counter"; // ZooKeeper counter node
    private static final String REDIS_PREFIX = "short_url:"; // Prefix for Redis cache

    @Autowired
    public UrlService(UrlRepository urlRepository, StringRedisTemplate redisTemplate, CuratorFramework curatorFramework) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
        this.curatorFramework = curatorFramework;
        this.lock = new InterProcessMutex(curatorFramework, "/url-shortener-lock");
    }
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private int getNextid() throws Exception{
        if(curatorFramework.checkExists().forPath(ZK_COUNTER_PATH)==null){
            curatorFramework.create().creatingParentsIfNeeded().forPath(ZK_COUNTER_PATH,"0".getBytes());
        }
        byte[] data = curatorFramework.getData().forPath(ZK_COUNTER_PATH);
        int nextId = Integer.parseInt(new String(data,StandardCharsets.UTF_8)) +1;
        curatorFramework.setData().forPath(ZK_COUNTER_PATH,String.valueOf(nextId).getBytes(StandardCharsets.UTF_8));
        return nextId;
    

    }
    private String encodebase62(int num){
        StringBuilder shortcode = new StringBuilder();
        while(num>0){
            shortcode.append(BASE62.charAt(num%62));
            num /=62;
        }
        return shortcode.reverse().toString();
    }
    public String shortenUrl(String originalUrl , Instant expireAt) throws Exception{
        lock.acquire();
        try{
            int nextId = getNextid();
            String shortcode = encodebase62(nextId);
            Url url = new Url(originalUrl,shortcode,expireAt);
            urlRepository.save(url);

            redisTemplate.opsForValue().set(REDIS_PREFIX + shortcode , originalUrl,1,TimeUnit.DAYS);
            return shortcode;
        }finally{
            lock.release();
        }
        }
    public String getOriginalUrl(String shortcode){
        String  cachedUrl = redisTemplate.opsForValue().get(REDIS_PREFIX +shortcode);
        if(cachedUrl!=null){
            return cachedUrl;
        }

        Optional<Url> urlOptional = urlRepository.findByShortcode(shortcode);
        if(urlOptional.isPresent()){
            Url url = urlOptional.get();

            redisTemplate.opsForValue().set(REDIS_PREFIX+shortcode, url.getOriginalUrl(),1,TimeUnit.DAYS);

            return url.getOriginalUrl();
        }
        return null;


    }



}

