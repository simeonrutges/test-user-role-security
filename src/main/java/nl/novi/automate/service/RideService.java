package nl.novi.automate.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.exceptions.*;
import nl.novi.automate.model.*;
import nl.novi.automate.repository.RideRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    Ride newRide = dtoMapperService.dtoToRide(rideDto);
    rideRepository.save(newRide);
    rideDto.setId(newRide.getId());

    User sender = userRepository.findByUsernameIgnoreCase("System");
    Optional<User> receiverOptional = userRepository.findByUsername(rideDto.getDriverUsername());
    if (receiverOptional.isPresent()) {
        User receiver = receiverOptional.get();

        String rideDetails = notificationService.rideDetails(newRide);

        Notification notification = new Notification(
                sender,
                receiver,
                NotificationType.RIDE_CONFIRMATION,
                LocalDateTime.now(),
                false,
                rideDetails,
                newRide.getId() // Voeg hier de rit ID toe
        );

        NotificationDto notificationDto = dtoMapperService.notificationToDto(notification);

        // Sla het bevestigingsbericht op
        notificationService.createNotification(notificationDto);
    } else {
        throw new UserNotFoundException("User not found with username: " + rideDto.getDriverUsername());
    }

    return rideDto;
}


    public RideDto getRideById(Long id) {
        Optional<Ride> ride = rideRepository.findById(id);
        if(ride.isPresent()) {
//            return transferToDto(ride.get());
            return dtoMapperService.rideToDto(ride.get());
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

//9-5:
//    public void addUserToRide(Long id, String username) {
//        var optionalRide = rideRepository.findById(id);
//        var optionalUser = userRepository.findByUsername(username);
//
//        if(optionalRide.isPresent() && optionalUser.isPresent()) {
//            var ride = optionalRide.get();
//            var user = optionalUser.get();
//
//            // Controleer of de gebruiker al aan de rit is toegevoegd
//            if (ride.getUsers().contains(user)) {
//                throw new UserAlreadyAddedToRideException("User already added to this ride");
//            }
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

//    31/5 hierboven was goed hieronder werkt goed met postman
//public void addUserToRide(Long id, String username, int pax) {
//    var optionalRide = rideRepository.findById(id);
//    var optionalUser = userRepository.findByUsername(username);
//
//    if(!optionalRide.isPresent()) {
//        throw new RecordNotFoundException("Ride with id " + id + " not found.");
//    }
//
//    if(!optionalUser.isPresent()) {
//        throw new RecordNotFoundException("User with username " + username + " not found.");
//    }
//
//    var ride = optionalRide.get();
//    var user = optionalUser.get();
//
//    // Controleer of er voldoende plekken beschikbaar zijn
//    if (ride.getAvailableSpots() < pax) {
//        throw new ExceededCapacityException("The number of passengers exceeds the available spots");
//    }
//
//    // Controleer of de gebruiker al aan de rit is toegevoegd
//    if (ride.getUsers().contains(user)) {
//        throw new UserAlreadyAddedToRideException("User already added to this ride");
//    }
//
//    user.getRides().add(ride);
//    ride.getUsers().add(user);
//
//    // Verminder het aantal beschikbare plekken
//    ride.setAvailableSpots(ride.getAvailableSpots() - pax);
//
//    userRepository.save(user);
//    rideRepository.save(ride);
//}
    //de code hierboven was goed 6-6

//    public void addUserToRide(Long id, String username, int pax) {
//        var optionalRide = rideRepository.findById(id);
//        var optionalUser = userRepository.findByUsername(username);
//
//        if(!optionalRide.isPresent()) {
//            throw new RecordNotFoundException("Ride with id " + id + " not found.");
//        }
//
//        if(!optionalUser.isPresent()) {
//            throw new RecordNotFoundException("User with username " + username + " not found.");
//        }
//
//        var ride = optionalRide.get();
//        var user = optionalUser.get();
//
//        // Controleer of er voldoende plekken beschikbaar zijn
//        if (ride.getAvailableSpots() < pax) {
//            throw new ExceededCapacityException("The number of passengers exceeds the available spots");
//        }
//
//        // Controleer of de gebruiker al aan de rit is toegevoegd
//        if (ride.getUsers().contains(user)) {
//            throw new UserAlreadyAddedToRideException("User already added to this ride");
//        }
//
//        user.getRides().add(ride);
//        ride.getUsers().add(user);
//
//        // Verminder het aantal beschikbare plekken
//        ride.setAvailableSpots(ride.getAvailableSpots() - pax);
//
//        // Verhoog het totale ritprijs op basis van het aantal passagiers en de prijs per persoon
//        ride.setTotalRitPrice(ride.getTotalRitPrice() + (ride.getPricePerPerson() * pax));
//
//        // Verhoog het aantal reserveringen
//        ride.setPax(ride.getPax() + pax);
//
//        // Update het aantal gereserveerde plekken per gebruiker
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});
//            reservedSpotsByUser.put(username, pax);
//            ride.setReservedSpotsByUser(objectMapper.writeValueAsString(reservedSpotsByUser));
//        } catch (IOException e) {
//            // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
//            // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
//            throw new RuntimeException(e);
//        }
//
//        userRepository.save(user);
//        rideRepository.save(ride);
//    }
    //deze code was goed 9/6!

