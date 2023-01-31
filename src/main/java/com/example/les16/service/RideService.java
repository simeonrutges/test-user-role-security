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
    public RideDto addRide(RideDto rideDto) {

        Ride newRide = transferToRide(rideDto);
        rideRepository.save(newRide);

//        return rideDto;

        return rideDto;

    }

//    public RideDto addUserToRide(String username, Long rideId)  {
//        UserRide userRide = new UserRide(username, rideId);
//        UserRideRepository.save(userRide);
//
//        return
//    }

    public Ride transferToRide(RideDto rideDto){
        var ride = new Ride();

        ride.setId(rideDto.getId());
        ride.setDestination(rideDto.getDestination());
        ride.setPickUpLocation(rideDto.getPickUpLocation());
        ride.setRoute(rideDto.getRoute());
        ride.setAddRideInfo(rideDto.getAddRideInfo());
        ride.setDepartureTime(rideDto.getDepartureTime());
        ride.setPricePerPerson(rideDto.getPricePerPerson());
        ride.setTotalRitPrice(rideDto.getTotalRitPrice());
        ride.setAvailableSpots(rideDto.getAvailableSpots());
        ride.setAutomaticAcceptance(rideDto.isAutomaticAcceptance());

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

    public List<RideDto> getAllRidesByDestination(String destination) {
        List<Ride> rideList = rideRepository.findAllRidesByDestinationEqualsIgnoreCase(destination);
        return transferRideListToDtoList(rideList);
    }
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

            rideRepository.save(ride1);

            return transferToDto(ride1);

        } else {

            throw new  RecordNotFoundException("geen rit gevonden");

        }
    }
}
