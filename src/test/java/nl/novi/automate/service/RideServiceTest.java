package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserAlreadyAddedToRideException;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.NotificationType;
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
import java.util.ArrayList;
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

    RideDto rideDto1;
    RideDto rideDto2;
    RideDto rideDto3;

    User user1;

    UserDto userDto1;

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
    void shouldAddUserToRideSuccessfully() {
        // Gegeven
        Long rideId = ride1.getId();
        String username = "username1";

        User user = new User();
        user.setUsername(username);
        // Initialiseren van andere gebruikersvelden...

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride1));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Wanneer
        rideService.addUserToRide(rideId, username);

        // Dan
        verify(userRepository).save(user);
        verify(rideRepository).save(ride1);
        assertTrue(ride1.getUsers().contains(user));
        assertTrue(user.getRides().contains(ride1));
    }

    @Test
    void shouldThrowUserAlreadyAddedToRideException() {
        // Gegeven
        Long rideId = ride1.getId();
        String username = "username1";

        User user = new User();
        user.setUsername(username);
        // Initialiseren van andere gebruikersvelden...

        ride1.getUsers().add(user);
        user.getRides().add(ride1);

        when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride1));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Wanneer
        Exception exception = assertThrows(UserAlreadyAddedToRideException.class, () -> {
            rideService.addUserToRide(rideId, username);
        });

        // Dan
        assertEquals("User already added to this ride", exception.getMessage());
    }

    @Test
    void shouldThrowRecordNotFoundException() {
        // Gegeven
        Long rideId = ride1.getId();
        String username = "username1";

        when(rideRepository.findById(rideId)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Wanneer
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            rideService.addUserToRide(rideId, username);
        });

        // Dan
        // Hier kan je een specifieke foutmelding controleren als je deze hebt ingesteld in de RecordNotFoundException
        // assertEquals("Expected exception message", exception.getMessage());
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
}