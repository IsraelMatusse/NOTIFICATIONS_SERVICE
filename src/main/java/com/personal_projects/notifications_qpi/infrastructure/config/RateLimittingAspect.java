package com.personal_projects.notifications_qpi.infrastructure.config;

import com.personal_projects.notifications_qpi.infrastructure.exceptions.RateLimitExceededException;
import com.personal_projects.notifications_qpi.infrastructure.middleware.ClientContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class RateLimittingAspect {

    private static final ConcurrentHashMap<String, RateLimit> rateLimits = new ConcurrentHashMap<>();
    private static final int REQUEST_LIMIT = 5;
    private static final long TIME_WINDOW = 30000; // 30 segundos
    private static final long BLOCK_DURATION = 60000;

    @Before("@annotation(com.personal_projects.notifications_qpi.infrastructure.validation.RateLimited)")
    public void beforeRequest() throws RateLimitExceededException {
        String clientId = ClientContext.getCurrentTenant();
        RateLimit rateLimit = rateLimits.computeIfAbsent(clientId, k -> new RateLimit());
        if (!rateLimit.tryAcquire()) {
            throw new RateLimitExceededException("Rate limit exceeded");
        }
    }

    private static class RateLimit {
        private final AtomicInteger count = new AtomicInteger(0);
        private final AtomicLong windowStartTime = new AtomicLong(System.currentTimeMillis());
        private final AtomicLong blockEndTime = new AtomicLong(0);

        public synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();
            if (now < blockEndTime.get()) {
                return false;
            }
            if (now - windowStartTime.get() > TIME_WINDOW) {
                count.set(0);
                windowStartTime.set(now);
            }
            int currentCount = count.get();
            if (currentCount < REQUEST_LIMIT) {
                count.incrementAndGet();
                return true;
            } else {
                blockEndTime.set(now + BLOCK_DURATION);
                return false;
            }
        }
    }



}

