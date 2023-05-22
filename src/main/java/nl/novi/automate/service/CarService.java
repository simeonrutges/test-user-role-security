package nl.novi.automate.service;

import nl.novi.automate.dto.CarDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.model.Car;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
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

//    public CarDto addCar(CarDto carDto) {
//        Car rc =  transferToCar(carDto);
//        carRepository.save(rc);
//        return carDto;
//    }

//    deze werkt goed:
//public CarDto addCar(CarDto carDto) {
//    Car rc =  transferToCar(carDto);
//    carRepository.save(rc);
//    CarDto addedCar = transferToDto(rc);
//    return addedCar;
//}

//    public CarDto addCar(CarDto carDto, String username) {
//        Car rc =  transferToCar(carDto);
//        rc = carRepository.save(rc);
//        Optional<User> user = userRepository.findByUsername(username);
//        if (user.isPresent()){
//            User u = user.get();
//            u.setCar(rc);
//            userRepository.save(u);
//        }
//
//        CarDto addedCar = transferToDto(rc);
//        return addedCar;
//    }
    //hieronder wordt eerst de auto gekoppeld aan de uset
public CarDto addCar(CarDto carDto, String username) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        Car car = transferToCar(carDto);
        car.setUser(user);  // koppel de auto aan de gebruiker
        Car savedCar = carRepository.save(car);
        user.setCar(savedCar); // dit is nodig om de bidirectionele relatie te behouden
        userRepository.save(user);
        return transferToDto(savedCar);
    } else {
        throw new EntityNotFoundException("User not found with username: " + username);
    }
}




// de orineel werkende:
//    public void deleteCar(Long id) {
//        carRepository.deleteById(id);
//    }

    ///////////
    // nu mee bezig: werkt wek, maar niet de juiste timing met FE:
    public void removeCarFromUser(Long carId) {
        User user = userRepository.findByCarId(carId);
        if (user != null) {
            user.setCar(null);
            userRepository.save(user);
        }
    }
    public void deleteCar(Long carId) {
        removeCarFromUser(carId);
        carRepository.deleteById(carId);
    }
    public Car getCarByUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getCar();
        } else {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
    }

///////////////



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
