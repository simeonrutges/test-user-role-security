package nl.novi.automate.intergrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.novi.automate.dto.CarDto;
import nl.novi.automate.model.Car;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.UserRepository;
import nl.novi.automate.service.CarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.Filter;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")  //activeerd de test applications...!
//@Transactional //23-6 geintroduceerd!!! let op, evtueel verwijderen!!!
class CarIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    Car car1;
    Car car2;
    CarDto carDto1;
    CarDto carDto2;

    User user1 =  new User();
    User user2 = new User();

    @BeforeEach
    void setUp() {if(carRepository.count()>0) {
        carRepository.deleteAll();
    }
        car1 = new Car();
//        car1.setId(1L);
        car1.setModel("Capture");
        car1.setBrand("Renault");
        car1.setLicensePlate("8HHG-98");

        car2 = new Car();
//        car2.setId(2L);
        car2.setModel("Megane");
        car2.setBrand("Renault");
        car2.setLicensePlate("7GGH-77");

        car1 = carRepository.save(car1);
        car2 = carRepository.save(car2);

        carDto1 = new CarDto();
        carDto1.setId(car1.getId());
        carDto1.setModel(car1.getModel());
        carDto1.setBrand(car1.getBrand());
        carDto1.setLicensePlate(car1.getLicensePlate());

        carDto2 = new CarDto();
        carDto2.setId(car2.getId());
        carDto2.setModel(car2.getModel());
        carDto2.setBrand(car2.getBrand());
        carDto2.setLicensePlate(car2.getLicensePlate());

        user1 = new User();
        user1.setUsername("testUser");
        user1.setFirstname("Test");
        user1.setLastname("User");
        user1.setEmail("testUser@novi-education.nl");

        userRepository.save(user1);

        user2 = new User();
        user2.setUsername("testUser2");
        user2.setFirstname("Test2");
        user2.setLastname("User2");
        user2.setEmail("testUser2@novi-education.nl");

        userRepository.save(user2);

//        car1.setUser(user1);
        user1.setCar(car1);
//        car2.setUser(user2);
        user2.setCar(car2);


        userRepository.save(user1);
        carRepository.save(car1);
        userRepository.save(user2);
        carRepository.save(car2);

//        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//        Authentication authentication = new TestingAuthenticationToken("testUser", "password", "BESTUURDER");
//        securityContext.setAuthentication(authentication);
//        SecurityContextHolder.setContext(securityContext);
//
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(this.context)
//                .addFilters(this.springSecurityFilterChain)
//                .build();

        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

//    @AfterEach
//    void tearDown() {
//        carRepository.deleteAll();
//    }

//    @Test
//    @WithMockUser(username = "testUser", roles = {"BESTUURDER"})
//    void shouldAddCarToUser() throws Exception {
//
//
//        // Maak een carDto object om te versturen in de request body
//        CarDto carDto = new CarDto();
//        carDto.setId(3L);
//        carDto.setModel("Clio");
//        carDto.setBrand("Renault");
//        carDto.setLicensePlate("6FFG-66");
//
//        // Maak een POST request naar de addCar API endpoint
//        mockMvc.perform(
//                        post("/cars")
//                                .content(objectMapper.writeValueAsString(carDto))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(carDto.getId())))
//                .andExpect(jsonPath("$.model", is(carDto.getModel())))
//                .andExpect(jsonPath("$.brand", is(carDto.getBrand())))
//                .andExpect(jsonPath("$.licensePlate", is(carDto.getLicensePlate())));
//    }


    @Test
    @Transactional
    void deleteCar() throws Exception {
        // Perform the delete request
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/cars/{id}", car2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

//         Verify the deletion
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/{id}", car2.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    void getCarByUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/cars/user/{username}", "testUser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(car1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.licensePlate").value("8HHG-98"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Capture"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value("Renault"));
    }


    public static String asJsonString(final CarDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
