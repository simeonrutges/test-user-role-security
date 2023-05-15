package com.example.les16.service;

import com.example.les16.dto.NotificationDto;
import com.example.les16.dto.RideDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.exceptions.UserAlreadyAddedToRideException;
import com.example.les16.exceptions.UserNotFoundException;
import com.example.les16.exceptions.UserNotInRideException;
import com.example.les16.model.Notification;
import com.example.les16.model.NotificationType;
import com.example.les16.model.Ride;
import com.example.les16.model.User;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {
    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final DtoMapperService dtoMapperService;

    public RideService(RideRepository rideRepository, UserRepository userRepository, @Lazy NotificationService notificationService, @Lazy DtoMapperService dtoMapperService) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.dtoMapperService = dtoMapperService;
    }

//    public RideDto addRide(RideDto rideDto) {
//
////        Ride newRide = transferToRide(rideDto);
//        Ride newRide = dtoMapperService.transferToRide(rideDto);
//        rideRepository.save(newRide);
//        rideDto.setId(newRide.getId());
//////
//        User sender = userRepository.findByUsernameIgnoreCase("System");
//        Optional<User> receiverOptional = userRepository.findByUsername(rideDto.getDriverUsername());
//        if (receiverOptional.isPresent()) {
//            User receiver = receiverOptional.get();
//            Notification notification = new Notification(
//                    sender,
//                    receiver,
//                    NotificationType.RIDE_CONFIRMATION,
//                    LocalDateTime.now(),
//                    false
//            );
//
//
//            NotificationDto notificationDto = dtoMapperService.notificationConvertToDto(notification);
//
//            // Sla het bevestigingsbericht op
//            notificationService.createNotification(notificationDto);
//        } else {
//            throw new UserNotFoundException("User not found with username: " + rideDto.getDriverUsername());
//        }
//////
//
//        return rideDto;
//
//    }
public RideDto addRide(RideDto rideDto) {
    Ride newRide = dtoMapperService.transferToRide(rideDto);
    rideRepository.save(newRide);
    rideDto.setId(newRide.getId());

    User sender = userRepository.findByUsernameIgnoreCase("System");
    Optional<User> receiverOptional = userRepository.findByUsername(rideDto.getDriverUsername());
    if (receiverOptional.isPresent()) {
        User receiver = receiverOptional.get();

        Notification notification = new Notification(
                sender,
                receiver,
                NotificationType.RIDE_CONFIRMATION,
                LocalDateTime.now(),
                false,
                newRide.getId() // Voeg hier de rit ID toe
        );

        NotificationDto notificationDto = dtoMapperService.notificationConvertToDto(notification);

        // Sla het bevestigingsbericht op
        notificationService.createNotification(notificationDto);
    } else {
        throw new UserNotFoundException("User not found with username: " + rideDto.getDriverUsername());
    }

    return rideDto;
}
//////////


    public RideDto getRideById(Long id) {
        Optional<Ride> ride = rideRepository.findById(id);
        if(ride.isPresent()) {
//            return transferToDto(ride.get());
            return dtoMapperService.transferToDto(ride.get());
        } else {
            throw new RecordNotFoundException("Geen rit gevonden");
        }
    }

//        if (rideRepository.findById(id).isPresent()){
//            Ride tv = rideRepository.findById(id).get();
//            RideDto dto =transferToDto(tv);
//            if(tv.getDriverProfile() != null){
//                dto.setDriverProfileDto(driverProfileService.transferToDto(tv.getDriverProfile()));
//            }
////            if(tv.getRemoteController() != null){
////                dto.setRemoteControllerDto(remoteControllerService.transferToDto(tv.getRemoteController()));
////            }
//
//            return transferToDto(tv);
//        } else {
//            throw new RecordNotFoundException("geen rit gevonden");
//        }
//    }


//    public RideDto addUserToRide(String username, Long rideId)  {
//        UserRide userRide = new UserRide(username, rideId);
//        UserRideRepository.save(userRide);
//
//        return
//    }

    public double calculateTotalRitPrice(double pricePerPerson, int pax){
        double totalPrice = pricePerPerson * pax;

        return totalPrice;

    }
