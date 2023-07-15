package nl.novi.automate.service;

import nl.novi.automate.dto.CarDto;
import nl.novi.automate.model.Car;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CarService carService;

    Car car1;

    @BeforeEach
    void setUp() {
        car1 = new Car();
        car1.setId(1L);
        car1.setModel("A3");
        car1.setBrand("Audi");
        car1.setLicensePlate("5KKH-92");
    }

    @Test
    public void givenUserExists_whenAddCar_thenReturnsCorrectDto() {
        User user = new User();
        user.setUsername("username");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        CarDto carDto = new CarDto();
        carDto.setLicensePlate("license");
        carDto.setModel("model");
        carDto.setBrand("brand");

        Car car = new Car();
        car.setLicensePlate(carDto.getLicensePlate());
        car.setModel(carDto.getModel());
        car.setBrand(carDto.getBrand());
        when(carRepository.save(any())).thenReturn(car);

        CarDto returnedDto = carService.addCar(carDto, "username");

        assertEquals(carDto.getLicensePlate(), returnedDto.getLicensePlate());
        assertEquals(carDto.getModel(), returnedDto.getModel());
        assertEquals(carDto.getBrand(), returnedDto.getBrand());
    }

    @Test
    public void givenCarExists_whenDeleteCar_thenInteractionsAreVerified() {
        when(userRepository.findByCarId(anyLong())).thenReturn(new User());

        doNothing().when(carRepository).deleteById(anyLong());

        carService.deleteCar(1L);

        verify(carRepository, times(1)).deleteById(1L);
    }
    @Test
    public void givenUserExists_whenGetCarByUser_thenReturnCar() {

        User user = new User();
        user.setUsername("username");
        user.setCar(car1);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Car returnedCar = carService.getCarByUser(user.getUsername());

        assertNotNull(returnedCar);
        assertEquals(car1, returnedCar);
    }

    @Test
    public void givenUserDoesNotExist_whenGetCarByUser_thenThrowEntityNotFoundException() {

        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            carService.getCarByUser(username);
        });

        assertTrue(exception.getMessage().contains("User not found with username: " + username));
    }

}