package com.bitly.url;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bitly.data.RedisClient;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final RedisClient redisClient;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String COUNTER_KEY = "url_counter";
    private final String baseUrl;

    public UrlService(UrlRepository urlRepository, RedisClient redisClient, @Value("${app.base-url}") String baseUrl) {
        this.urlRepository = urlRepository;
        this.redisClient = redisClient;
        this.baseUrl = baseUrl;
    }

    public String getRedirectUrl(String code) {
        String originalUrl = urlRepository.findOriginalUrlByCode(code);
        if (originalUrl == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found");
        }

        return originalUrl;
    }

    public String createShortUrl(String originalUrl, String customCode) {
        if (customCode != null) {
            String existingUrl = urlRepository.findOriginalUrlByCode(customCode);
            if (existingUrl != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Code already exists, please choose another");
            }

            urlRepository.saveUrlMapping(customCode, originalUrl);
            return baseUrl + customCode;
        }

        Long number = redisClient.increment(COUNTER_KEY);
        String base62 = toBase62(number.intValue());

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
