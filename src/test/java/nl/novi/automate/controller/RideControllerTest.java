package nl.novi.automate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.exceptions.ExceededCapacityException;
import nl.novi.automate.exceptions.UserAlreadyAddedToRideException;
import nl.novi.automate.model.ReservationInfo;
import nl.novi.automate.model.Ride;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.RideService;
import nl.novi.automate.service.UserService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserNotInRideException;


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

    @MockBean
    private ReservationInfo reservationInfo;

    // create an ObjectMapper as an instance field
    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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
        ride1.setRoute("Amsterdam - Utrecht");
        ride1.setAddRideInfo("Extra informatie over de rit");
        ride1.setDepartureTime(LocalTime.of(8, 0));
        ride1.setDepartureDate(LocalDate.of(2024, 6, 1));
        ride1.setDepartureDateTime(LocalDateTime.of(2024, 6, 1, 8, 0));
        ride1.setPricePerPerson(10.0);
        ride1.setPax(3);
        ride1.setTotalRitPrice(30.0);
        ride1.setAvailableSpots(2);
        ride1.setEta(LocalTime.of(10, 0));
        ride1.setDriverUsername("bestuurder1");
        ride1.setPickUpAddress("Dorpsstraat 6");
        ride1.setDestinationAddress("Station");

        ride2 = new Ride();
        ride2.setId(2L);
        ride2.setPickUpLocation("Woerden");
        ride2.setDestination("Den Haag");
        ride2.setRoute("Woerden - Den Haag");
        ride2.setAddRideInfo("Extra informatie over de rit");
        ride2.setDepartureTime(LocalTime.of(9, 0));
        ride2.setDepartureDate(LocalDate.of(2024, 6, 2));
        ride2.setDepartureDateTime(LocalDateTime.of(2024, 6, 2, 9, 0));
        ride2.setPricePerPerson(15.0);
        ride2.setPax(3);
        ride2.setTotalRitPrice(45.0);
        ride2.setAvailableSpots(2);
        ride2.setEta(LocalTime.of(11, 0));
        ride2.setDriverUsername("bestuurder2");
        ride2.setPickUpAddress("Mezenstraat 16");
        ride2.setDestinationAddress("Kievitstraat 1");

        ride3 = new Ride();
        ride3.setId(3L);
        ride3.setPickUpLocation("Locatie1");
        ride3.setDestination("Locatie2");
        ride3.setRoute("Locatie1 - Locatie2");
        ride3.setAddRideInfo("Extra informatie over de rit");
        ride3.setDepartureTime(LocalTime.of(10, 0));
        ride3.setDepartureDate(LocalDate.of(2024, 5, 31));
        ride3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));
        ride3.setPricePerPerson(20.0);
        ride3.setPax(4);
        ride3.setTotalRitPrice(80.0);
        ride3.setAvailableSpots(3);
        ride3.setEta(LocalTime.of(12, 0));
        ride3.setDriverUsername("bestuurder3");
        ride3.setPickUpAddress("Meeuwenlaan 26");
        ride3.setDestinationAddress("Mussenstraat 50");


        rideDto1 = new RideDto();
        rideDto1.id = 1L;
        rideDto1.pickUpLocation = "Amsterdam";
        rideDto1.destination = "Utrecht";
        rideDto1.route = "Amsterdam - Utrecht";
        rideDto1.addRideInfo = "Extra informatie over de rit";
        rideDto1.departureTime = LocalTime.of(8, 0);
        rideDto1.departureDate = LocalDate.of(2024, 6, 1);
        rideDto1.departureDateTime = LocalDateTime.of(2024, 6, 1, 8, 0);
        rideDto1.pricePerPerson = 10.0;
        rideDto1.pax = 3;
        rideDto1.totalRitPrice = 30.0;
        rideDto1.availableSpots = 2;
        rideDto1.eta = LocalTime.of(23, 0);
        rideDto1.driverUsername = "bestuurder1";
        rideDto1.pickUpAddress = "Dorpsstraat 6";
        rideDto1.destinationAddress = "Station";

        rideDto2 = new RideDto();
        rideDto2.id = 2L;
        rideDto2.pickUpLocation = "Woerden";
        rideDto2.destination = "Den Haag";
        rideDto2.route = "Woerden - Den Haag";
        rideDto2.addRideInfo = "Extra informatie over de rit";
        rideDto2.departureDate = LocalDate.of(2024, 6, 2);
        rideDto2.departureDateTime = LocalDateTime.of(2024, 6, 2, 9, 0);
        rideDto2.pricePerPerson = 15.0;
        rideDto2.pax = 3;
        rideDto2.totalRitPrice = 45.0;
        rideDto2.availableSpots = 2;
        rideDto2.eta = LocalTime.of(11, 0);
        rideDto2.driverUsername = "bestuurder2";
        rideDto2.pickUpAddress = "Mezenstraat 16";
        rideDto2.destinationAddress = "Kievitstraat 1";

        rideDto3 = new RideDto();
        rideDto3.id = 3L;
        rideDto3.pickUpLocation = "Locatie1";
        rideDto3.destination = "Locatie2";
        rideDto3.route = "Locatie1 - Locatie2";
        rideDto3.addRideInfo = "Extra informatie over de rit";
        rideDto3.departureDate = LocalDate.of(2024, 5, 31);
        rideDto3.departureDateTime = LocalDateTime.of(2024, 5, 31, 10, 0);
        rideDto3.pricePerPerson = 20.0;
        rideDto3.pax = 4;
        rideDto3.totalRitPrice = 80.0;
        rideDto3.availableSpots = 3;
        rideDto3.eta = LocalTime.of(12, 0);
        rideDto3.driverUsername = "bestuurder3";
        rideDto3.pickUpAddress = "Meeuwenlaan 26";
        rideDto3.destinationAddress = "Mussenstraat 50";

    }

    @Test
