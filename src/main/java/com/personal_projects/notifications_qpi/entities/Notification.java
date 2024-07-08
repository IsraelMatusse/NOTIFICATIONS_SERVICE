package com.personal_projects.notifications_qpi.entities;

import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.infrastructure.enums.NotificationStatus;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
    private String subject;
    private String recipient;
    private LocalDateTime sendDate;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Enumerated(EnumType.STRING)
    private Channel channel;


    public Notification(NotificationCreateDTO notificationData){
        this.message=notificationData.message();
        this.subject=notificationData.subject();
        this.channel=notificationData.channel();
        this.status=NotificationStatus.PENDING;
        this.sendDate=notificationData.sendDate();
        this.recipient=notificationData.recipient();
    }
}
