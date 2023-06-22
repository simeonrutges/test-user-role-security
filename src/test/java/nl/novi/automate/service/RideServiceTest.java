package nl.novi.automate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserAlreadyAddedToRideException;
import nl.novi.automate.exceptions.UserNotInRideException;
import nl.novi.automate.model.*;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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
//    Dit geeft ons later de mogelijkheid om een save of update methode te testen

    Ride ride1;
    Ride ride2;
    Ride ride3;

    RideDto rideDto1;
    RideDto rideDto2;
    RideDto rideDto3;

    User user1;

    UserDto userDto1;

    @BeforeEach
    void setUp() {
        User systemUser = new User();
        systemUser.setUsername("System");
        when(userRepository.findByUsername("System")).thenReturn(Optional.of(systemUser));

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
        ride1.setUsers(new ArrayList<>());

        ride2 = new Ride();
        ride2.setId(2L);
        ride2.setPickUpLocation("Woerden");
        ride2.setDestination("Den Haag");
        ride2.setUsers(new ArrayList<>());

        ride3 = new Ride();
        ride3.setId(3L);
        ride3.setPickUpLocation("Arnhem");
        ride3.setDestination("Maastricht");
        ride3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));
        ride3.setUsers(new ArrayList<>());

        UserDto systemUserDto = new UserDto();
        systemUserDto.setUsername("System");
        when(dtoMapperService.userToDto(systemUser)).thenReturn(systemUserDto);

        rideDto1 = new RideDto();
        rideDto1.setId(1L);
        rideDto1.setPickUpLocation("Amsterdam");
        rideDto1.setDestination("Utrecht");
        rideDto1.setRoute("Amsterdam - Utrecht");
        rideDto1.setAddRideInfo("Extra informatie over de rit");
        rideDto1.setDepartureTime(LocalTime.of(8, 0));
        rideDto1.setDepartureDate(LocalDate.of(2024, 6, 1));
        rideDto1.setDepartureDateTime(LocalDateTime.of(2024, 6, 1, 8, 0));
        rideDto1.setPricePerPerson(10.0);
        rideDto1.setPax(3);
        rideDto1.setTotalRitPrice(30.0);
        rideDto1.setAvailableSpots(2);
        rideDto1.setAutomaticAcceptance(true);
        rideDto1.setEta(LocalTime.of(10, 0));
        rideDto1.setDriverUsername("bestuurder1");
        // Note: For 'users' field, we should have UserDto list.
        // So, create UserDto instances, add them to a list and then set this list to 'users' field of rideDto1


        rideDto2 = new RideDto();
        rideDto2.setId(2L);
        rideDto2.setPickUpLocation("Woerden");
        rideDto2.setDestination("Den Haag");
        // Initialize remaining fields of rideDto2...
        //...

        rideDto3 = new RideDto();
        rideDto3.setId(3L);
        rideDto3.setPickUpLocation("Arnhem");
        rideDto3.setDestination("Maastricht");
        rideDto3.setDepartureDateTime(LocalDateTime.of(2024, 5, 31, 10, 0));
        // Initialize remaining fields of rideDto3...

        user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("password1");
        user1.setFirstname("firstname1");
        user1.setLastname("lastname1");
        user1.setEmail("email1@example.com");
        user1.setEnabled(true);
        user1.setPhoneNumber(1234567890);
        user1.setBio("bio1");
        user1.setFileName("filename1");
        user1.setRoles(new ArrayList<>());  // Initialiseer de 'roles' lijst
        user1.setRides(new ArrayList<>());  // Initialiseer de 'rides' lijst

        userDto1 = new UserDto();
        userDto1.setUsername("username1");
        userDto1.setPassword("password1");
        userDto1.setFirstname("firstname1");
        userDto1.setLastname("lastname1");
        userDto1.setEmail("email1@example.com");
        userDto1.setEnabled(true);
        userDto1.setPhoneNumber(1234567890);
        userDto1.setBio("bio1");
        userDto1.setFileName("filename1");
        userDto1.setRoles(new String[]{"ROLE_USER"});  // Initialiseer de 'roles' lijst
//        userDto1.setCar(new CarDto());  // Initialiseer de 'car' veld

