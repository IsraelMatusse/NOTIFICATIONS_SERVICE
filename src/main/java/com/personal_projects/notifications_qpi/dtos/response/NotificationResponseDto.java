package com.personal_projects.notifications_qpi.dtos.response;

import com.personal_projects.notifications_qpi.entities.Notification;

import java.time.LocalDateTime;

public record NotificationResponseDto(

    String id,
    String message,
    String subject,
    String recipient,
    LocalDateTime sendDate,
    String channel
) {
    public NotificationResponseDto(Notification notification) {
        this(
                notification.getId(),
                notification.getMessage(),
                notification.getSubject() !=null ? notification.getSubject() : "Não especificado",
                notification.getRecipient(),
                notification.getSendDate(),
                notification.getChannel().getName()
        );
    }
}
