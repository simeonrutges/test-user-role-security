package nl.novi.automate.service;

import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.*;
import nl.novi.automate.repository.RoleRepository;
import nl.novi.automate.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DtoMapperService {
    private final RoleRepository roleRepos;
    private final UserRepository userRepository;

    public DtoMapperService(RoleRepository roleRepos, UserRepository userRepository) {
        this.roleRepos = roleRepos;
        this.userRepository = userRepository;
    }


    public UserDto userToDto(User user) {
        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.firstname = user.getFirstname();
        dto.lastname = user.getLastname();
        dto.phoneNumber = user.getPhoneNumber();
        dto.email = user.getEmail();
        dto.bio = user.getBio();

        dto.setRoles(getRoleNames((List<Role>) user.getRoles()));

        dto.fileName = user.getFileName();
        dto.docFile = user.getDocFile();

        return dto;
    }

    public User dtoToUser(UserDto userDto) {
        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.isEnabled());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setBio(userDto.getBio());

        user.setFileName(userDto.getFileName());
        user.setDocFile(user.getDocFile());

        List<Role> userRoles = new ArrayList<>();
        for (String rolename : userDto.roles) {
            Optional<Role> or = roleRepos.findById(rolename);

            userRoles.add(or.get());
        }
        user.setRoles(userRoles);

        return user;
    }

    private static String[] getRoleNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getRolename)
                .toArray(String[]::new);
    }


    public Ride dtoToRide(RideDto rideDto){
        var ride = new Ride();
//deze hieronder vandaag weggehaalt vanwege de PUT
        ride.setId(rideDto.getId());
        ride.setDestination(rideDto.getDestination());
        ride.setPickUpLocation(rideDto.getPickUpLocation());
        ride.setRoute(rideDto.getRoute());
        ride.setAddRideInfo(rideDto.getAddRideInfo());
        ride.setDepartureTime(rideDto.getDepartureTime());
        ride.setDepartureDate(rideDto.getDepartureDate());
        ride.setDepartureDateTime(rideDto.getDepartureDateTime());
        ride.setPricePerPerson(rideDto.getPricePerPerson());
        ride.setPax(rideDto.getPax());
//        ride.setTotalRitPrice(calculateTotalRitPrice(rideDto.getPricePerPerson(), ride.getPax()));
        ride.setTotalRitPrice(rideDto.getPricePerPerson() * rideDto.getPax());
        ride.setAvailableSpots(rideDto.getAvailableSpots());
        ride.setAutomaticAcceptance(rideDto.isAutomaticAcceptance());
        ride.setEta(rideDto.getEta());
        ride.setDriverUsername(rideDto.getDriverUsername());

        return ride;
    }

    public RideDto rideToDto(Ride ride){
        var dto = new RideDto();

        dto.id = ride.getId();
        dto.destination = ride.getDestination();
        dto.pickUpLocation = ride.getPickUpLocation();
        dto.route = ride.getRoute();
        dto.addRideInfo = ride.getAddRideInfo();
        dto.departureTime = ride.getDepartureTime();
        dto.departureDate = ride.getDepartureDate();
        dto.departureDateTime = ride.getDepartureDateTime();
        dto.pricePerPerson = ride.getPricePerPerson();
        dto.pax = ride.getPax();
        dto.totalRitPrice = ride.getTotalRitPrice();
        dto.availableSpots = ride.getAvailableSpots();
        dto.automaticAcceptance = ride.isAutomaticAcceptance();
        dto.eta = ride.getEta();
        dto.driverUsername = ride.getDriverUsername();

        List<UserDto> userDtos = ride.getUsers().stream().map(this::userToDto).collect(Collectors.toList());
        dto.setUsers(userDtos);

        return dto;
    }

//    public double calculateTotalRitPrice(double pricePerPerson, int pax){
//        double totalPrice = pricePerPerson * pax;
//
//        return totalPrice;
//
//    }


    public NotificationDto notificationToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(notification.getId());
        notificationDto.setSender(userToDto(notification.getSender()));
        notificationDto.setReceiver(userToDto(notification.getReceiver()));
        notificationDto.setType(notification.getType());
        notificationDto.setSentDate(notification.getSentDate());
        notificationDto.setRead(notification.isRead());
        notificationDto.setRideId(notification.getRideId());

        return notificationDto;
    }

    public Notification dtoToNotification(NotificationDto notificationDto) {
        Notification notification = new Notification();

        notification.setId(notificationDto.getId());
        notification.setSender(userRepository.findByUsername(notificationDto.getSender().getUsername()).orElseThrow(() -> new UserNotFoundException("Sender not found")));
        notification.setReceiver(userRepository.findByUsername(notificationDto.getReceiver().getUsername()).orElseThrow(() -> new UserNotFoundException("Receiver not found")));
        notification.setType(notificationDto.getType());
        notification.setSentDate(notificationDto.getSentDate());
        notification.setRead(notificationDto.isRead());
        notification.setRideId(notificationDto.getRideId());

        return notification;
    }

    public MessageDto messageToDto(Message message) {
        MessageDto messageDto = new MessageDto();

        messageDto.setId(message.getId());
        messageDto.setSenderUsername(message.getSenderUsername());
        messageDto.setReceiverUsername(message.getReceiverUsername());
        messageDto.setContent(message.getContent());
        messageDto.setTimestamp(message.getTimestamp());
        messageDto.setRead(message.isRead());

        return messageDto;
    }

    public Message dtoToMessage(MessageDto messageDto) {
        Message message = new Message();

        message.setId(messageDto.getId());
        message.setSenderUsername(messageDto.getSenderUsername());
        message.setReceiverUsername(messageDto.getReceiverUsername());
        message.setContent(messageDto.getContent());
        message.setTimestamp(messageDto.getTimestamp());
        message.setRead(messageDto.isRead());

        return message;
    }
}