//    public void addUserToRide(Long id, String username, int pax) {
//        var optionalRide = rideRepository.findById(id);
//        var optionalUser = userRepository.findByUsername(username);
//
//        if (!optionalRide.isPresent()) {
//            throw new RecordNotFoundException("Ride with id " + id + " not found.");
//        }
//
//        if (!optionalUser.isPresent()) {
//            throw new RecordNotFoundException("User with username " + username + " not found.");
//        }
//
//        var ride = optionalRide.get();
//        var user = optionalUser.get();
//
//        // Controleer of er voldoende plekken beschikbaar zijn
//        if (ride.getAvailableSpots() < pax) {
//            throw new ExceededCapacityException("The number of passengers exceeds the available spots");
//        }
//
//        // Controleer of de gebruiker al aan de rit is toegevoegd
//        if (ride.getUsers().contains(user)) {
//            throw new UserAlreadyAddedToRideException("User already added to this ride");
//        }
//
//        user.getRides().add(ride);
//        ride.getUsers().add(user);
//
//        // Verminder het aantal beschikbare plekken
//        ride.setAvailableSpots(ride.getAvailableSpots() - pax);
//
//        // Verhoog het totale ritprijs op basis van het aantal passagiers en de prijs per persoon
//        ride.setTotalRitPrice(ride.getTotalRitPrice() + (ride.getPricePerPerson() * pax));
//
//        // Verhoog het aantal reserveringen
//        ride.setPax(ride.getPax() + pax);
//
//        // Update het aantal gereserveerde plekken per gebruiker
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});
//            reservedSpotsByUser.put(username, pax);
//            ride.setReservedSpotsByUser(objectMapper.writeValueAsString(reservedSpotsByUser));
//        } catch (IOException e) {
//            // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
//            // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
//            throw new RuntimeException(e);
//        }
//
//        if (!ride.getDriverUsername().equals(user.getUsername())) {
//            // Verstuur een notificatie naar de bestuurder van de rit na het toevoegen van de passagier
//            User sender = userRepository.findByUsername("System").orElseThrow(() -> new UserNotFoundException("System user not found")); // Systeem is de afzender van de notificatie
//            User receiver = userRepository.findByUsername(ride.getDriverUsername()).orElseThrow(() -> new UserNotFoundException("Driver not found")); // Je moet een manier hebben om de bestuurder van de rit te krijgen
//
//            NotificationDto notificationDto = new NotificationDto();
//            notificationDto.setSender(dtoMapperService.userToDto(sender));
//            notificationDto.setReceiver(dtoMapperService.userToDto(receiver));
//            notificationDto.setType(NotificationType.PASSENGER_JOINED_RIDE);
//            notificationDto.setSentDate(LocalDateTime.now()); // Stel de datum en tijd in op nu
//            notificationDto.setRead(false); // De notificatie is nog niet gelezen
//            notificationDto.setRideId(ride.getId()); // Stel het rideId in
//
//            notificationService.createNotification(notificationDto); // Verstuur de notificatie
//        }
//        userRepository.save(user);
//        rideRepository.save(ride);
//    }

    public void addUserToRide(Long id, String username, int pax) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findByUsername(username);

        if(!optionalRide.isPresent()) {
            throw new RecordNotFoundException("Ride with id " + id + " not found.");
        }

        if(!optionalUser.isPresent()) {
            throw new RecordNotFoundException("User with username " + username + " not found.");
        }

        var ride = optionalRide.get();
        var user = optionalUser.get();

        // Controleer of er voldoende plekken beschikbaar zijn
        if (ride.getAvailableSpots() < pax) {
            throw new ExceededCapacityException("The number of passengers exceeds the available spots");
        }

        // Controleer of de gebruiker al aan de rit is toegevoegd
        if (ride.getUsers().contains(user)) {
            throw new UserAlreadyAddedToRideException("User already added to this ride");
        }

        user.getRides().add(ride);
        ride.getUsers().add(user);

        // Verminder het aantal beschikbare plekken
        ride.setAvailableSpots(ride.getAvailableSpots() - pax);

        // Verhoog het totale ritprijs op basis van het aantal passagiers en de prijs per persoon
        ride.setTotalRitPrice(ride.getTotalRitPrice() + (ride.getPricePerPerson() * pax));

        // Verhoog het aantal reserveringen
        ride.setPax(ride.getPax() + pax);

        // Update het aantal gereserveerde plekken per gebruiker
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});
            reservedSpotsByUser.put(username, pax);
            ride.setReservedSpotsByUser(objectMapper.writeValueAsString(reservedSpotsByUser));
        } catch (IOException e) {
            // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
            // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
            throw new RuntimeException(e);
        }

        // Send a notification to the ride's driver after adding the passenger
        if (!ride.getDriverUsername().equals(user.getUsername())) {
            User sender = userRepository.findByUsername("System").orElseThrow(() -> new UserNotFoundException("System user not found")); // System is the sender of the notification
            User receiver = userRepository.findByUsername(ride.getDriverUsername()).orElseThrow(() -> new UserNotFoundException("Driver not found")); // You need a way to get the ride's driver

            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setSender(dtoMapperService.userToDto(sender));
            notificationDto.setReceiver(dtoMapperService.userToDto(receiver));
            notificationDto.setType(NotificationType.PASSENGER_JOINED_RIDE);
            notificationDto.setSentDate(LocalDateTime.now()); // Set the date and time to now
            notificationDto.setRead(false); // The notification has not been read yet
            notificationDto.setRideId(ride.getId()); // Set the rideId

            notificationService.createNotification(notificationDto); // Send the notification
        }

        if (!ride.getDriverUsername().equals(user.getUsername())) {
            // Send a notification to the passenger after they join the ride
            User sender = userRepository.findByUsername("System").orElseThrow(() -> new UserNotFoundException("System user not found")); // System is the sender of the notification

            NotificationDto passengerNotificationDto = new NotificationDto();
            passengerNotificationDto.setSender(dtoMapperService.userToDto(sender));
            passengerNotificationDto.setReceiver(dtoMapperService.userToDto(user)); // The receiver is the passenger
            passengerNotificationDto.setType(NotificationType.RIDE_CONFIRMATION);
            passengerNotificationDto.setSentDate(LocalDateTime.now()); // Set the date and time to now
            passengerNotificationDto.setRead(false); // The notification has not been read yet
            passengerNotificationDto.setRideId(ride.getId()); // Set the rideId

            notificationService.createNotification(passengerNotificationDto); // Send the notification
        }

        userRepository.save(user);
        rideRepository.save(ride);
    }






