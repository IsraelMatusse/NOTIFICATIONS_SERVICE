package com.personal_projects.notifications_qpi.controllers;

import com.personal_projects.notifications_qpi.dtos.internal.ResponseAPI;
import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    @PostMapping
    public ResponseEntity<ResponseAPI>createNotification(@RequestBody NotificationCreateDTO notificationData){
        notificationService.createNotification(notificationData);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseAPI("Notification created", null));
    }
    @GetMapping
    public ResponseEntity<ResponseAPI>getNotifications(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Notifications", notificationService.findAll()));
    }
}
