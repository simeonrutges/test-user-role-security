package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.Ride;
import nl.novi.automate.model.User;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Mockito's 'strictness' niveaus hebben betrekking op hoe streng de bibliotheek is over het controleren van onnodige stubs (mocks die niet gebruikt worden in je tests) en ongebruikte invocations (methodes die aangeroepen worden op je mocks maar niet geverifieerd worden in je tests).
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
        RideDto rideDto = new RideDto();
        // Initialize rideDto fields here...

        Ride newRide = new Ride();
        // Initialize newRide fields here...

        User systemUser = new User();
        systemUser.setUsername("System");
        when(userRepository.findByUsernameIgnoreCase("System")).thenReturn(systemUser);

        User driverUser = new User();
        driverUser.setUsername("Driver");
        when(userRepository.findByUsername(rideDto.getDriverUsername())).thenReturn(Optional.of(driverUser));

        when(dtoMapperService.dtoToRide(rideDto)).thenReturn(newRide);

        NotificationDto notificationDto = new NotificationDto();
        // Initialize notificationDto fields here...
        when(dtoMapperService.notificationToDto(any(Notification.class))).thenReturn(notificationDto);

        rideService.addRide(rideDto);

        verify(rideRepository).save(newRide);
        verify(notificationService).createNotification(any(NotificationDto.class));
    }


    @Test
    void getRideByIdSuccess() {
        Long rideId = 1L;

        Optional<Ride> optionalRide = Optional.of(ride1);
        when(rideRepository.findById(rideId)).thenReturn(optionalRide);

        RideDto rideDto = new RideDto();
        // Initialize rideDto fields here...

        when(dtoMapperService.rideToDto(ride1)).thenReturn(rideDto);

        RideDto returnedRideDto = rideService.getRideById(rideId);

        assertEquals(rideDto, returnedRideDto);
    }

    @Test
    void getRideByIdRecordNotFoundException() {
        Long rideId = 1L;

        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> rideService.getRideById(rideId));
    }



    @Test
    void getRideById() {
    }


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
    void addUserToRide() {
    }

    @Test
    void getRidesByDestination() {
        // Maak een lijst met één rit die overeenkomt met ride3
        List<Ride> rideList = Collections.singletonList(ride3);
        when(rideRepository.findAllRidesByDestinationEqualsIgnoreCase("Maastricht")).thenReturn(rideList);

        // Stel de RideDto in om overeen te komen met ride3
        RideDto rideDto = new RideDto();
        rideDto.setId(3L);
        rideDto.setPickUpLocation("Arnhem");
        rideDto.setDestination("Maastricht");
        rideDto.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));
        // Andere eigenschappen van rideDto hier instellen ...

        when(dtoMapperService.rideToDto(ride3)).thenReturn(rideDto);

        // Nu de methode aanroepen die je wilt testen
        List<RideDto> ridesFound = rideService.getRidesByCriteria(Optional.of("Maastricht"), Optional.empty(), Optional.empty());

        assertFalse(ridesFound.isEmpty(), "No rides found");
        assertEquals(ride3.getDestination(), ridesFound.get(0).getDestination());
    }



    @Test
    void getRidesByPickUpLocation() {
        // Maak een lijst met één rit die overeenkomt met ride2
        List<Ride> rideList = Collections.singletonList(ride2);
        when(rideRepository.findAllRidesByPickUpLocationEqualsIgnoreCase("Woerden")).thenReturn(rideList);

        // Stel de RideDto in om overeen te komen met ride2
        RideDto rideDto = new RideDto();
        rideDto.setId(ride2.getId());
        rideDto.setPickUpLocation(ride2.getPickUpLocation());
        rideDto.setDestination(ride2.getDestination());
        // Vul de overige velden van rideDto in, indien nodig...

        when(dtoMapperService.rideToDto(ride2)).thenReturn(rideDto);

        List<RideDto> ridesFound = rideService.getRidesByCriteria(Optional.empty(), Optional.of("Woerden"), Optional.empty());

        assertFalse(ridesFound.isEmpty(), "No rides found");
        assertEquals(ride2.getPickUpLocation(), ridesFound.get(0).getPickUpLocation());
    }

    @Test
    void getRidesByDepartureDateTime() {
        LocalDateTime testDateTime = LocalDateTime.of(2024, 5, 31, 10, 0);
        List<Ride> rideList = Collections.singletonList(ride3);
        when(rideRepository.findAllRidesByDepartureDateTimeEquals(testDateTime)).thenReturn(rideList);

        // Stel de RideDto in om overeen te komen met ride3
        RideDto rideDto = new RideDto();
        rideDto.setId(ride3.getId());
        rideDto.setPickUpLocation(ride3.getPickUpLocation());
        rideDto.setDestination(ride3.getDestination());
        rideDto.setDepartureDateTime(ride3.getDepartureDateTime());
        // Vul de overige velden van rideDto in, indien nodig...

        when(dtoMapperService.rideToDto(ride3)).thenReturn(rideDto);

        List<RideDto> ridesFound = rideService.getRidesByCriteria(Optional.empty(), Optional.empty(), Optional.of(testDateTime));

        assertFalse(ridesFound.isEmpty(), "No rides found");
        assertEquals(ride3.getDepartureDateTime(), ridesFound.get(0).getDepartureDateTime());
    }

    @Test
    void getRidesByAllCriteria() {
        String destination = "destination";
        String pickUpLocation = "pickUpLocation";
        LocalDateTime departureDateTime = LocalDateTime.now();

        List<Ride> rideList = Collections.singletonList(ride1);
        when(rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCaseAndDepartureDateTimeEquals(
                destination, pickUpLocation, departureDateTime)).thenReturn(rideList);

        // Stel de RideDto in om overeen te komen met ride1
        RideDto rideDto = new RideDto();
        rideDto.setId(ride1.getId());
        rideDto.setPickUpLocation(ride1.getPickUpLocation());
        rideDto.setDestination(ride1.getDestination());
        rideDto.setDepartureDateTime(ride1.getDepartureDateTime());
        // Vul de overige velden van rideDto in, indien nodig...

        when(dtoMapperService.rideToDto(ride1)).thenReturn(rideDto);

        List<RideDto> ridesFound = rideService.getRidesByCriteria(Optional.of(destination), Optional.of(pickUpLocation), Optional.of(departureDateTime));

    }

//    @Test
//    void transferRideListToDtoList() {
//    }
//
//    @Test
//    void deleteRide() {
//    }
//
//    @Test
//    void updateRide() {
//    }
//
//    @Test
//    void removeUserFromRide() {
//    }
}