///////////// test mapper
//    public Ride transferToRide(RideDto rideDto){
//        var ride = new Ride();
////deze hieronder vandaag weggehaalt vanwege de PUT
//        ride.setId(rideDto.getId());
//        ride.setDestination(rideDto.getDestination());
//        ride.setPickUpLocation(rideDto.getPickUpLocation());
//        ride.setRoute(rideDto.getRoute());
//        ride.setAddRideInfo(rideDto.getAddRideInfo());
//        ride.setDepartureTime(rideDto.getDepartureTime());
//        ride.setDepartureDate(rideDto.getDepartureDate());
//        ride.setDepartureDateTime(rideDto.getDepartureDateTime());
//        ride.setPricePerPerson(rideDto.getPricePerPerson());
//        ride.setPax(rideDto.getPax());
//        ride.setTotalRitPrice(calculateTotalRitPrice(rideDto.getPricePerPerson(), ride.getPax()));
//        ride.setAvailableSpots(rideDto.getAvailableSpots());
//        ride.setAutomaticAcceptance(rideDto.isAutomaticAcceptance());
//        ride.setEta(rideDto.getEta());
//
//
//        ride.setDriverUsername(rideDto.getDriverUsername());
//
//        rideRepository.save(ride);
//
//        return ride;
//    }
//
//    public RideDto transferToDto(Ride ride){
//        var dto = new RideDto();
//
//        dto.id = ride.getId();
//        dto.destination = ride.getDestination();
//        dto.pickUpLocation = ride.getPickUpLocation();
//        dto.route = ride.getRoute();
//        dto.addRideInfo = ride.getAddRideInfo();
//        dto.departureTime = ride.getDepartureTime();
//        dto.departureDate = ride.getDepartureDate();
//        dto.departureDateTime = ride.getDepartureDateTime();
//        dto.pricePerPerson = ride.getPricePerPerson();
//        dto.pax = ride.getPax();
//        dto.totalRitPrice = ride.getTotalRitPrice();
//        dto.availableSpots = ride.getAvailableSpots();
//        dto.automaticAcceptance = ride.isAutomaticAcceptance();
//        dto.eta = ride.getEta();
//
//
//        dto.driverUsername = ride.getDriverUsername();
//
//        return dto;
//    }




// hieronder vanavond verder gegaan. is dit inderdaad nodig?
//    public void addUserToRide(Long id, String username) {
//        var optionalRide = rideRepository.findById(id);
//        var optionalUser = userRepository.findByUsername(username);
//
//        if(optionalRide.isPresent() && optionalUser.isPresent()) {
//            var ride = optionalRide.get();
//            var user = optionalUser.get();
//
//            user.getRides().add(ride);
//            ride.getUsers().add(user);
//
//            userRepository.save(user);
//            rideRepository.save(ride);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }

//9-5:
    public void addUserToRide(Long id, String username) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findByUsername(username);

        if(optionalRide.isPresent() && optionalUser.isPresent()) {
            var ride = optionalRide.get();
            var user = optionalUser.get();

            // Controleer of de gebruiker al aan de rit is toegevoegd
            if (ride.getUsers().contains(user)) {
                throw new UserAlreadyAddedToRideException("User already added to this ride");
            }

            user.getRides().add(ride);
            ride.getUsers().add(user);

            userRepository.save(user);
            rideRepository.save(ride);
        } else {
            throw new RecordNotFoundException();
        }
    }



    //    public List<RideDto> getAllRidesByDestination(String destination) {
//        List<Ride> rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCase(destination);
//        return transferRideListToDtoList(rideList);
//    }
public List<RideDto> getRidesByCriteria(
        Optional<String> destination,
        Optional<String> pickUpLocation,
//        Optional<LocalDate> departureDate)
        Optional<LocalDateTime> departureDateTime)

