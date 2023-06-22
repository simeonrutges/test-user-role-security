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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        UserDto sender = new UserDto();
        sender.setUsername("testusername");
        sender.setPassword("testpassword");
        sender.setFirstname("testfirstname");
        sender.setLastname("testlastname");
        sender.setEmail("testemail@test.com");
        String[] roles = {"BESTUURDER"};
        sender.setRoles(roles);

        UserDto receiver = new UserDto();
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
//        dto.setRead(true);
        dto.setRideDetails("Test ride details");
        dto.setRideId(1L);

        LocalDateTime sentDate = LocalDateTime.of(2023, 6, 21, 22, 20, 5, 486068600);
        dto.setSentDate(sentDate);

        json = asJsonString(dto);

    }
//    @Test
//    void getAllNotifications_ReturnsNotificationList_WhenNotificationsExist() throws Exception {
//        List<NotificationDto> notificationDtoList = new ArrayList<>();
//        notificationDtoList.add(dto);
//
//        when(notificationService.getAllNotifications()).thenReturn(notificationDtoList);
//
//        mockMvc.perform(get("/notifications"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(notificationDtoList)));
//    }

//    @Test
//    void getNotificationById_ReturnsNotification_WhenNotificationExists() throws Exception {
//        when(notificationService.getNotificationById(1L)).thenReturn(dto);
//
//        mockMvc.perform(get("/notifications/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(dto)));
//    }

//    @Test
//    void createNotification_ReturnsCreatedNotification_WhenRequestIsValid() throws Exception {
//        when(notificationService.createNotification(dto)).thenReturn(dto);
//
//        mockMvc.perform(post("/notifications")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(dto)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(dto)));
//    }

    @Test
    void getNotificationsForUser_ReturnsNotificationList_WhenNotificationsExistForUser() throws Exception {
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        notificationDtoList.add(dto);

        when(notificationService.getNotificationsForUser("testuser")).thenReturn(notificationDtoList);

        mockMvc.perform(get("/notifications/user/testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(notificationDtoList)));
    }


//    private static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
