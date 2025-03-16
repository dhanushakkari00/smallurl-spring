package com.example.smallurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.smallurl.repository") // Ensure repository scanning
public class SmallurlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallurlApplication.class, args);
    }
}
