package com.personal_projects.notifications_qpi.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;

import java.time.LocalDateTime;

public record NotificationCreateDTO(

        String message,
        String subject,
        String recipient,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime sendDate,
        Channel channel
) {
}
