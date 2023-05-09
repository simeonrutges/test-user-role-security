package com.example.les16.service;

import com.example.les16.dto.NotificationDto;
import com.example.les16.dto.RideDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.UserNotFoundException;
import com.example.les16.model.Notification;
import com.example.les16.model.Ride;
import com.example.les16.model.Role;
import com.example.les16.model.User;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.RoleRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static UserDto transferToDto(User user) {

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

    public RideDto transferToDto(Ride ride){
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


        // test 13/4: omzetten van de User objecten naar UserDto objecten
//        List<UserDto> userDtos = ride.getUsers().stream()
//                .map(UserService::transferToDto)
//                .collect(Collectors.toList());
//        dto.setUsers(userDtos);

        return dto;
    }
    public NotificationDto notificationConvertToDto(Notification notification) {
        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId(notification.getId());

//        notificationDto.setSender(transferToDto(notification.getSender()));
//        notificationDto.setReceiver(transferToDto(notification.getReceiver()));
        notificationDto.setSender(transferToDto(notification.getSender()));
        notificationDto.setReceiver(transferToDto(notification.getReceiver()));

        notificationDto.setType(notification.getType());
        notificationDto.setSentDate(notification.getSentDate());
        notificationDto.setRead(notification.isRead());

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

        return notification;
    }
}
