package nl.novi.automate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.dto.UserDto;
import nl.novi.automate.model.NotificationType;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.NotificationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.Collections;  // Voor Collections.singletonList

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    private UserService userService;

    @MockBean
    private NotificationService notificationService;

    private static ObjectMapper objectMapper;

    private NotificationDto dto;
    private UserDto sender;
    private UserDto receiver;
    private String json;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        dto = new NotificationDto();
        sender = new UserDto();
        sender.setUsername("testusername");
        sender.setPassword("testpassword");
        sender.setFirstname("testfirstname");
        sender.setLastname("testlastname");
        sender.setEmail("testemail@test.com");
        String[] roles = {"BESTUURDER"};
        sender.setRoles(roles);

        receiver = new UserDto();
        receiver.setUsername("testusername2");
        receiver.setPassword("testpassword2");
        receiver.setFirstname("testfirstname2");
        receiver.setLastname("testlastname2");
        receiver.setEmail("testemail2@test.com");
        String[] rolesReceiver = {"PASSAGIER"};
        receiver.setRoles(rolesReceiver);


        dto.setId(1L);
        dto.setSender(sender);
        dto.setReceiver(receiver);
        dto.setType(NotificationType.PASSENGER_JOINED_RIDE);
        dto.setSentDate(LocalDateTime.now());
        dto.setRideDetails("Test ride details");
        dto.setRideId(1L);

        LocalDateTime sentDate = LocalDateTime.of(2023, 6, 21, 22, 20, 5, 486068600);
        dto.setSentDate(sentDate);

        json = asJsonString(dto);

        List<NotificationDto> notifications = new ArrayList<>();
        notifications.add(dto);

        when(notificationService.getNotificationsForUser(anyString())).thenReturn(notifications);

    }



    @Test
    public void getNotificationById() throws Exception {
        when(notificationService.getNotificationById(anyLong())).thenReturn(dto);

        mockMvc.perform(get("/notifications/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId().intValue())));

        verify(notificationService, times(1)).getNotificationById(anyLong());
    }

    @Test
    public void getNotificationsForUser() throws Exception {
        mockMvc.perform(get("/notifications/user/{username}", "testusername2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
