package nl.novi.automate.controller;

import nl.novi.automate.dto.CarDto;
import nl.novi.automate.model.Car;
import nl.novi.automate.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @PostMapping("")
    public ResponseEntity<CarDto> addCar(@Valid @RequestBody CarDto carDto, Authentication authentication) {
        String username = authentication.getName(); // verkrijg de gebruikersnaam van de ingelogde gebruiker
        CarDto addedCar = carService.addCar(carDto, username); // geef de gebruikersnaam door aan de CarService
//        return ResponseEntity.ok(addedCar); 29/5
        return new ResponseEntity<>(addedCar, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //29-5
    public void deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getCarByUser(@PathVariable("username") String username) {
        try {
            Car car = carService.getCarByUser(username);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
