package com.bitly.url.dto;

public class CreateShortUrlResponse {
    private String shortUrl;

    public CreateShortUrlResponse() {
    }

    public CreateShortUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
