package nl.novi.automate.service;

import nl.novi.automate.dto.RideDto;
import nl.novi.automate.model.Ride;
import nl.novi.automate.repository.RideRepository;
import nl.novi.automate.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    @Mock
    RideRepository rideRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    NotificationService notificationService;

    @Mock
    DtoMapperService dtoMapperService;

    @InjectMocks
    RideService rideService;

    @Captor
    ArgumentCaptor<Ride>captor;

    Ride ride1;
    Ride ride2;
    Ride ride3;

    @BeforeEach //Arrange
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
        ride1.setAutomaticAcceptance(true);
        ride1.setEta(LocalTime.of(10, 0));
        ride1.setDriverUsername("bestuurder1");
//            ride1.setUsers(new ArrayList<>());  // Maak een lege lijst, of voeg UserDto instanties toe

        ride2 = new Ride();
        ride2.setId(2L);
        ride2.setPickUpLocation("Woerden");
        ride2.setDestination("Den Haag");

        ride3 = new Ride();
        ride3.setId(3L);
        ride3.setPickUpLocation("Arnhem");
        ride3.setDestination("Maastricht");
        ride3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));
    }

    @AfterEach
    void tearDown() {
    }

    //When=Act Then=Assert
    @Test
    void addRide() {
    }

//    @Test
//    void getRideById() {
//    }
//@Test
//@Disabled
//void getRideByIdSuccess() throws Exception {
//    Long rideId = 1L;
//
//    given(rideService.getRideById(rideId)).willReturn(rideDto1);
//
//    this.mockMvc.perform(MockMvcRequestBuilders.get("/rides/" + rideId)
//                    .contentType(MediaType.APPLICATION_JSON))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value("Amsterdam"))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Utrecht"));
//
//    verify(rideService, times(1)).getRideById(rideId);
//}
//
//    @Test
//    @Disabled
//    void getRideByIdRecordNotFoundException() throws Exception {
//        Long rideId = 1L;
//
//        given(rideService.getRideById(rideId)).willThrow(new RecordNotFoundException("Geen rit gevonden"));
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides/" + rideId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isNotFound())
//                .andExpect(MockMvcResultMatchers.content().string("Geen rit gevonden"));
//
//        verify(rideService, times(1)).getRideById(rideId);
//    }


    @Test
    void calculateTotalRitPrice() {
    }

    @Test
    void addUserToRide() {
    }

    @Test
    void getRidesByDestination() {
        List<Ride> rideList = Collections.singletonList(ride3);
        when(rideRepository.findAllRidesByDestinationEqualsIgnoreCase("Maastricht")).thenReturn(rideList);

        List<RideDto> ridesFound = rideService.getRidesByCriteria(Optional.of("Maastricht"), Optional.empty(), Optional.empty());

        assertFalse(ridesFound.isEmpty(), "No rides found");
        assertEquals(ride3.getDestination(), ridesFound.get(0).getDestination());
    }







    @Test
    void transferRideListToDtoList() {
    }

    @Test
    void deleteRide() {
    }

    @Test
    void updateRide() {
    }

    @Test
    void removeUserFromRide() {
    }
}