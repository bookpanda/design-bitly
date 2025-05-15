package com.bitly.url;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bitly.url.dto.CreateShortUrlRequest;
import com.bitly.url.dto.CreateShortUrlResponse;
import com.bitly.url.dto.GetRedirectUrlResponse;

@RestController
@RequestMapping("/urls")
public class UrlController {
    @GetMapping
    public GetRedirectUrlResponse getRedirectUrl(@RequestParam("short_code") String code) {
        // For demo, just return the input URL as is (replace with actual logic)
        return new GetRedirectUrlResponse("http://original.url/" + code);
    }

    @PostMapping
    public CreateShortUrlResponse createShortUrl(@RequestBody CreateShortUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        // For demo, just return a fake shortened URL (replace with your logic)
        String shortUrl = "http://short.url/" + originalUrl.hashCode();
        return new CreateShortUrlResponse(shortUrl);
    }
}
