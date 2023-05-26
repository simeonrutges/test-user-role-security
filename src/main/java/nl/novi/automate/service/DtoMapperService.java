package nl.novi.automate.service;

import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.*;
import nl.novi.automate.repository.RideRepository;
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
    private final RideService rideService;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public DtoMapperService(RoleRepository roleRepos, RideService rideService, RideRepository rideRepository, UserRepository userRepository) {
        this.roleRepos = roleRepos;
        this.rideService = rideService;
        this.rideRepository = rideRepository;
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

    private static String[] getRoleNames(List<Role> roles) {
        return roles.stream()
                .map(Role::getRolename)
                .toArray(String[]::new);
    }

    public User transferToUser(UserDto userDto) {

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

//    DEZE SAFE MOET IK NOG WEGHALEN OMDAT: Dit is belangrijk om te voorkomen dat er onvolledige of incorrecte gegevens
//    in de database worden opgeslagen. Bijvoorbeeld, in de methode transferToRide(RideDto rideDto), wordt de lijst met
//    gebruikers aan de rit toegevoegd, maar deze gebruikers zijn mogelijk nog niet in de database opgeslagen. Dit kan tot
//    problemen leiden als je probeert de rit op te slaan voordat de gebruikers zijn opgeslagen.
        userRepository.save(user);

        return user;
    }

    public Ride transferToRide(RideDto rideDto){
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
        ride.setTotalRitPrice(calculateTotalRitPrice(rideDto.getPricePerPerson(), ride.getPax()));
        ride.setAvailableSpots(rideDto.getAvailableSpots());
        ride.setAutomaticAcceptance(rideDto.isAutomaticAcceptance());
        ride.setEta(rideDto.getEta());


        ride.setDriverUsername(rideDto.getDriverUsername());



        //test 13/4?
        // gebruikers omzetten van UserDto naar User
//        List<User> users = rideDto.getUsers().stream()
//                .map(userDto -> userService.transferToUser(userDto))
//                .collect(Collectors.toList());
//        ride.setUsers(users);
        //



        rideRepository.save(ride);
        /// moet deze laatste save erbij???
        return ride;
    }

    public double calculateTotalRitPrice(double pricePerPerson, int pax){
        double totalPrice = pricePerPerson * pax;

        return totalPrice;

    }

    public RideDto userToDto(Ride ride){
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

    public NotificationDto notificationConvertToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(notification.getId());

//        notificationDto.setSender(transferToDto(notification.getSender()));
//        notificationDto.setReceiver(transferToDto(notification.getReceiver()));
        notificationDto.setSender(userToDto(notification.getSender()));
        notificationDto.setReceiver(userToDto(notification.getReceiver()));

        notificationDto.setType(notification.getType());
        notificationDto.setSentDate(notification.getSentDate());
        notificationDto.setRead(notification.isRead());
        notificationDto.setRideId(notification.getRideId());

        return notificationDto;
    }

    public Notification notificationDtoConvertToEntity(NotificationDto notificationDto) {
        Notification notification = new Notification();

        notification.setId(notificationDto.getId());
//        notification.setSender(userService.transferToUser(notificationDto.getSender()));
//        notification.setReceiver(userService.transferToUser(notificationDto.getReceiver()));
//        notification.setSender(transferToUser(notificationDto.getSender()));
//        notification.setReceiver(transferToUser(notificationDto.getReceiver()));
        notification.setSender(userRepository.findByUsername(notificationDto.getSender().getUsername()).orElseThrow(() -> new UserNotFoundException("Sender not found")));
        notification.setReceiver(userRepository.findByUsername(notificationDto.getReceiver().getUsername()).orElseThrow(() -> new UserNotFoundException("Receiver not found")));

        notification.setType(notificationDto.getType());
        notification.setSentDate(notificationDto.getSentDate());
        notification.setRead(notificationDto.isRead());
        notification.setRideId(notificationDto.getRideId());

        return notification;
    }

    ///message

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

