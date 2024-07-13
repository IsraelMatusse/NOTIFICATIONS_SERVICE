package com.personal_projects.notifications_qpi.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.infrastructure.enums.NotificationStatus;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@EntityListeners(AuditingEntityListener.class)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String message;
    private String subject;
    private String recipient;
    private LocalDateTime sendDate;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
    @Enumerated(EnumType.STRING)
    private Channel channel;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    private Boolean active = true;


    public Notification(NotificationCreateDTO notificationData, String createdBy){
        this.message=notificationData.message();
        this.subject=notificationData.subject();
        this.channel=notificationData.channel();
        this.status=NotificationStatus.PENDING;
        this.sendDate=notificationData.sendDate();
        this.recipient=notificationData.recipient();
        this.createdAt=LocalDateTime.now();
        this.createdBy=createdBy;
    }
}
