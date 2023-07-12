package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.ResourceNotFoundException;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.NotificationType;
import nl.novi.automate.model.Ride;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.NotificationRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final DtoMapperService dtoMapperService;


    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, DtoMapperService dtoMapperService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.dtoMapperService = dtoMapperService;
    }

    public List<NotificationDto> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(dtoMapperService::notificationToDto)
                .collect(Collectors.toList());
    }

    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return dtoMapperService.notificationToDto(notification);
    }

    public List<NotificationDto> getNotificationsForUser(String username) {
        List<Notification> notifications = notificationRepository.findByReceiverUsername(username);
        System.out.println("Fetched notifications for user " + username + ": " + notifications);
        List<NotificationDto> notificationDtos = notifications.stream()
                .map(dtoMapperService::notificationToDto)
                .collect(Collectors.toList());
        System.out.println("Converted notifications to DTOs: " + notificationDtos);
        return notificationDtos;
    }

    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = dtoMapperService.dtoToNotification(notificationDto);
        Notification savedNotification = notificationRepository.save(notification);
        return dtoMapperService.notificationToDto(savedNotification);
    }

    public NotificationDto createNotification(NotificationDto notificationDto, Ride ride) {
        //        voor DELETE RDIE
        Notification notification = dtoMapperService.dtoToNotification(notificationDto);
        notification.setRideDetails(rideDetails(ride));
        Notification savedNotification = notificationRepository.save(notification);
        return dtoMapperService.notificationToDto(savedNotification);
    }

    public String rideDetails(Ride ride) {
        String rideDetails = "Rit details: Driver - " + ride.getDriverUsername() +
                ", Pick Up Location - " + ride.getPickUpLocation() +
                ", Destination - " + ride.getDestination() +
                ", Departure DateTime - " + ride.getDepartureDateTime() +
                ", Pax - " + ride.getPax() +
                ", Estimated Arrival Time - " + ride.getEta();
        return rideDetails;
    }

    public void createAndSendPassengerLeftRideNotification(User driver, String passengerUsername, Ride ride) {
        NotificationDto notificationDto = new NotificationDto();

        UserDto receiverDto = dtoMapperService.userToDto(driver);

        User systemUser = userRepository.findByUsername("System").orElseThrow(() -> new UserNotFoundException("System user not found"));
        UserDto senderDto = dtoMapperService.userToDto(systemUser);

        notificationDto.setReceiver(receiverDto);
        notificationDto.setSender(senderDto);
        notificationDto.setType(NotificationType.PASSENGER_LEFT_RIDE);
        notificationDto.setSentDate(LocalDateTime.now()); // Zorgt dat deze tijdzone correct is
        notificationDto.setRead(false);
        notificationDto.setRideDetails("Passenger username: " + passengerUsername + "\n" + rideDetails(ride));
        notificationDto.setRideId(ride.getId());

        createNotification(notificationDto, ride);
    }
}
