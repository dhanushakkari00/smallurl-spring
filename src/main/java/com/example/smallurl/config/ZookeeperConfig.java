package com.example.smallurl.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookeeperConfig {
    private static final String ZOOKEEPERR_CONNECTION_STRING = "localhost:2181";

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZOOKEEPERR_CONNECTION_STRING, new ExponentialBackoffRetry(1000, 3));
    client.start();
    return client;

    }
}

