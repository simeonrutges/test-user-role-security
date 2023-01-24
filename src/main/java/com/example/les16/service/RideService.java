package com.example.les16.service;

import com.example.les16.dto.RideDto;
import com.example.les16.dto.UserDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Ride;
import com.example.les16.model.Role;
import com.example.les16.model.User;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {
//    private final RideRepository rideRepository;
//
//    private final DriverProfileRepository driverProfileRepository;
//
//    private final DriverProfileService driverProfileService;
//
//    public RideService(RideRepository rideRepository) {
//        this.rideRepository = rideRepository;
//    }

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;


    public RideDto getRideById(Long id) {
        Optional<Ride> ride = rideRepository.findById(id);
        if(ride.isPresent()) {
            return transferToDto(ride.get());
        } else {
            throw new RecordNotFoundException("No car found");
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
    public RideDto addRide(RideDto rideDto) {

        Ride newRide = transferToRide(rideDto);
        rideRepository.save(newRide);

//        return rideDto;

        return transferToDto(newRide);

    }

    public Ride transferToRide(RideDto rideDto){
        var ride = new Ride();

//        ride.setId(rideDto.getId());
        ride.setDestination(rideDto.getDestination());
        ride.setPickUpLocation(rideDto.getPickUpLocation());
        ride.setRoute(rideDto.getRoute());
        ride.setAddRideInfo(rideDto.getAddRideInfo());
        ride.setDepartureTime(rideDto.getDepartureTime());
        ride.setPricePerPerson(rideDto.getPricePerPerson());
        ride.setTotalRitPrice(rideDto.getTotalRitPrice());
        ride.setAvailableSpots(rideDto.getAvailableSpots());
        ride.setAutomaticAcceptance(rideDto.isAutomaticAcceptance());
/////
//        List<User> userRides = new ArrayList<>();
//        for (UserDto user : rideDto.users) {
//            Optional<User> ou = userRepository.findByUsername(user.getUsername());
//
//            userRides.add(ou.get());
//        }
//        ride.setUsers(userRides);
        ///// tot hier erbij gezet
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
        dto.pricePerPerson = ride.getPricePerPerson();
        dto.totalRitPrice = ride.getTotalRitPrice();
        dto.availableSpots = ride.getAvailableSpots();
        dto.automaticAcceptance = ride.isAutomaticAcceptance();

//        if(ride.getUsers() !=null){
//            dto.setUsers(UserService.fromUser(ride.getUsers());
//        }

        return dto;
    }
// hieronder vanavond verder gegaan. is dit inderdaad nodig?
//    public void assignUserToRide(Long id, String username) {
//        var optionalRide = rideRepository.findById(id);
//        var optionalUser = userRepository.findById(username);
//
//        if(optionalRide.isPresent() && optionalUser.isPresent()) {
//            var ride = optionalRide.get();
//            var user = optionalUser.get();
//
//            ride.setUsers((Collection<User>) user);
//            rideRepository.save(ride);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }
}
