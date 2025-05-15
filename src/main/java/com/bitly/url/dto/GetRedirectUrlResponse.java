package com.bitly.url.dto;

public class GetRedirectUrlResponse {
    private String redirectUrl;

    public GetRedirectUrlResponse() {
    }

    public GetRedirectUrlResponse(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
