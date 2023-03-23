package com.example.les16.controller;

import com.example.les16.dto.RideDto;
import com.example.les16.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/rides")
public class RideController {
    private final RideService rideService;
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

//    @PostMapping("")
//            public Object addRide(@Valid @RequestBody RideDto dto, BindingResult br) {
//        if (br.hasErrors()) {
//            StringBuilder sb = new StringBuilder();
//            for (FieldError fe : br.getFieldErrors()) {
//                sb.append(fe.getField() + ": ");
//                sb.append(fe.getDefaultMessage());
//                sb.append("\n");
//            }
//            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
//        } else {
//
//
////    public RideDto addRide(@RequestBody RideDto dto){
//            RideDto dto1 = rideService.addRide(dto);
////        Rit savedRit =  rideService.save(ritDto);
//
//
//            return dto1;
//        }
//    }

    @PostMapping("")
    public ResponseEntity<RideDto> addRide(@Valid @RequestBody RideDto dto, BindingResult br) {
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(null);
        } else {
            RideDto addedRideDto = rideService.addRide(dto);
            return ResponseEntity.ok(addedRideDto);
        }
    }

    @PostMapping("/{rideId}/{username}")
    public ResponseEntity<Object> addUserToRide(@PathVariable Long rideId, @PathVariable String username){
        rideService.addUserToRide(rideId, username);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("")
//    public ResponseEntity<List<RideDto>> getAllRides(@RequestParam(value = "destination", required = true) Optional<String> destination) {
//
//        List<RideDto> dtos;
//        dtos = rideService.getAllRidesByDestination(destination.get());
//
//        return ResponseEntity.ok().body(dtos);
//    }

//    @GetMapping("")
//    public ResponseEntity<List<RideDto>> getRidesByCriteria(
//            @RequestParam(value = "destination", required = false) Optional<String> destination,
//            @RequestParam(value = "pickUpLocation", required = false) Optional<String> pickUpLocation,
//            @RequestParam(value = "departureDate", required = false) Optional<LocalDate> departureDate ) {
//
//        List<RideDto> dtos;
//
//        if (destination.isPresent() && pickUpLocation.isPresent() && departureDate.isPresent()) {
//            dtos = rideService.getRidesByDestinationAndPickUpLocationAndDepartureDate(
//                    destination.get(),
//                    pickUpLocation.get(),
//                    departureDate.get()
//            );
//        } else if (destination.isPresent() && pickUpLocation.isPresent()) {
//            dtos = rideService.getRidesByDestinationAndPickUpLocation(
//                    destination.get(),
//                    pickUpLocation.get()
//            );
//        } else if (destination.isPresent() && departureDate.isPresent()) {
//            dtos = rideService.getRidesByDestinationAndDepartureDate(
//                    destination.get(),
//                    departureDate.get()
//            );
//        } else if (pickUpLocation.isPresent() && departureDate.isPresent()) {
//            dtos = rideService.getRidesByPickUpLocationAndDepartureDate(
//                    pickUpLocation.get(),
//                    departureDate.get()
//            );
//        } else if (destination.isPresent()) {
//            dtos = rideService.getRidesByDestination(destination.get());
//        } else if (pickUpLocation.isPresent()) {
//            dtos = rideService.getRidesByPickUpLocation(pickUpLocation.get());
//        } else if (departureDate.isPresent()) {
//            dtos = rideService.getRidesByDepartureDate(departureDate.get());
//        } else {
//            dtos = rideService.getAllRides();
//        }
//
//        return ResponseEntity.ok().body(dtos);
//    }
@GetMapping("")
public ResponseEntity<List<RideDto>> getRidesByCriteria(
        @RequestParam(value = "destination", required = false) Optional<String> destination,
        @RequestParam(value = "pickUpLocation", required = false) Optional<String> pickUpLocation,
        @RequestParam(value = "departureDate", required = false) Optional<LocalDate> departureDate ) {

    List<RideDto> dtos = rideService.getRidesByCriteria(destination, pickUpLocation, departureDate);

    return ResponseEntity.ok().body(dtos);
}


    @GetMapping("/{id}")
    public ResponseEntity<RideDto> getRide(@PathVariable("id")Long id) {

        RideDto ride = rideService.getRideById(id);

        return ResponseEntity.ok().body(ride);
        // Deze nog niet getest in Postman. Niet zeker of deze nodig is

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRide(@PathVariable Long id) {

        rideService.deleteRide(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRide(@PathVariable Long id, @RequestBody RideDto newRide) {

        RideDto dto = rideService.updateRide(id, newRide);

        return ResponseEntity.ok().body(dto);

    }



//    @PutMapping("/{id}/{username}")
//    public void assignUserToRide(@PathVariable("id") Long id, @PathVariable("username") String username) {
//        rideService.assignUserToRide(id, username);
//    }
}
