package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.exceptions.ResourceNotFoundException;
import nl.novi.automate.model.Notification;
import nl.novi.automate.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;

    public NotificationService(NotificationRepository notificationRepository, UserService userService, DtoMapperService dtoMapperService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.dtoMapperService = dtoMapperService;
    }

    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
//                .map(this::notificationConvertToDto)
                .map(dtoMapperService::notificationToDto)
                .collect(Collectors.toList());
    }

    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return dtoMapperService.notificationToDto(notification);
    }

    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = dtoMapperService.dtoToNotification(notificationDto);
        Notification savedNotification = notificationRepository.save(notification);
        return dtoMapperService.notificationToDto(savedNotification);
    }
    // In NotificationService.java
//    public List<NotificationDto> getNotificationsForUser(String username) {
//        List<Notification> notifications = notificationRepository.findByReceiverUsername(username);
//        return notifications.stream()
////                .map(this::notificationConvertToDto)
//                .map(dtoMapperService::notificationConvertToDto)
//                .collect(Collectors.toList());
//    }
    public List<NotificationDto> getNotificationsForUser(String username) {
        List<Notification> notifications = notificationRepository.findByReceiverUsername(username);
        System.out.println("Fetched notifications for user " + username + ": " + notifications);
        List<NotificationDto> notificationDtos = notifications.stream()
                .map(dtoMapperService::notificationToDto)
                .collect(Collectors.toList());
        System.out.println("Converted notifications to DTOs: " + notificationDtos);
        return notificationDtos;
    }


    // Conversiemethoden

//    public NotificationDto notificationConvertToDto(Notification notification) {
//        NotificationDto notificationDto = new NotificationDto();
//
//        notificationDto.setId(notification.getId());
//
////        notificationDto.setSender(transferToDto(notification.getSender()));
////        notificationDto.setReceiver(transferToDto(notification.getReceiver()));
//        notificationDto.setSender(dtoMapperService.transferToDto(notification.getSender()));
//        notificationDto.setReceiver(dtoMapperService.transferToDto(notification.getReceiver()));
//
//        notificationDto.setType(notification.getType());
//        notificationDto.setSentDate(notification.getSentDate());
//        notificationDto.setRead(notification.isRead());
//
//        return notificationDto;
//    }
//
//    private Notification notificationDtoConvertToEntity(NotificationDto notificationDto) {
//        Notification notification = new Notification();
//
//        notification.setId(notificationDto.getId());
////        notification.setSender(userService.transferToUser(notificationDto.getSender()));
////        notification.setReceiver(userService.transferToUser(notificationDto.getReceiver()));
//        notification.setSender(dtoMapperService.transferToUser(notificationDto.getSender()));
//        notification.setReceiver(dtoMapperService.transferToUser(notificationDto.getReceiver()));
//
//        notification.setType(notificationDto.getType());
//        notification.setSentDate(notificationDto.getSentDate());
//        notification.setRead(notificationDto.isRead());
//
//        return notification;
//    }

    // Andere service methoden indien nodig (bijv. update, delete)
}
