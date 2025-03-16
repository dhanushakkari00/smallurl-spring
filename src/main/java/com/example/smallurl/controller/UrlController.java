package com.example.smallurl.controller;

import com.example.smallurl.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Shorten a long URL with an optional expiration time.
     */
    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestParam String originalUrl,
                                        @RequestParam(required = false) Long expireInSeconds) {
        try {
            Instant expirationTime = (expireInSeconds != null) ? Instant.now().plusSeconds(expireInSeconds) : null;
            String shortCode = urlService.shortenUrl(originalUrl, expirationTime);
            return ResponseEntity.ok().body("Shortened URL: http://localhost:8080/api/redirect/" + shortCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/redirect/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        try {
            String originalUrl = urlService.getOriginalUrl(shortCode);
            if (originalUrl == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: URL not found or expired.");
            }
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
