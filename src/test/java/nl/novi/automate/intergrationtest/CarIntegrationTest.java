package nl.novi.automate.intergrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.dto.CarDto;
import nl.novi.automate.model.Car;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.service.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")  //activeerd de test applications...!
class CarIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();

        Car car = new Car();
        car.setId(1L);
        car.setModel("Capture");
        car.setBrand("Renault");
        car.setLicensePlate("8HHG-98");

        carRepository.save(car);
    }

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
//    @WithMockUser(username = "testuser", roles = "BESTUURDER")
    void addCar() throws Exception {
        CarDto carDto = new CarDto(1L, "8HHG-98", "Capture", "Renault");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/cars")
                        .principal(new UsernamePasswordAuthenticationToken("testuser", "testpassword"))
                        .content(objectMapper.writeValueAsString(carDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(carDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.licensePlate").value(carDto.getLicensePlate()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(carDto.getModel()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(carDto.getBrand()));
    }

    @Test
    void deleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cars/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void getCarByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/user/{username}", "user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.licensePlate").value("8HHG-98"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Capture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Renault"));
    }
}
