package com.personal_projects.notifications_qpi.infrastructure.config;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ResilienceConfig {

    private static final Logger logger = LoggerFactory.getLogger(ResilienceConfig.class);

    @Autowired
    private RetryRegistry retryRegistry;

    @PostConstruct
    public void listenOnRetry() {
        retryRegistry.retry("email-service").getEventPublisher()
                .onRetry(event -> logger.info("Retry attempt {} for {}", event.getNumberOfRetryAttempts(), event.getName()))
                .onSuccess(event -> logger.info("Call succeeded on retry attempt {}", event.getNumberOfRetryAttempts()))
                .onError(event -> logger.error("Call failed on retry attempt {}", event.getNumberOfRetryAttempts()));
    }
}