//    public int getReservedSpotsForUser(Long rideId, String username) {
//        Optional<Ride> rideOptional = rideRepository.findById(rideId);
//        if (!rideOptional.isPresent()) {
//            throw new RecordNotFoundException("Ride not found");
//        }
//
//        Ride ride = rideOptional.get();
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (!userOptional.isPresent()) {
//            throw new RecordNotFoundException("User not found");
//        }
//
//        User user = userOptional.get();
//        if (!ride.getUsers().contains(user)) {
//            throw new UserNotInRideException("User is not part of this ride");
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            // Converteer de JSON string naar een Map
//            Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});
//
//            // Haal het aantal gereserveerde zitplaatsen voor de gebruiker op
//            Integer reservedSpots = reservedSpotsByUser.get(username);
//            if (reservedSpots == null) {
//                throw new UserNotInRideException("User is not part of this ride");
//            }
//
//            return reservedSpots;
//        } catch (IOException e) {
//            // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
//            // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
//            throw new RuntimeException(e);
//        }
//    }


        public ReservationInfo getReservationInfoForUser(Long rideId, String username) {
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

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Converteer de JSON string naar een Map
                Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});

                // Haal het aantal gereserveerde zitplaatsen voor de gebruiker op
                Integer reservedSpots = reservedSpotsByUser.get(username);
                if (reservedSpots == null) {
                    throw new UserNotInRideException("User is not part of this ride");
                }

                // Bereken de totale prijs die de gebruiker moet betalen voor zijn gereserveerde plekken
                double totalPrice = ride.getPricePerPerson() * reservedSpots;

                return new ReservationInfo(reservedSpots, totalPrice);
            } catch (IOException e) {
                // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
                // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
                throw new RuntimeException(e);
            }
        }