//        ride1.setUsers(new ArrayList<>(Arrays.asList(user1)));
//        user1.setRides(new ArrayList<>(Arrays.asList(ride1)));
        ride1.setUsers(new ArrayList<>(Collections.singletonList(user1)));
        user1.setRides(new ArrayList<>(Collections.singletonList(ride1)));

        when(rideRepository.findById(1L)).thenReturn(Optional.of(ride1));
    }


    @AfterEach
    void tearDown() {
    }

    //When=Act Then=Assert
    @Test
    void addRide() {
        RideDto rideDto = new RideDto();
        rideDto.id = 1L;
        rideDto.pickUpLocation = "Location A";
        rideDto.destination = "Location B";
        rideDto.route = "Route 1";
        rideDto.addRideInfo = "Additional Ride Info";

        rideDto.departureTime = LocalTime.of(10, 0);
        rideDto.departureDate = LocalDate.now().plusDays(1); // a date in the future
        rideDto.departureDateTime = LocalDateTime.now().plusDays(1); // a datetime in the future

        rideDto.pricePerPerson = 3.5;
        rideDto.pax = 2;
        rideDto.totalRitPrice = 7.0;
        rideDto.availableSpots = 2;
        rideDto.automaticAcceptance = true;
        rideDto.eta = LocalTime.of(12, 0); // Estimated arrival time

        rideDto.driverUsername = "Driver";
        rideDto.users = new ArrayList<>(); // Assume no users yet

        Ride newRide = new Ride();
        newRide.setId(1L);
        newRide.setPickUpLocation("Location A");
        newRide.setDestination("Location B");
        newRide.setRoute("Route 1");
        newRide.setAddRideInfo("Additional Ride Info");

        newRide.setDepartureTime(LocalTime.of(10, 0));
        newRide.setDepartureDate(LocalDate.now().plusDays(1)); // a date in the future
        newRide.setDepartureDateTime(LocalDateTime.now().plusDays(1)); // a datetime in the future

        newRide.setPricePerPerson(3.5);
        newRide.setPax(2);
        newRide.setTotalRitPrice(7.0);
        newRide.setAvailableSpots(2);
        newRide.setAutomaticAcceptance(true);
        newRide.setEta(LocalTime.of(12, 0)); // Estimated arrival time

        newRide.setDriverUsername("Driver");
        newRide.setUsers(new ArrayList<>()); // Assume no users yet

        User systemUser = new User();
        systemUser.setUsername("System");
        when(userRepository.findByUsernameIgnoreCase("System")).thenReturn(systemUser);

        User driverUser = new User();
        driverUser.setUsername("Driver");
        when(userRepository.findByUsername(rideDto.getDriverUsername())).thenReturn(Optional.of(driverUser));

        when(dtoMapperService.dtoToRide(rideDto)).thenReturn(newRide);

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(1L);
        notificationDto.setSender(dtoMapperService.userToDto(systemUser));
        notificationDto.setReceiver(dtoMapperService.userToDto(driverUser));
        notificationDto.setType(NotificationType.PASSENGER_JOINED_RIDE);
        notificationDto.setSentDate(LocalDateTime.now());
        notificationDto.setRead(false);
        notificationDto.setRideId(1L);

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
    void getRideById_RideExists() {
        when(rideRepository.findById(ride1.getId())).thenReturn(Optional.of(ride1));
        when(dtoMapperService.rideToDto(ride1)).thenReturn(rideDto1);

        RideDto result = rideService.getRideById(ride1.getId());

        assertEquals(rideDto1, result);
    }


    @Test
    void testAddUserToRide() throws JsonProcessingException {
        // setup
        String username = "username1";
        Long rideId = 1L;
        int pax = 1;

        Ride ride = new Ride();
        ride.setAvailableSpots(2);
        ride.setPricePerPerson(10.0);
        ride.setTotalRitPrice(0.0);
        ride.setPax(0);
        ride.setReservedSpotsByUser("{}");
        ride.setDriverUsername("driver");
        ride.setUsers(new ArrayList<>());

        User user = new User();
        user.setUsername(username);
        user.setRides(new ArrayList<>());

        User driver = new User();
        driver.setUsername("driver");

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(ride.getDriverUsername())).thenReturn(Optional.of(driver));

        UserDto driverDto = new UserDto();
        driverDto.setUsername("driver");

        UserDto userDto = new UserDto();
        userDto.setUsername(username);

        when(dtoMapperService.userToDto(driver)).thenReturn(driverDto);
        when(dtoMapperService.userToDto(user)).thenReturn(userDto);

        // act
        rideService.addUserToRide(rideId, username, pax);

        // assert
        assertEquals(ride.getAvailableSpots(), 1);
        assertEquals(ride.getTotalRitPrice(), 10.0);
        assertEquals(ride.getPax(), 1);
        assertTrue(ride.getUsers().contains(user));
        assertTrue(user.getRides().contains(ride));
        assertTrue(ride.getReservedSpotsByUser().contains(username));

        verify(userRepository).save(user);
        verify(rideRepository).save(ride);

        verify(notificationService, times(2)).createNotification(any(), any());
    }


    @Test
    void testTransferRideListToDtoList() {
        // Prepare test data
        List<Ride> rides = Arrays.asList(ride1, ride2, ride3);

        when(dtoMapperService.rideToDto(ride1)).thenReturn(rideDto1);
        when(dtoMapperService.rideToDto(ride2)).thenReturn(rideDto2);
        when(dtoMapperService.rideToDto(ride3)).thenReturn(rideDto3);

        // Execute the method to test
        List<RideDto> result = rideService.transferRideListToDtoList(rides);

        // Verify the result
        assertEquals(3, result.size());
        assertSame(rideDto1, result.get(0));
        assertSame(rideDto2, result.get(1));
        assertSame(rideDto3, result.get(2));

        // Verify the interactions with the mocked DtoMapperService
        verify(dtoMapperService, times(1)).rideToDto(ride1);
        verify(dtoMapperService, times(1)).rideToDto(ride2);
        verify(dtoMapperService, times(1)).rideToDto(ride3);
    }

