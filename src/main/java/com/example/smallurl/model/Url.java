package com.example.smallurl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Document(collection = "short_urls")
public class Url {
    @Id
    private String id;
    private String originalUrl;
    private String shortcode;
    private Instant createdAt;
    private Instant expireAt;

    public Url(String originalUrl , String shortcode, Instant expireAt){
        this.originalUrl = originalUrl;
        this.shortcode = shortcode;
        this.expireAt = expireAt;
        this.createdAt = Instant.now();
    }
    public String getID(){
        return id;
    }
    public String getOriginalUrl(){
        return originalUrl;

    }
    public String getShortCode(){
        return shortcode;
    }
    public Instant getCreatedAt(){
        return createdAt;
    }
    public Instant getExpiresAt(){
        return expireAt;
    }
    public void setOriginalUrl(String originalUrl){
        this.originalUrl = originalUrl;
    }
    public void setExpireAt(Instant expireAt){
        this.expireAt = expireAt;
    }
    public void setShortCode(String shortcode){
        this.shortcode = shortcode;
    }
    @Override
    public String toString() {
        return "ShortUrl{" +
                "id='" + id + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortCode='" + shortcode + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expireAt +
                '}';
    }

}
