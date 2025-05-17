package com.bitly.url;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;

    @Test
    void testRedirectUrl() throws Exception {
        when(urlService.getRedirectUrl("abc123")).thenReturn("https://example.com");

        mockMvc.perform(get("/urls/abc123")).andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "https://example.com"));
    }

    @Test
    void testCreateShortUrl() throws Exception {
        when(urlService.createShortUrl("https://example.com")).thenReturn("http://localhost/xyz");

        String jsonBody = """
                    {
                      "original_url": "https://example.com"
                    }
                """;

        mockMvc.perform(post("/urls").contentType("application/json").content(jsonBody)).andExpect(status().isOk())
                .andExpect(jsonPath("$.short_url").value("http://localhost/xyz"));
    }
}
