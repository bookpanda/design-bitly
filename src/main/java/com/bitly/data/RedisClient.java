package com.bitly.data;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisClient {
    private final StringRedisTemplate redisTemplate;

    public RedisClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(String key, String value, Duration ttl) {
        if (ttl == null) {
            redisTemplate.opsForValue().set(key, value);
            return;
        }
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }
}
