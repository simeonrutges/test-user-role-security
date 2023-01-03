package com.example.les16.service;

import com.example.les16.dto.RideDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Ride;
import com.example.les16.model.User;
import com.example.les16.repository.RideRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
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

        return rideDto;
    }

    public Ride transferToRide(RideDto dto){
        var ride = new Ride();

        ride.setId(dto.getId());
        ride.setDestination(dto.getDestination());
        ride.setPickUpLocation(dto.getPickUpLocation());
        ride.setRoute(dto.getRoute());
        ride.setAddRideInfo(dto.getAddRideInfo());
        ride.setDepartureTime(dto.getDepartureTime());
        ride.setPricePerPerson(dto.getPricePerPerson());
        ride.setTotalRitPrice(dto.getTotalRitPrice());
        ride.setAvailableSpots(dto.getAvailableSpots());
        ride.setAutomaticAcceptance(dto.isAutomaticAcceptance());

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

        return dto;
    }
// hieronder vanavond verder gegaan. is dit inderdaad nodig?
    public void assignUserToRide(Long id, String username) {
        var optionalRide = rideRepository.findById(id);
        var optionalUser = userRepository.findById(username);

        if(optionalRide.isPresent() && optionalUser.isPresent()) {
            var ride = optionalRide.get();
            var user = optionalUser.get();

            ride.setUsers((Collection<User>) user);
            rideRepository.save(ride);
        } else {
            throw new RecordNotFoundException();
        }
    }
}
