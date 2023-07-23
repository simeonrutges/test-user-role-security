package nl.novi.automate.service;

import nl.novi.automate.dto.UserDto;
import nl.novi.automate.exceptions.ExtensionNotSupportedException;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.exceptions.UserNotFoundException;
import nl.novi.automate.model.Car;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.CarRepository;
import nl.novi.automate.repository.RideRepository;
import nl.novi.automate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    CarRepository carRepository;

    @Mock
    DtoMapperService dtoMapperService;

    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<User> argumentCaptor;

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("username1");
        user1.setFirstname("firstname1");
        user1.setLastname("lastname1");
        user1.setEmail("email1@test.com");
        user1.setEnabled(true);
        user1.setPhoneNumber(123456789);
        user1.setBio("bio1");

        user2 = new User();
        user2.setUsername("username2");
        user2.setFirstname("firstname2");
        user2.setLastname("lastname2");
        user2.setEmail("email2@test.com");
        user2.setEnabled(true);
        user2.setPhoneNumber(987654321);
        user2.setBio("bio2");
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        when(dtoMapperService.dtoToUser(userDto)).thenReturn(user1);
        userService.createUser(userDto);
        verify(userRepository, times(1)).save(argumentCaptor.capture());
        assertEquals(user1, argumentCaptor.getValue());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(dtoMapperService.userToDto(user1)).thenReturn(new UserDto());
        when(dtoMapperService.userToDto(user2)).thenReturn(new UserDto());
        List<UserDto> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        UserDto userDto = new UserDto();
        assertThrows(RecordNotFoundException.class, () -> userService.updateUser("username", userDto));
    }

    @Test
    void testUploadFileDocument_NotJpgOrJpeg() {
        User user = new User();
        user.setUsername("username");
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("filename.png");
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));
        assertThrows(ExtensionNotSupportedException.class, () -> userService.uploadFileDocument("username", multipartFile));
    }

    @Test
    void testUploadFileDocument_JpgOrJpeg() throws IOException, ExtensionNotSupportedException {
        User user = new User();
        user.setUsername("username");
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getOriginalFilename()).thenReturn("filename.jpg"); // of "filename.jpeg"
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(user));

        User result = userService.uploadFileDocument("username", multipartFile);

        assertEquals("filename.jpg", result.getFileName());
    }

    @Test
    void testDeleteProfileImage_NotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.deleteProfileImage("username"));
    }

    @Test
    void testFindRidesForUser_NotFound() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findRidesForUser("username"));
    }
}