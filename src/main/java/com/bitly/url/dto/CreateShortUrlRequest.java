package com.bitly.url.dto;

public class CreateShortUrlRequest {
    private String originalUrl;

    public CreateShortUrlRequest() {
    }

    public CreateShortUrlRequest(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
