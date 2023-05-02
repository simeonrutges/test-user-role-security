package com.example.les16.service;

import com.example.les16.dto.NotificationDto;
import com.example.les16.exceptions.ResourceNotFoundException;
import com.example.les16.model.Notification;
import com.example.les16.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.les16.service.UserService.transferToDto;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;


    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return convertToDto(notification);
    }

    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = convertToEntity(notificationDto);
        Notification savedNotification = notificationRepository.save(notification);
        return convertToDto(savedNotification);
    }

    // Conversiemethoden

    private NotificationDto convertToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(notification.getId());

        notificationDto.setSender(transferToDto(notification.getSender()));
        notificationDto.setReceiver(transferToDto(notification.getReceiver()));

        notificationDto.setType(notification.getType());
        notificationDto.setSentDate(notification.getSentDate());
        notificationDto.setRead(notification.isRead());

        return notificationDto;
    }

    private Notification convertToEntity(NotificationDto notificationDto) {
        Notification notification = new Notification();

        notification.setId(notificationDto.getId());
        notification.setSender(userService.transferToUser(notificationDto.getSender()));
        notification.setReceiver(userService.transferToUser(notificationDto.getReceiver()));

        notification.setType(notificationDto.getType());
        notification.setSentDate(notificationDto.getSentDate());
        notification.setRead(notificationDto.isRead());

        return notification;
    }

    // Andere service methoden indien nodig (bijv. update, delete)
}
