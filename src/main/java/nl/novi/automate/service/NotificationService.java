package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.exceptions.ResourceNotFoundException;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.Ride;
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

    // Andere service methoden indien nodig (bijv. update, delete)
    public NotificationDto createNotification(NotificationDto notificationDto, Ride ride) {
        Notification notification = dtoMapperService.dtoToNotification(notificationDto);
        notification.setRideDetails(rideDetails(ride));
        Notification savedNotification = notificationRepository.save(notification);
        return dtoMapperService.notificationToDto(savedNotification);
    }

    public String rideDetails(Ride ride) {
        String rideDetails = "Rit details: Driver - " + ride.getDriverUsername() +
                ", Pick Up Location - " + ride.getPickUpLocation() +
                ", Destination - " + ride.getDestination() +
//                ", Route - " + ride.getRoute() +
//                ", Departure Time - " + ride.getDepartureTime() +
//                ", Departure Date - " + ride.getDepartureDate() +
                ", Departure DateTime - " + ride.getDepartureDateTime() +
//                ", Price per Person - " + ride.getPricePerPerson() +
                ", Pax - " + ride.getPax() +
//                ", Total Rit Price - " + ride.getTotalRitPrice() +
                ", Estimated Arrival Time - " + ride.getEta();
        return rideDetails;
    }

}
