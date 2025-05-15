package com.bitly.url.dto;

public class GetRedirectUrlRequest {
    private String url;

    public GetRedirectUrlRequest() {
    }

    public GetRedirectUrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
