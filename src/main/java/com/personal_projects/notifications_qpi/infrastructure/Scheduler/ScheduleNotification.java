package com.personal_projects.notifications_qpi.infrastructure.Scheduler;

import com.personal_projects.notifications_qpi.dtos.request.EmailRequestDTO;
import com.personal_projects.notifications_qpi.entities.Notification;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;
import com.personal_projects.notifications_qpi.infrastructure.enums.NotificationStatus;
import com.personal_projects.notifications_qpi.services.EmailService;
import com.personal_projects.notifications_qpi.services.NotificationService;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component

public class ScheduleNotification {
  private final NotificationService notificationService;
  private final EmailService emailService;
  private final Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduleNotification.class);

    public ScheduleNotification(NotificationService notificationService, EmailService emailService) {
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    @Scheduled(fixedDelay = 5000)
    public void sendEmailSchedule() {
        List<Notification> emailNotificationsAndStatusPending = notificationService.findByStatusAndChannel(NotificationStatus.PENDING, Channel.EMAIL);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (Notification notification : emailNotificationsAndStatusPending) {
            CompletableFuture<Void> future = emailService.sendEmailAsync(this.toDto(notification))
                    .thenAccept(success -> {
                        if (success) {
                            notificationService.changeNotificationStatus(notification, NotificationStatus.SENT);
                            logger.info("Email sent successfully");
                        } else {
                            notificationService.changeNotificationStatus(notification, NotificationStatus.FAILED);
                            logger.error("Failed to send email");
                        }
                    })
                    .exceptionally(throwable -> {
                        notificationService.changeNotificationStatus(notification, NotificationStatus.FAILED);
                        logger.error("Error in sendEmailSchedule: " + throwable.getMessage());
                        return null;
                    });

            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }



    public EmailRequestDTO toDto(Notification notification){
        return new EmailRequestDTO(notification.getRecipient(),notification.getSubject(),notification.getMessage());
    }


}

