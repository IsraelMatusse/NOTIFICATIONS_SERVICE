package com.personal_projects.notifications_qpi.repositories;

import com.personal_projects.notifications_qpi.entities.Notification;
import com.personal_projects.notifications_qpi.infrastructure.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepo  extends JpaRepository<Notification, String> {

    List<Notification>findByStatus(NotificationStatus status);

    List<Notification>findByRecipient(String recipient);
    List<Notification>findByStatusAndSendDateLessThanEqual(NotificationStatus status, LocalDateTime sendDate);
}
