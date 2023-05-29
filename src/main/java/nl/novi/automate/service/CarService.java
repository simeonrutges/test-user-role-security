package nl.novi.automate.service;

import nl.novi.automate.dto.CarDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.model.Car;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

public CarDto addCar(CarDto carDto, String username) {
//    Optional<User> userOptional = userRepository.findByUsername(username);
//    if (userOptional.isPresent()) {
//        User user = userOptional.get();
//        Car car = transferToCar(carDto);
//        car.setUser(user);  // koppel de auto aan de gebruiker
//        Car savedCar = carRepository.save(car);
//        user.setCar(savedCar); // dit is nodig om de bidirectionele relatie te behouden
//        userRepository.save(user);
//        return transferToDto(savedCar);
//    } else {
//        throw new EntityNotFoundException("User not found with username: " + username);
//    }
//}

    // 29/5 hieronder de nieuwe code. Was de code hierboven die goed werkte. testen
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));

    Car car = transferToCar(carDto);
    car.setUser(user);
    Car savedCar = carRepository.save(car);
    user.setCar(savedCar);
    userRepository.save(user);

    return transferToDto(savedCar);
}

    // nu mee bezig: werkt wek, maar niet de juiste timing met FE:
    public void removeCarFromUser(Long carId) {
        User user = userRepository.findByCarId(carId);
        if (user != null) {
            user.setCar(null);
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteCar(Long carId) {
        removeCarFromUser(carId);
        carRepository.deleteById(carId);
    }

//public void deleteCar(Long carId) {
//    var optionalCar = carRepository.findById(carId);
//
//    if (optionalCar.isPresent()) {
//        var car = optionalCar.get();
//        var user = car.getUser();
//
//        if (user != null) {
//            user.setCar(null);
//            userRepository.save(user);
//        }
//
//        carRepository.deleteById(carId);
//    } else {
//        throw new RecordNotFoundException();
//    }
//}




    public Car getCarByUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getCar();
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
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
