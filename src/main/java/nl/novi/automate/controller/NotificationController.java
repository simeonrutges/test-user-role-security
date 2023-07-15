package nl.novi.automate.controller;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.service.NotificationService;
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

    @GetMapping("/user/{username}")
    public ResponseEntity<List<NotificationDto>> getNotificationsForUser(@PathVariable String username) {
        List<NotificationDto> notifications = notificationService.getNotificationsForUser(username);
        return ResponseEntity.ok(notifications);
    }

}
