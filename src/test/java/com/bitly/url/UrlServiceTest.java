package com.bitly.url;

import com.bitly.data.RedisClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UrlServiceTest {
    private UrlRepository urlRepository;
    private RedisClient redisClient;
    private UrlService urlService;

    private final String BASE_URL = "http://localhost/";

    @BeforeEach
    void setUp() {
        urlRepository = mock(UrlRepository.class);
        redisClient = mock(RedisClient.class);
        urlService = new UrlService(urlRepository, redisClient, BASE_URL);
    }

    @Test
    void testCreateShortUrl() {
        String originalUrl = "https://example.com";
        long fakeCounter = 123;

        when(redisClient.increment("url_counter")).thenReturn(fakeCounter);

        String expectedCode = "1z"; // 123 in base62
        String result = urlService.createShortUrl(originalUrl, null);

        assertEquals(BASE_URL + expectedCode, result);
        verify(urlRepository).saveUrlMapping(expectedCode, originalUrl);
    }

    @Test
    void testCreateShortUrlWithCustomCode() {
        String originalUrl = "https://example.com";
        String customCode = "custom123";

        String result = urlService.createShortUrl(originalUrl, customCode);

        assertEquals(BASE_URL + customCode, result);
        verify(urlRepository).saveUrlMapping(customCode, originalUrl);
    }

    @Test
    void testGetRedirectUrl_Success() {
        String code = "abc123";
        String expectedUrl = "https://example.com";

        when(urlRepository.findOriginalUrlByCode(code)).thenReturn(expectedUrl);

        String result = urlService.getRedirectUrl(code);
        assertEquals(expectedUrl, result);
    }

    @Test
    void testGetRedirectUrl_NotFound() {
        String code = "notfound";

        when(urlRepository.findOriginalUrlByCode(code)).thenReturn(null);

        assertThrows(ResponseStatusException.class, () -> urlService.getRedirectUrl(code));
    }
}
