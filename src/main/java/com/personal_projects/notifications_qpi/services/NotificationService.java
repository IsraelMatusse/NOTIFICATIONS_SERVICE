package com.personal_projects.notifications_qpi.services;

import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.dtos.response.NotificationResponseDto;
import com.personal_projects.notifications_qpi.entities.ApiKey;
import com.personal_projects.notifications_qpi.entities.Notification;
import com.personal_projects.notifications_qpi.infrastructure.enums.Channel;
import com.personal_projects.notifications_qpi.infrastructure.enums.NotificationStatus;
import com.personal_projects.notifications_qpi.infrastructure.exceptions.NotFoundException;
import com.personal_projects.notifications_qpi.infrastructure.exceptions.UnprocessableEntityException;
import com.personal_projects.notifications_qpi.repositories.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final ApiKeyService apiKeyService;

    public Notification saveNotification(Notification notification) {
        return notificationRepo.save(notification);
    }
    public Notification findById(String id) {
        return notificationRepo.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
    }
    @Cacheable("notifications")
    public List<Notification>findAll(){
        return notificationRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }
    @CacheEvict(value = "notifications", allEntries = true)
    public void clearCache() {
    }

    public List<Notification>findByStatusAndChannel(NotificationStatus status, Channel channel){
        return notificationRepo.findByStatusAndChannel(status, channel);
    }

    public void changeNotificationStatus(Notification notification, NotificationStatus notificationStatus){
        notification.setStatus(notificationStatus);
        this.saveNotification(notification);
    }

    public List<NotificationResponseDto>getNotificationsRes(){
        return notificationRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(NotificationResponseDto::new).toList();
    }

    public NotificationResponseDto findByIdRes(String id) throws NotFoundException {
        return new NotificationResponseDto(this.findById(id));
    }

    @CacheEvict(value = "notifications", allEntries = true)
    public void createNotification(NotificationCreateDTO notificationData, String rawApiKey) throws UnprocessableEntityException {
        ApiKey apiKey=apiKeyService.getApiKeyFromRawApiKey(rawApiKey);
        if(notificationData.channel().equals(Channel.EMAIL) && notificationData.subject()==null ){
            throw new UnprocessableEntityException("Subject is required for email notifications");
        }
        Notification notification = new Notification(notificationData, apiKey.getUserEmail());
        notificationRepo.save(notification);
    }

}
