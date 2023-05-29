package nl.novi.automate.controller;

import nl.novi.automate.dto.CarDto;
import nl.novi.automate.model.Car;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.CarService;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import javax.persistence.EntityNotFoundException;

import static nl.novi.automate.controller.RideControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;


    @MockBean
    private CarService carService;

    Car car1;

    CarDto carDto1;

    @BeforeEach
    void setUp() {
        // zal ik een constructor in de Car entiteit zetten?
        car1 = new Car();
        car1.setId(1L);
        car1.setModel("Capture");
        car1.setBrand("Renault");
        car1.setLicensePlate("8HHG-98");

        carDto1 = new CarDto(1L, "8HHG-98", "Capture", "Renault");
    }

    @Test
    @WithMockUser(username="testuser", roles="BESTUURDER")
    void addCar() throws Exception {
        when(carService.addCar(any(CarDto.class), anyString())).thenReturn(carDto1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cars")
                        .principal(new UsernamePasswordAuthenticationToken("testuser", "testpassword"))
                        .content(asJsonString(carDto1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(carDto1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.licensePlate").value(carDto1.getLicensePlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(carDto1.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(carDto1.getBrand()));
    }

    @Test
    void deleteCar() throws Exception {
        doNothing().when(carService).deleteCar(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cars/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        // Voor een DELETE-methode zijn er niet echt velden om te controleren, omdat het geen inhoud retourneert.
    }

    @Test
    void getCarByUser_Success() throws Exception {
        when(carService.getCarByUser(anyString())).thenReturn(car1);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/user/{username}", "user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(car1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.licensePlate").value(car1.getLicensePlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(car1.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(car1.getBrand()));
    }

    @Test
    void getCarByUser_Failure() throws Exception {
        when(carService.getCarByUser(anyString())).thenThrow(new EntityNotFoundException("Car not found"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/user/{username}", "user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(content().string("Car not found")); // Hier controleer ik de foutboodschap die wordt geretourneerd.
    }
}