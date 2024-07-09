package com.personal_projects.notifications_qpi.services;

import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.entities.Notification;
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

    @CacheEvict(value = "notifications", allEntries = true)
    public void createNotification(NotificationCreateDTO notificationData){
        Notification notification = new Notification(notificationData);
        notificationRepo.save(notification);
    }

}
