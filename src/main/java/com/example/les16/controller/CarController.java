package com.example.les16.controller;

import com.example.les16.dto.CarDto;
import com.example.les16.model.Car;
import com.example.les16.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("")
    public List<CarDto> getAllCars() {

        List<CarDto> dtos = carService.getAllCars();

        return dtos;
    }

    @GetMapping("/{id}")
    public CarDto getCar(@PathVariable("id") Long id) {

        CarDto dto = carService.getCar(id);

        return dto;
    }
//////
    @GetMapping("/user/{username}")
    public ResponseEntity<Car> getCarByUser(@PathVariable("username") String username) {
        Car car = carService.getCarByUser(username);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//////

//    @PostMapping("")
//    public Object addCar(@Valid @RequestBody CarDto dto, BindingResult br) {
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
//            CarDto dto1 = carService.addCar(dto);
//            return dto1;
//        }
//    }

//    deze werkt goed!:
//    @PostMapping("")
//    public ResponseEntity<Object> addCar(@Valid @RequestBody CarDto dto) {
//        CarDto dto1 = carService.addCar(dto);
//        return ResponseEntity.ok(dto1);
//    }

    @PostMapping("")
    public ResponseEntity<CarDto> addCar(@Valid @RequestBody CarDto carDto, Authentication authentication) {
        String username = authentication.getName(); // verkrijg de gebruikersnaam van de ingelogde gebruiker
        CarDto addedCar = carService.addCar(carDto, username); // geef de gebruikersnaam door aan de CarService
        return ResponseEntity.ok(addedCar);
    }


// dit is de juiste!:
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
    }

//@DeleteMapping("/{carId}")
//public ResponseEntity<Void> deleteCar(@PathVariable Long carId) {
//    carService.deleteCar(carId);
//    return ResponseEntity.noContent().build();
//}



    //    ALles werkt behalvde de PUT
    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable("id") Long id, @RequestBody CarDto dto) {
        carService.updateCar(id, dto);
        return dto;
    }
    // car was "remoteController"
}
