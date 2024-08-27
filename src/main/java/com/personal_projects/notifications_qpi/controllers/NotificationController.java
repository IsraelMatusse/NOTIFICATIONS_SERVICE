package com.personal_projects.notifications_qpi.controllers;

import com.personal_projects.notifications_qpi.dtos.internal.ResponseAPI;
import com.personal_projects.notifications_qpi.dtos.request.NotificationCreateDTO;
import com.personal_projects.notifications_qpi.infrastructure.exceptions.NotFoundException;
import com.personal_projects.notifications_qpi.infrastructure.exceptions.UnprocessableEntityException;
import com.personal_projects.notifications_qpi.infrastructure.middleware.ClientContext;
import com.personal_projects.notifications_qpi.infrastructure.validation.RateLimited;
import com.personal_projects.notifications_qpi.services.NotificationService;
import org.springframework.http.HttpHeaders;
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

    @RateLimited
    @GetMapping
    public ResponseEntity<ResponseAPI>getNotifications(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Notifications", notificationService.getNotificationsRes()));
    }

    @GetMapping("/{id}")
    @RateLimited
    public ResponseEntity<ResponseAPI>getNotificationsById(@PathVariable String id) throws  NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Notifications", notificationService.findByIdRes(id)));
    }

    @GetMapping("/emails")
    public ResponseEntity<ResponseAPI>testStranglerFig(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Teste de strangler fig", null));
    }


    @RateLimited
    @PostMapping
    public ResponseEntity<ResponseAPI>createNotification(@RequestBody NotificationCreateDTO notificationData,
                                                         @RequestHeader (value = HttpHeaders.AUTHORIZATION) String rawApiKey) throws UnprocessableEntityException {
        String apiKey= rawApiKey.replace("Bearer ", "");
        notificationService.createNotification(notificationData, apiKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseAPI("Notification created", null));
    }

}
