package com.example.les16.controller;

import com.example.les16.dto.NotificationDto;
import com.example.les16.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @PostMapping("")
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationDTO) {
        NotificationDto createdNotification = notificationService.createNotification(notificationDTO);
        return ResponseEntity.ok(createdNotification);
    }

    // Andere endpoints indien nodig (bijv. update, delete)

    @GetMapping("/user/{username}")
    public ResponseEntity<List<NotificationDto>> getNotificationsForUser(@PathVariable String username) {
        List<NotificationDto> notifications = notificationService.getNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

}
