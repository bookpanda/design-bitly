package com.bitly.url.dto;

public class CreateShortUrlRequest {
    private String originalUrl;
    private String customCode;

    public CreateShortUrlRequest() {
    }

    public CreateShortUrlRequest(String originalUrl, String customCode) {
        this.originalUrl = originalUrl;
        this.customCode = customCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }
}
