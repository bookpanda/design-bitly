package com.bitly.url;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitly.url.dto.CreateShortUrlRequest;
import com.bitly.url.dto.CreateShortUrlResponse;

@RestController
@RequestMapping("/urls")
public class UrlController {
    @GetMapping("/")
    public String getRedirectUrl(@RequestParam("url") String url) {
        // For demo, just return the input URL as is (replace with actual logic)
        return "Redirecting to: " + url;
    }

    @PostMapping("/")
    public CreateShortUrlResponse createShortUrl(@RequestBody CreateShortUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        // For demo, just return a fake shortened URL (replace with your logic)
        String shortUrl = "http://short.url/" + originalUrl.hashCode();
        return new CreateShortUrlResponse(shortUrl);
    }
}