/////



//public List<RideDto> getRidesByCriteria(
//        Optional<String> destination,
//        Optional<String> pickUpLocation,
////        Optional<LocalDate> departureDate)
//        Optional<LocalDateTime> departureDateTime)
//
//{
//
//    List<Ride> rideList;
//
//    if (destination.isPresent() && pickUpLocation.isPresent() && departureDateTime.isPresent()) {
//        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCaseAndDepartureDateTimeEquals(
//                destination.get(),
//                pickUpLocation.get(),
//                departureDateTime.get()
//        );
//    } else if (destination.isPresent() && pickUpLocation.isPresent()) {
//        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase(
//                destination.get(),
//                pickUpLocation.get()
//        );
//    } else if (destination.isPresent() && departureDateTime.isPresent()) {
//        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndDepartureDateTimeEquals(
//                destination.get(),
//                departureDateTime.get()
//        );
//    } else if (pickUpLocation.isPresent() && departureDateTime.isPresent()) {
//        rideList = rideRepository.findAllRidesByPickUpLocationEqualsIgnoreCaseAndDepartureDateTimeEquals(
//                pickUpLocation.get(),
//                departureDateTime.get()
//        );
//    } else if (destination.isPresent()) {
//        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCase(destination.get());
//    } else if (pickUpLocation.isPresent()) {
//        rideList = rideRepository.findAllRidesByPickUpLocationEqualsIgnoreCase(pickUpLocation.get());
//    } else if (departureDateTime.isPresent()) {
//        rideList = rideRepository.findAllRidesByDepartureDateTimeEquals(departureDateTime.get());
//    } else {
//        rideList = rideRepository.findAll();
//    }
//
//    return transferRideListToDtoList(rideList);
//}

    public List<RideDto> getRidesByCriteria(
            String destination,
            String pickUpLocation,
            LocalDate departureDate,
            int pax)
    {
        List<Ride> rideList;

        rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase(
                destination,
                pickUpLocation
        );

        List<Ride> availableRideList = new ArrayList<>();
        for (Ride ride : rideList) {
            if (departureDate == null || ride.getDepartureDateTime().toLocalDate().equals(departureDate)) {
                if (ride.getAvailableSpots() >= pax) {
                    availableRideList.add(ride);
                }
            }
        }

        return transferRideListToDtoList(availableRideList);
    }

    public List<RideDto> transferRideListToDtoList(List<Ride> rides){
        List<RideDto> rideDtoList = new ArrayList<>();

        for(Ride ride : rides) {
//            RideDto dto = transferToDto(ride);
            RideDto dto = dtoMapperService.rideToDto(ride);

            rideDtoList.add(dto);
        }
        return rideDtoList;
    }