//    @WithMockUser(username="testuser", roles="BESTUURDER")
    void testAddRideWithInvalidDto_ReturnsBadRequestResponse() throws Exception {
        // Configureer de rijdto met ongeldige gegevens, in dit geval lege velden
        RideDto invalidRideDto = new RideDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRideDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
// @WithMockUser(username="testuser", roles="BESTUURDER")
    void testAddRideWithValidDto_ReturnsOkResponse() throws Exception {
        // Configureer een geldige rijdto
        RideDto validRideDto = new RideDto();
        validRideDto.setPickUpLocation("TestLocation");
        validRideDto.setDestination("TestDestination");
        validRideDto.setDepartureTime(LocalTime.now());
        validRideDto.setDepartureDate(LocalDate.now().plusDays(1)); // Een datum in de toekomst
        validRideDto.setDepartureDateTime(LocalDateTime.now().plusDays(1)); // Een datum en tijd in de toekomst
        validRideDto.setPricePerPerson(5.0);
        validRideDto.setPax(2);
        validRideDto.setAvailableSpots(2);
        validRideDto.setEta(LocalTime.now());
        validRideDto.setDriverUsername("testDriver");
        validRideDto.setPickUpAddress("TestAddress");
        validRideDto.setDestinationAddress("TestDestinationAddress");

        when(rideService.addRide(validRideDto)).thenReturn(validRideDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rides")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validRideDto)))
                .andExpect(status().isOk())
                .andReturn();
    }




    @Test
    void addUserToRideSuccess() throws Exception {
        Long rideId = 1L;
        String username = "testuser";
        int pax = 1;

        // assuming the rideService.addUserToRide() method doesn't return anything when successful
        doNothing().when(rideService).addUserToRide(rideId, username, pax);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rides/" + rideId + "/" + username + "/" + pax)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(rideService, times(1)).addUserToRide(rideId, username, pax);
    }

    @Test
    void addUserToRideUserAlreadyAddedToRideException() throws Exception {
        Long rideId = 1L;
        String username = "testuser";
        int pax = 2;

        doThrow(new UserAlreadyAddedToRideException("User already added to ride"))
                .when(rideService).addUserToRide(rideId, username, pax);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rides/" + rideId + "/" + username + "/" + pax)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("User already added to ride"));

        verify(rideService, times(1)).addUserToRide(rideId, username, pax);
    }

    @Test
    void addUserToRideFailsDueToExceededCapacity() throws Exception {
        Long rideId = 1L;
        String username = "testuser";
        int pax = 1;

        doThrow(new ExceededCapacityException("Capacity Exceeded"))
                .when(rideService).addUserToRide(rideId, username, pax);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rides/" + rideId + "/" + username + "/" + pax)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

        verify(rideService, times(1)).addUserToRide(rideId, username, pax);
    }

    @Test
    void addUserToRideFailsDueToRecordNotFound() throws Exception {
        Long rideId = 1L;
        String username = "testuser";
        int pax = 1;

        doThrow(new RecordNotFoundException("Record not found"))
                .when(rideService).addUserToRide(rideId, username, pax);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rides/" + rideId + "/" + username + "/" + pax)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

        verify(rideService, times(1)).addUserToRide(rideId, username, pax);
    }



//    @Test
////    @WithMockUser(username="testuser", roles="PASSAGIER")
//    void getRidesByDestination() throws Exception{
//        given(rideService.getRidesByCriteria(eq(Optional.of("Utrecht")), any(), any()))
//                .willReturn(List.of(rideDto1));
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides?destination=Utrecht")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pickUpLocation").value("Amsterdam"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].destination").value("Utrecht"));
//    }

//    @Test
//    void getReservationInfoForUser_ReturnsReservationInfo_WhenUserAndRideExist() throws Exception {
//        ReservationInfo reservationInfo = new ReservationInfo();  // fill with sample data
//        when(rideService.getReservationInfoForUser(1L, "username")).thenReturn(reservationInfo);
//
//        mockMvc.perform(get("/rides/1/users/username/reservationInfo"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(reservationInfo)));
//
//        verify(rideService, times(1)).getReservationInfoForUser(1L, "username");
//    }

    @Test
    void getReservationInfoForUser_ReturnsNotFound_WhenUserAndRideDoNotExist() throws Exception {
        when(rideService.getReservationInfoForUser(1L, "username")).thenThrow(new RecordNotFoundException());

        mockMvc.perform(get("/rides/1/users/username/reservationInfo"))
                .andExpect(status().isNotFound());

        verify(rideService, times(1)).getReservationInfoForUser(1L, "username");
    }

    @Test
    void getRidesByCriteria_ReturnsRides_WhenCriteriaAreValid() throws Exception {
        List<RideDto> dtos = new ArrayList<>();  // fill with sample data
        dtos.add(rideDto1); // using the rideDto1 defined in your setup method

        String dtosAsJson = objectMapper.writeValueAsString(dtos);

        when(rideService.getRidesByCriteria("destination", "pickUpLocation", LocalDate.parse("2024-06-01"), 3)).thenReturn(dtos);

        mockMvc.perform(get("/rides?destination=destination&pickUpLocation=pickUpLocation&departureDate=2024-06-01&pax=3"))
                .andExpect(status().isOk())
                .andExpect(content().json(dtosAsJson));

        verify(rideService, times(1)).getRidesByCriteria("destination", "pickUpLocation", LocalDate.parse("2024-06-01"), 3);
    }

    @Test
    void getRidesByCriteria_ReturnsBadRequest_WhenDateFormatIsInvalid() throws Exception {
        mockMvc.perform(get("/rides?destination=destination&pickUpLocation=pickUpLocation&departureDate=invalid_date&pax=3"))
                .andExpect(status().isBadRequest());

        verify(rideService, never()).getRidesByCriteria(anyString(), anyString(), any(LocalDate.class), anyInt());
    }




    @Test
    void getReservationInfoForUser_ReturnsInfo_WhenUserAndRideExist() throws Exception {
        ReservationInfo reservationInfo = new ReservationInfo(5, 20.0);

        when(rideService.getReservationInfoForUser(1L, "username")).thenReturn(reservationInfo);

        mockMvc.perform(get("/rides/1/users/username/reservationInfo"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(reservationInfo)));

        verify(rideService, times(1)).getReservationInfoForUser(1L, "username");
    }


    @Test
    void getReservationInfoForUser_ReturnsForbidden_WhenUserNotInRide() throws Exception {
        when(rideService.getReservationInfoForUser(1L, "username")).thenThrow(new UserNotInRideException("User is not in ride"));

        mockMvc.perform(get("/rides/1/users/username/reservationInfo"))
                .andExpect(status().isForbidden());

        verify(rideService, times(1)).getReservationInfoForUser(1L, "username");
    }




    @Test
//    @WithMockUser(username="testuser", roles="PASSAGIER")
    void getRide() throws Exception {
        given(rideService.getRideById(1L)).willReturn(rideDto1);

        this.mockMvc.perform(get("/rides/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value("Amsterdam"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Utrecht"));
    }

    @Test
//    @WithMockUser(username="testuser", roles="BESTUURDER")
    void deleteRide() throws Exception {
        doNothing().when(rideService).deleteRide(1L);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/rides/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        verify(rideService, times(1)).deleteRide(1L);
    }


////    Deze methode is een hulpmethode die een Java-object omzet (serializeert) naar
////    een JSON-tekststring met behulp van de ObjectMapper klasse uit de Jackson-bibliotheek:
//    public static String asJsonString(final Object obj) {
//        ObjectMapper objectMapper = new ObjectMapper();
////        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
////        objectMapper.setDateFormat(dateFormat);
//
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    @WithMockUser(username="testuser", roles="BESTUURDER") // Zorg ervoor dat de juiste rollen zijn ingesteld
    void removeUserFromRide_Success() throws Exception {
        doNothing().when(rideService).removeUserFromRide(anyLong(), anyString());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rides/{rideId}/users/{username}", 1L, "testuser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username="testuser", roles="BESTUURDER") // Zorg ervoor dat de juiste rollen zijn ingesteld
    void removeUserFromRide_UserNotInRide() throws Exception {
        doThrow(new UserNotInRideException("User not in ride")).when(rideService).removeUserFromRide(anyLong(), anyString());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rides/{rideId}/users/{username}", 1L, "testuser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("User not in ride"));
    }

    @Test
    @WithMockUser(username="testuser", roles="BESTUURDER") // Zorg ervoor dat de juiste rollen zijn ingesteld
    void removeUserFromRide_RecordNotFound() throws Exception {
        doThrow(new RecordNotFoundException("Record not found")).when(rideService).removeUserFromRide(anyLong(), anyString());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/rides/{rideId}/users/{username}", 1L, "testuser")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Record not found"));
    }

    @Test
    @WithMockUser(username="testuser", roles="BESTUURDER") // Zorg ervoor dat de juiste rollen zijn ingesteld
    void updateRide() throws Exception {
        when(rideService.updateRide(anyLong(), any(RideDto.class))).thenReturn(rideDto1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/rides/{id}", 1L)
                        .content(objectMapper.writeValueAsString(rideDto1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(rideDto1.id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value(rideDto1.pickUpLocation))
                .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value(rideDto1.destination))
                .andExpect(MockMvcResultMatchers.jsonPath("$.route").value(rideDto1.route));
        // Voeg hier eventueel meer .andExpect statements toe voor andere velden in RideDto
    }


    public static String asJsonString(final Object obj) {
        try {
            // use the ObjectMapper instance field
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
