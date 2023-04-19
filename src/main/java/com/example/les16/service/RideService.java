package com.example.les16.service;

import com.example.les16.dto.RideDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Ride;
import com.example.les16.model.User;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RideService {
//    @Autowired
//    private UserService userService;


    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public RideService(RideRepository rideRepository, UserRepository userRepository) {
        this.rideRepository = rideRepository;
        this.userRepository = userRepository;
    }

    public RideDto addRide(RideDto rideDto) {

        Ride newRide = transferToRide(rideDto);
        rideRepository.save(newRide);
        rideDto.setId(newRide.getId());

        return rideDto;

    }


    public RideDto getRideById(Long id) {
        Optional<Ride> ride = rideRepository.findById(id);
        if(ride.isPresent()) {
            return transferToDto(ride.get());
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

        // test 13/4: omzetten van de User objecten naar UserDto objecten
//        List<UserDto> userDtos = ride.getUsers().stream()
//                .map(UserService::transferToDto)
//                .collect(Collectors.toList());
//        dto.setUsers(userDtos);

        return dto;
    }
// hieronder vanavond verder gegaan. is dit inderdaad nodig?
    public void addUserToRide(Long id, String username) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findByUsername(username);

        if(optionalRide.isPresent() && optionalUser.isPresent()) {
            var ride = optionalRide.get();
            var user = optionalUser.get();

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
            RideDto dto = transferToDto(ride);
//            if(ride.getPassengers() != null){
//                dto.setPassengers(passengerService.transferToDto(ride.getPassengers()));
//            }
//            if(ride.getRemoteController() != null){
//                dto.setRemoteControllerDto(remoteControllerService.transferToDto(ride.getRemoteController()));
//            }
            rideDtoList.add(dto);
        }
        return rideDtoList;
    }

    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }

    public RideDto updateRide(Long id, RideDto newRide) {
        if (rideRepository.findById(id).isPresent()){

            Ride ride = rideRepository.findById(id).get();

            Ride ride1 = transferToRide(newRide);
            ride1.setId(ride.getId());
//deze hierboven geprobeert weg te halen
            rideRepository.save(ride1);

            return transferToDto(ride1);

        } else {

            throw new  RecordNotFoundException("geen rit gevonden");

        }
    }
}
