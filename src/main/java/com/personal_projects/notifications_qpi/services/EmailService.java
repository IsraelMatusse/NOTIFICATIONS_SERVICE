package com.personal_projects.notifications_qpi.services;

import com.personal_projects.notifications_qpi.dtos.request.EmailRequestDTO;
import com.personal_projects.notifications_qpi.integrations.emailservice.EmailServiceWebClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailService {

    private final EmailServiceWebClient emailServiceWebClient;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(EmailService.class);

    public EmailService(EmailServiceWebClient emailServiceWebClient) {
        this.emailServiceWebClient = emailServiceWebClient;
    }

    public void sendEmail(EmailRequestDTO emailRequestDTO) {
        emailServiceWebClient.sendEmail(emailRequestDTO);
    }

    @Async("asyncExecutor")
    public CompletableFuture<Boolean> sendEmailAsync(EmailRequestDTO emailRequestDTO) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                emailServiceWebClient.sendEmail(emailRequestDTO);
                return true;
            } catch (Exception e) {
                logger.error("Error sending email: " + e.getMessage());
                return false;
            }
        });
    }
}