{

    List<Ride> rideList;

    if (destination.isPresent() && pickUpLocation.isPresent() && departureDateTime.isPresent()) {
        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCaseAndDepartureDateTimeEquals(
                destination.get(),
                pickUpLocation.get(),
                departureDateTime.get()
        );
    } else if (destination.isPresent() && pickUpLocation.isPresent()) {
        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase(
                destination.get(),
                pickUpLocation.get()
        );
    } else if (destination.isPresent() && departureDateTime.isPresent()) {
        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndDepartureDateTimeEquals(
                destination.get(),
                departureDateTime.get()
        );
    } else if (pickUpLocation.isPresent() && departureDateTime.isPresent()) {
        rideList = rideRepository.findAllRidesByPickUpLocationEqualsIgnoreCaseAndDepartureDateTimeEquals(
                pickUpLocation.get(),
                departureDateTime.get()
        );
    } else if (destination.isPresent()) {
        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCase(destination.get());
    } else if (pickUpLocation.isPresent()) {
        rideList = rideRepository.findAllRidesByPickUpLocationEqualsIgnoreCase(pickUpLocation.get());
    } else if (departureDateTime.isPresent()) {
        rideList = rideRepository.findAllRidesByDepartureDateTimeEquals(departureDateTime.get());
    } else {
        rideList = rideRepository.findAll();
    }

    return transferRideListToDtoList(rideList);
}


//    public List<RideDto> getRidesByCriteria(
//            Optional<String> destination,
//            Optional<String> pickUpLocation,
//            Optional<LocalDate> departureDate) {
//
//        Specification<Ride> spec = Specification.where(null);
//
//        if (destination.isPresent()) {
//            spec = spec.and(RideSpecifications.destinationEqualsIgnoreCase(destination.get()));
//        }
//
//        if (pickUpLocation.isPresent()) {
//            spec = spec.and(RideSpecifications.pickUpLocationEqualsIgnoreCase(pickUpLocation.get()));
//        }
//
//        if (departureDate.isPresent()) {
//            spec = spec.and(RideSpecifications.departureDateEquals(departureDate.get()));
//        }
//
//        List<Ride> rideList = rideRepository.findAll(spec);
//        return transferRideListToDtoList(rideList);
//    }




    public List<RideDto> transferRideListToDtoList(List<Ride> rides){
        List<RideDto> rideDtoList = new ArrayList<>();

        for(Ride ride : rides) {
//            RideDto dto = transferToDto(ride);
            RideDto dto = dtoMapperService.transferToDto(ride);

            rideDtoList.add(dto);
        }
        return rideDtoList;
    }

//    public void deleteRide(Long id) {
//        rideRepository.deleteById(id);
//    }

    public void deleteRide(Long id) {
        Optional<Ride> optionalRide = rideRepository.findById(id);

        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();

            // Verwijder de rit uit de lijst van ritten voor elke gerelateerde gebruiker
            for (User user : ride.getUsers()) {
                user.getRides().remove(ride);
                userRepository.save(user); // Sla de bijgewerkte User entiteit op
            }

            // Verwijder de Ride entiteit
            rideRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Rit niet gevonden");
        }
    }



    public RideDto updateRide(Long id, RideDto newRide) {
        if (rideRepository.findById(id).isPresent()){

            Ride ride = rideRepository.findById(id).get();

//            Ride ride1 = transferToRide(newRide);
            Ride ride1 = dtoMapperService.transferToRide(newRide);
            ride1.setId(ride.getId());

            rideRepository.save(ride1);

//            return transferToDto(ride1);
            return dtoMapperService.transferToDto(ride1);

        } else {

            throw new  RecordNotFoundException("geen rit gevonden");

        }
    }

    ///
    public void removeUserFromRide(Long rideId, String username) {
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (!rideOptional.isPresent()) {
            throw new RecordNotFoundException("Ride not found");
        }

        Ride ride = rideOptional.get();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new RecordNotFoundException("User not found");
        }

        User user = userOptional.get();
        if (!ride.getUsers().contains(user)) {
            throw new UserNotInRideException("User is not part of this ride");
        }

        ride.getUsers().remove(user);
        user.getRides().remove(ride);
        rideRepository.save(ride);
        userRepository.save(user);
    }
}
