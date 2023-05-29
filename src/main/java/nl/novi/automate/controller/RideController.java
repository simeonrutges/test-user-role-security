package nl.novi.automate.controller;

import nl.novi.automate.dto.RideDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserAlreadyAddedToRideException;
import nl.novi.automate.exceptions.UserNotInRideException;
import nl.novi.automate.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

//    @PostMapping("/{rideId}/{username}")
//    public ResponseEntity<Object> addUserToRide(@PathVariable Long rideId, @PathVariable String username){
//        rideService.addUserToRide(rideId, username);
//        return ResponseEntity.ok().build();
//    }

//9/5:
    @PostMapping("/{rideId}/{username}")
    public ResponseEntity<?> addUserToRide(@PathVariable Long rideId, @PathVariable String username) {
        try {
            rideService.addUserToRide(rideId, username);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyAddedToRideException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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

//@GetMapping("")
//public ResponseEntity<List<RideDto>> getRidesByCriteria(
//        @RequestParam(value = "destination", required = false) Optional<String> destination,
//        @RequestParam(value = "pickUpLocation", required = false) Optional<String> pickUpLocation,
////        @RequestParam(value = "departureDate", required = false) Optional<LocalDate> departureDate)
//        @RequestParam(value = "departureDateTime", required = false) Optional<LocalDateTime> departureDateTime)
//{
//    List<RideDto> dtos = rideService.getRidesByCriteria(destination, pickUpLocation, departureDateTime);
//
//    return ResponseEntity.ok().body(dtos);
//}

    //hierboven was de originele!!!! 24-05!!!


    @GetMapping("")
    public ResponseEntity<List<RideDto>> getRidesByCriteria(
            @RequestParam(value = "destination", required = false) Optional<String> destination,
            @RequestParam(value = "pickUpLocation", required = false) Optional<String> pickUpLocation,
            @RequestParam(value = "departureDateTime", required = false) Optional<String> departureDateTimeStr)
    {
        Optional<LocalDateTime> departureDateTime = Optional.empty();
        if(departureDateTimeStr.isPresent()){
            try{
                departureDateTime = Optional.of(LocalDateTime.parse(departureDateTimeStr.get(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }catch(DateTimeParseException e){
                // handle invalid date-time format
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date-time format.");
            }
        }

        List<RideDto> dtos = rideService.getRidesByCriteria(destination, pickUpLocation, departureDateTime);

        return ResponseEntity.ok().body(dtos);
    }


    ////// tot hier nieuw  24/05





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

    //test 24/4:

    ////10-5
    @DeleteMapping("/{rideId}/users/{username}")
    public ResponseEntity<?> removeUserFromRide(@PathVariable Long rideId, @PathVariable String username) {
        try {
            rideService.removeUserFromRide(rideId, username);
            return ResponseEntity.ok().build();
        } catch (UserNotInRideException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (RecordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}