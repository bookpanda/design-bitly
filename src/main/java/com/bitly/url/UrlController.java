package com.bitly.url;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping
    public ResponseEntity<Void> getRedirectUrl(@RequestParam("short_code") String code) {
        String originalUrl = "http://original.url/" + code;

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(java.net.URI.create(originalUrl));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping
    public CreateShortUrlResponse createShortUrl(@RequestBody CreateShortUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        // For demo, just return a fake shortened URL (replace with your logic)
        String shortUrl = "http://short.url/" + originalUrl.hashCode();
        return new CreateShortUrlResponse(shortUrl);
    }
}
