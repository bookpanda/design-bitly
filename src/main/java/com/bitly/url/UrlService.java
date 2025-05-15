package com.bitly.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String baseUrl;

    public UrlService(UrlRepository urlRepository, @Value("${app.base-url}") String baseUrl) {
        this.urlRepository = urlRepository;
        this.baseUrl = baseUrl;
    }

    public String getRedirectUrl(String code) {
        String originalUrl = urlRepository.findOriginalUrlByCode(code);
        return originalUrl;
    }

    public String createShortUrl(String originalUrl) {
        int number = 10032;
        String base62 = toBase62(number);

        urlRepository.saveUrlMapping(base62, originalUrl);

        return baseUrl + base62;
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
