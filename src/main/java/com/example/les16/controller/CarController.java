package com.example.les16.controller;

import com.example.les16.dto.CarDto;
import com.example.les16.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin
//@CrossOrigin
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

    @PostMapping("")
    public Object addCar(@Valid @RequestBody CarDto dto, BindingResult br) {
        if (br.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField() + ": ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        } else {

            CarDto dto1 = carService.addCar(dto);
            return dto1;
        }
    }


    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
    }

//    ALles werkt behalvde de PUT
    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable("id") Long id, @RequestBody CarDto dto) {
        carService.updateCar(id, dto);
        return dto;
    }
    // car was "remoteController"
}
