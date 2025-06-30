package com.mdl.student.ratelimiter;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RedisRateLimiter {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;

    // 1 minute window
    private static final int TIME_WINDOW_SECONDS = 60;

    // Max 5 requests per client per minute
    private static final int MAX_REQUESTS = 5;

    private Jedis jedis;

    public RedisRateLimiter() {
        this.jedis = new Jedis(REDIS_HOST, REDIS_PORT); // Connect to Redis
    }

    public boolean isRateLimited(String clientId) {
        // Each client has a unique key (rate_limit:clientId) stored in Redis to track their request count.
        // Unique key for each client
        String key = "rate_limit:" + clientId;

        // Get the current request count for the client
        String requestCount = jedis.get(key);

        if (requestCount == null) {
            // If key doesn't exist, it's the first request. Set the count to 1 and expire the key in 1 minute.
            jedis.setex(key, TIME_WINDOW_SECONDS, "1");
            return false; // Not rate limited
        }

        int currentCount = Integer.parseInt(requestCount);

        if (currentCount < MAX_REQUESTS) {
            // Increment the count for this client
            jedis.incr(key);
            return false; // Not rate limited
        } else {
            // Rate limit exceeded
            return true; // Rate limited
        }
    }
}