//    public void deleteRide(Long id) {
//        Optional<Ride> optionalRide = rideRepository.findById(id);
//
//        if (optionalRide.isPresent()) {
//            Ride ride = optionalRide.get();
//
//            // Verwijder de rit uit de lijst van ritten voor elke gerelateerde gebruiker
//            for (User user : ride.getUsers()) {
//                user.getRides().remove(ride);
//                userRepository.save(user); // Sla de bijgewerkte User entiteit op
//            }
//
//            // Verwijder de Ride entiteit
//            rideRepository.deleteById(id);
//        } else {
//            throw new RecordNotFoundException("Rit niet gevonden");
//        }
//    }
//    hierboven 9/6 werkte goed!

    public void deleteRide(Long id) {
        Optional<Ride> optionalRide = rideRepository.findById(id);

        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();

            // Stuur een notificatie naar elke gerelateerde gebruiker voordat de rit wordt verwijderd
            for (User user : ride.getUsers()) {
                // Verstuur de notificatie alleen naar passagiers, niet naar de bestuurder
                if (!user.getUsername().equals(ride.getDriverUsername())) {
                    User sender = userRepository.findByUsername("System").orElseThrow(() -> new UserNotFoundException("System user not found")); // System is de verzender van de notificatie

                    NotificationDto notificationDto = new NotificationDto();
                    notificationDto.setSender(dtoMapperService.userToDto(sender));
                    notificationDto.setReceiver(dtoMapperService.userToDto(user));
                    notificationDto.setType(NotificationType.RIDE_CANCELLED_BY_DRIVER);
                    notificationDto.setSentDate(LocalDateTime.now()); // Stel de datum en tijd in op nu
                    notificationDto.setRead(false); // De notificatie is nog niet gelezen
                    notificationDto.setRideId(ride.getId()); // Stel de rideId in

                    notificationService.createNotification(notificationDto, ride); // Verstuur de notificatie
                }

                // Verwijder de rit uit de lijst van ritten voor elke gerelateerde gebruiker
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
            Ride ride1 = dtoMapperService.dtoToRide(newRide);
            ride1.setId(ride.getId());

            rideRepository.save(ride1);

//            return transferToDto(ride1);
            return dtoMapperService.rideToDto(ride1);

        } else {

            throw new  RecordNotFoundException("geen rit gevonden");

        }
    }

    ///
//    public void removeUserFromRide(Long rideId, String username) {
//        Optional<Ride> rideOptional = rideRepository.findById(rideId);
//        if (!rideOptional.isPresent()) {
//            throw new RecordNotFoundException("Ride not found");
//        }
//
//        Ride ride = rideOptional.get();
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (!userOptional.isPresent()) {
//            throw new RecordNotFoundException("User not found");
//        }
//
//        User user = userOptional.get();
//        if (!ride.getUsers().contains(user)) {
//            throw new UserNotInRideException("User is not part of this ride");
//        }
//
//        ride.getUsers().remove(user);
//        user.getRides().remove(ride);
//        rideRepository.save(ride);
//        userRepository.save(user);
//    }
    //nieuwe 6-6:
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

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Converteer de JSON string naar een Map
            Map<String, Integer> reservedSpotsByUser = objectMapper.readValue(ride.getReservedSpotsByUser(), new TypeReference<Map<String, Integer>>() {});

            // Haal het aantal gereserveerde zitplaatsen voor de gebruiker op
            Integer reservedSpots = reservedSpotsByUser.get(username);
            if (reservedSpots == null) {
                throw new UserNotInRideException("User is not part of this ride");
            }

            if (ride.getPax() < reservedSpots) {
                throw new IllegalStateException("The number of reserved spots exceeds the total number of reservations");
            }

            // Update het aantal beschikbare zitplaatsen
            ride.setAvailableSpots(ride.getAvailableSpots() + reservedSpots);

            // Verminder het totale aantal reserveringen (pax)
            ride.setPax(ride.getPax() - reservedSpots);

            // Verminder het totale ritprijs op basis van het aantal gereserveerde plekken voor de gebruiker en de prijs per persoon
            ride.setTotalRitPrice(ride.getTotalRitPrice() - (ride.getPricePerPerson() * reservedSpots));

            // Verwijder de gebruiker uit de rit en de rit uit de gebruiker
            ride.getUsers().remove(user);
            user.getRides().remove(ride);

            // Verwijder de vermelding voor deze gebruiker uit de Map van gereserveerde zitplaatsen
            reservedSpotsByUser.remove(username);
            // Converteer de Map weer naar een JSON String
            ride.setReservedSpotsByUser(objectMapper.writeValueAsString(reservedSpotsByUser));

            rideRepository.save(ride);
            userRepository.save(user);

        } catch (IOException e) {
            // Dit is een RuntimeException omdat ObjectMapper.readValue IOException kan gooien.
            // Dit zou alleen gebeuren als er iets mis is met de JSON String die we uit de database krijgen.
            throw new RuntimeException(e);
        }
    }

}
