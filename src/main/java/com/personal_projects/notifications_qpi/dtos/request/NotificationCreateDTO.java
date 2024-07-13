package com.personal_projects.notifications_qpi.dtos.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NotificationCreateDTO(

        @NotBlank(message = "Message is required")
        String message,
        String subject,
        @NotBlank(message = "Recipient is required")
        String recipient,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime sendDate,
        @NotNull(message = "Channel is required")
        Channel channel
) {
}
