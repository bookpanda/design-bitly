package com.bitly.url;

import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String getRedirectUrl(String code) {
        String originalUrl = "http://example.com/original-url";

        return originalUrl;
    }

    public String createShortUrl(String originalUrl) {
        int number = 10032;
        String base62 = toBase62(number);

        return "http://localhost:8080/urls/" + base62;
    }

    private String toBase62(int number) {
        StringBuilder sb = new StringBuilder();

        if (number == 0) {
            return "0";
        }

        while (number > 0) {
            int remainder = number % 62;
            sb.append(BASE62.charAt(remainder));
            number /= 62;
        }

        return sb.reverse().toString();
    }
}
