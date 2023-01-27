package com.example.les16.controller;

import com.example.les16.dto.RideDto;
import com.example.les16.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//@CrossOrigin
@RestController
@RequestMapping(value = "/rides")
public class RideController {
    private final RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }


    @PostMapping("")
            public RideDto addRide(@RequestBody RideDto dto){
//    public RideDto addRide(@RequestBody RideDto dto){
        RideDto dto1 = rideService.addRide(dto);
//        Rit savedRit =  rideService.save(ritDto);


        return dto1;
    }

    @PostMapping("/{username}/{rideId}")
    public RideDto addUserToRide(@PathVariable String username, @PathVariable Long rideId){
        RideDto dto2 = rideService.addUserToRide(username, rideId);
        return dto2;
    }
    // tot hier werkt het... hier verder gegaan

//    @PutMapping("/{id}/{username}")
//    public void assignUserToRide(@PathVariable("id") Long id, @PathVariable("username") String username) {
//        rideService.assignUserToRide(id, username);
//    }
}