@Test
void getRidesByCriteria_shouldReturnRidesMatchingCriteria() {
    // Arrange
    List<Ride> rideList = Arrays.asList(ride1, ride2, ride3);
    List<RideDto> rideDtoList = Arrays.asList(rideDto1, rideDto2, rideDto3);

    // Stel het verwachte gedrag van de mocks in
    when(rideRepository.findAllRidesByDestinationEqualsIgnoreCaseAndPickUpLocationEqualsIgnoreCase("Utrecht", "Amsterdam"))
            .thenReturn(Arrays.asList(ride1));
    when(dtoMapperService.rideToDto(ride1)).thenReturn(rideDto1);

    // Act
    List<RideDto> result = rideService.getRidesByCriteria("Utrecht", "Amsterdam", LocalDate.of(2024, 6, 1), 2);

    // Assert
    assertEquals(1, result.size());
    assertEquals(rideDto1, result.get(0));
}

    @Test
    void calculateTotalRitPrice_shouldReturnCorrectTotalPrice() {
        // Arrange
        double pricePerPerson = 10.0;
        int pax = 3;
        double expectedTotalPrice = 30.0;

        // Act
        double result = rideService.calculateTotalRitPrice(pricePerPerson, pax);

        // Assert
        assertEquals(expectedTotalPrice, result, 0.01);
    }

@Test
@Disabled
void getReservationInfoForUser_shouldReturnCorrectReservationInfo() throws Exception {
    // Arrange
    Long rideId = 1L;
    String username = "username1";

    // Maak een map van de gebruikersnaam naar het aantal gereserveerde zitplaatsen
    Map<String, Integer> reservedSpotsByUserMap = new HashMap<>();
    reservedSpotsByUserMap.put(username, 2);
    String reservedSpotsByUserJson;
    try {
        reservedSpotsByUserJson = new ObjectMapper().writeValueAsString(reservedSpotsByUserMap);
    } catch (JsonProcessingException e) {
        throw new RuntimeException("Fout bij het omzetten van JSON naar String", e);
    }
    ride1.setReservedSpotsByUser(reservedSpotsByUserJson);

    // Act
    ReservationInfo result = rideService.getReservationInfoForUser(rideId, username);

    // Assert
    assertNotNull(result);
    assertEquals(2, result.getReservedSpots());
    assertEquals(20.0, result.getTotalPrice());
}

    @Test
    void deleteRide() {
        // Act
        rideService.deleteRide(1L);

        // Assert
        verify(rideRepository).findById(1L);
        verify(rideRepository).deleteById(1L);
        verify(userRepository, times(1)).save(any(User.class));
        verify(notificationService, times(1)).createNotification(any(NotificationDto.class), eq(ride1));
    }
}
