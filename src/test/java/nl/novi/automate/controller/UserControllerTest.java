package nl.novi.automate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.dto.RideDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private UserDto dto;

    @BeforeEach
    public void setUp() {
        dto = new UserDto();

        dto.setUsername("testUsername");
        dto.setPassword("testPassword");
        dto.setFirstname("testFirstname");
        dto.setLastname("testLastname");
        dto.setEmail("test@email.com");
        dto.setRoles(new String[] {"ROLE_USER"});
    }

    @Test
    public void testCreateUser() throws Exception {
        dto.setPassword("password");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userService.createUser(any())).thenReturn("newUsername");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("newUsername"));

        verify(passwordEncoder, times(1)).encode(any());
        verify(userService, times(1)).createUser(any());
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(anyString(), any())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{username}", "testuser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));

        verify(userService, times(1)).updateUser(anyString(), any());
    }

    @Test
    public void testAddRideToUser() throws Exception {
        doNothing().when(userService).addRideToUser(anyString(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/{username}/{rideId}", "testuser", 1L))
                .andExpect(status().isOk());

        verify(userService, times(1)).addRideToUser(anyString(), anyLong());
    }

    @Test
    public void testDeleteProfileImage() throws Exception {
        doNothing().when(userService).deleteProfileImage(anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/deleteProfileImage/{username}", "testuser"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteProfileImage(anyString());
    }

    @Test
    public void testGetRidesForUser() throws Exception {
        when(userService.findRidesForUser(anyString())).thenReturn(new ArrayList<RideDto>());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{username}/rides", "testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(userService, times(1)).findRidesForUser(anyString());
    }
    @Test
    public void testCreateUserWithoutEmail() throws Exception {
        dto.setEmail(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(passwordEncoder, times(0)).encode(any());
        verify(userService, times(0)).createUser(any());
    }

    @Test
    public void testCreateUserWithInvalidEmail() throws Exception {
        dto.setEmail("invalidEmail");

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(passwordEncoder, times(0)).encode(any());
        verify(userService, times(0)).createUser(any());
    }
}