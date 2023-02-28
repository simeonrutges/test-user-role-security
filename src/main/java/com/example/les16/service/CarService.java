package com.example.les16.service;

import com.example.les16.dto.CarDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Car;
import com.example.les16.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDto> getAllCars() {
        List<CarDto> dtos = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
        for (Car rc : cars) {
            dtos.add(transferToDto(rc));
        }
        return dtos;
    }

    public CarDto getCar(long id) {
        Optional<Car> car = carRepository.findById(id);
        if(car.isPresent()) {
            return transferToDto(car.get());
        } else {
            throw new RecordNotFoundException("No car found");
        }
    }

    public CarDto addCar(CarDto carDto) {
        Car rc =  transferToCar(carDto);
        carRepository.save(rc);
        return carDto;
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public void updateCar(Long id, CarDto carDto) {
        if(!carRepository.existsById(id)) {
            throw new RecordNotFoundException("No car found");
        }
        Car storedCar = carRepository.findById(id).orElse(null);
        // deze weggehaalt met Mark. Anders werkte de put niet
//        storedCar.setId(carDto.getId());
        storedCar.setLicensePlate(carDto.getLicensePlate());
        storedCar.setModel(carDto.getModel());
        storedCar.setBrand(carDto.getBrand());
        carRepository.save(storedCar);
    }

    public Car transferToCar(CarDto carDto){
        var car = new Car();

        car.setId(carDto.getId());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setModel(carDto.getModel());
        car.setBrand(carDto.getBrand());

        return car;
    }
    public CarDto transferToDto(Car car){
        var dto = new CarDto();

        dto.id = car.getId();
        dto.licensePlate = car.getLicensePlate();
        dto.model = car.getModel();
        dto.brand = car.getBrand();

        return dto;
    }
}
