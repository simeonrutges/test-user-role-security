package nl.novi.automate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.model.Ride;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.RideService;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(RideController.class)
@AutoConfigureMockMvc(addFilters = false) // deze uitzetten als test werkt en onderstaande activeren:
class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    private RideService rideService;

    @MockBean
    private UserService userService;

    Ride ride1;
    Ride ride2;
    Ride ride3;
    RideDto rideDto1;
    RideDto rideDto2;
    RideDto rideDto3;


    @BeforeEach
    void setUp() {
        ride1 = new Ride();
        ride1.setId(1L);
        ride1.setPickUpLocation("Amsterdam");
        ride1.setDestination("Utrecht");

//            rideDto1 = new RideDto();
//            rideDto1.id = 1L;
//            rideDto1.pickUpLocation = "Amsterdam";
//            rideDto1.destination = "Utrecht";
//            rideDto1.route = "Amsterdam - Utrecht";
//            rideDto1.addRideInfo = "Extra informatie over de rit";
//            rideDto1.departureTime = LocalTime.of(8, 0);
//            rideDto1.departureDate = LocalDate.of(2024, 6, 1);
//            rideDto1.departureDateTime = LocalDateTime.of(2024, 6, 1, 8, 0);
//            rideDto1.pricePerPerson = 10.0;
//            rideDto1.pax = 3;
//            rideDto1.totalRitPrice = 30.0;
//            rideDto1.availableSpots = 2;
//            rideDto1.automaticAcceptance = true;
//            rideDto1.eta = LocalTime.of(10, 0);
//            rideDto1.driverUsername = "bestuurder1";
//            rideDto1.users = new ArrayList<>();  // Maak een lege lijst, of voeg UserDto instanties toe




        ride2 = new Ride();
        ride2.setId(2L);
        ride2.setPickUpLocation("Woerden");
        ride2.setDestination("Den Haag");


        ride3 = new Ride();
        ride3.setId(3L);
        ride3.setPickUpLocation("Locatie1");
        ride3.setDestination("Locatie2");
        ride3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));


        rideDto1 = new RideDto();
        rideDto1.id = 1L;
        rideDto1.pickUpLocation = "Amsterdam";
        rideDto1.destination = "Utrecht";

//            rideDto1 = new RideDto();
//            rideDto1.id = 1L;
//            rideDto1.pickUpLocation = "Amsterdam";
//            rideDto1.destination = "Utrecht";
//            rideDto1.route = "Amsterdam - Utrecht";
//            rideDto1.addRideInfo = "Extra informatie over de rit";
//            rideDto1.departureTime = LocalTime.of(8, 0);
//            rideDto1.departureDate = LocalDate.of(2024, 6, 1);
//            rideDto1.departureDateTime = LocalDateTime.of(2024, 6, 1, 8, 0);
//            rideDto1.pricePerPerson = 10.0;
//            rideDto1.pax = 3;
//            rideDto1.totalRitPrice = 30.0;
//            rideDto1.availableSpots = 2;
//            rideDto1.automaticAcceptance = true;
//            rideDto1.eta = LocalTime.of(10, 0);
//            rideDto1.driverUsername = "bestuurder1";
//            rideDto1.users = new ArrayList<>();  // Maak een lege lijst, of voeg UserDto instanties toe

        rideDto2 = new RideDto();
        rideDto2.id = 2L;
        rideDto2.pickUpLocation = "Woerden";
        rideDto2.destination = "Den Haag";


        rideDto3 = new RideDto();
        rideDto3.id = 3L;
        rideDto3.setPickUpLocation("Locatie1");
        rideDto3.setDestination("Locatie2");
        rideDto3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));

    }

    @Test
    void addRide() throws Exception{
        RideDto rideDto = rideDto1;

        given(rideService.addRide(any(RideDto.class))).willReturn(rideDto1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rides")
                        .content(asJsonString(rideDto1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value("Amsterdam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Utrecht"));

        verify(rideService, times(1)).addRide(any(RideDto.class));
    }


    @Test
    @Disabled
    void addUserToRide() {
    }

    @Test
    void getRidesByDestination() throws Exception{
        given(rideService.getRidesByCriteria(eq(Optional.of("Utrecht")), any(), any()))
                .willReturn(List.of(rideDto1));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides?destination=Utrecht")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pickUpLocation").value("Amsterdam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Utrecht"));
    }

    @Test
//    @WithMockUser(username="testuser", roles="BESTUURDER")       // check authorization, not authentication:  = INGELOGDE USER IN MIJN GEVAL PASSAGIER OF BESTUURDER!!
    void getRidesByPickUpLocation() throws Exception{
        given(rideService.getRidesByCriteria(any(), eq(Optional.of("Woerden")), any()))
                .willReturn(List.of(rideDto2));


        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides?pickUpLocation=Woerden")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pickUpLocation").value("Woerden"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Den Haag"));
//        [0] vanwege de manier waarop de JSON-structuur is geformatteerd in de HTTP-response/ Zonder [0] werkt het niet
    }


    //    @Test
//    void getRidesByDepartureDateTime() throws Exception {
//        LocalDateTime departureDateTime = LocalDateTime.of(2024, 5, 31, 10, 0);
//        given(rideService.getRidesByCriteria(any(), any(), eq(Optional.of(departureDateTime))))
//                .willReturn(List.of(rideDto3));
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides")
//                        .param("departureDateTime", departureDateTime.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDateTime").value(departureDateTime.toString()));
//
//    }
    @Test
    void getRidesByDepartureDateTime() throws Exception {
        LocalDateTime departureDateTime = LocalDateTime.of(2024, 5, 31, 10, 0);
        given(rideService.getRidesByCriteria(any(), any(), eq(Optional.of(departureDateTime))))
                .willReturn(List.of(rideDto3));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides")
                        .param("departureDateTime", departureDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departureDateTime").value(departureDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }


    @Test
    void getRide() throws Exception{
        given(rideService.getRideById(1L)).willReturn(rideDto1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value("Amsterdam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Utrecht"));
    }


    @Test
    void deleteRide() throws Exception{
        doNothing().when(rideService).deleteRide(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rides/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(rideService, times(1)).deleteRide(1L);
    }



    @Test
    @Disabled
    void updateRide() {
    }

    @Test
    @Disabled
    void removeUserFromRide() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
