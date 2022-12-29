package com.example.les16.controller;

import com.example.les16.dto.CarDto;
import com.example.les16.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    @Autowired
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
    public CarDto addCar(@RequestBody CarDto dto) {
        CarDto dto1 = carService.addCar(dto);
        return dto1;
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
