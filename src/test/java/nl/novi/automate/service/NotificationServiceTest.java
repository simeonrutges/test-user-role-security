package nl.novi.automate.service;

import nl.novi.automate.dto.NotificationDto;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.NotificationType;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.NotificationRepository;
import nl.novi.automate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;



import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    NotificationRepository notificationRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    DtoMapperService dtoMapperService;

    @InjectMocks
    NotificationService notificationService;

    @Captor
    ArgumentCaptor<Notification> argumentCaptor;

    Notification notification1;
    Notification notification2;

    @BeforeEach
    void setUp() {
            notification1 = new Notification();
            notification1.setId(1L);
            notification1.setSender(new User());
            notification1.setReceiver(new User());
            notification1.setType(NotificationType.PASSENGER_JOINED_RIDE);
            notification1.setSentDate(LocalDateTime.now());
            notification1.setRead(true);
            notification1.setRideDetails("Some details");
            notification1.setRideId(123L);

            notification2 = new Notification();
            notification2.setId(2L);
            notification2.setSender(new User());
            notification2.setReceiver(new User());
            notification2.setType(NotificationType.PASSENGER_LEFT_RIDE);
            notification2.setSentDate(LocalDateTime.now());
            notification2.setRead(false);
            notification2.setRideDetails("Some other details");
            notification2.setRideId(456L);
        }

    @Test
    void getNotificationById() {
        NotificationDto notificationDto = new NotificationDto();
        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification1));
        when(dtoMapperService.notificationToDto(notification1)).thenReturn(notificationDto);

        NotificationDto result = notificationService.getNotificationById(1L);
        assertEquals(notificationDto, result);
        verify(notificationRepository, times(1)).findById(1L);
        verify(dtoMapperService, times(1)).notificationToDto(notification1);
    }

    @Test
    void getNotificationsForUser() {
        NotificationDto notificationDto1 = new NotificationDto();
        NotificationDto notificationDto2 = new NotificationDto();
        when(notificationRepository.findByReceiverUsername(anyString())).thenReturn(Arrays.asList(notification1, notification2));
        when(dtoMapperService.notificationToDto(notification1)).thenReturn(notificationDto1);
        when(dtoMapperService.notificationToDto(notification2)).thenReturn(notificationDto2);

        List<NotificationDto> result = notificationService.getNotificationsForUser("username");
        assertEquals(2, result.size());
        assertEquals(notificationDto1, result.get(0));
        assertEquals(notificationDto2, result.get(1));
        verify(notificationRepository, times(1)).findByReceiverUsername(anyString());
        verify(dtoMapperService, times(1)).notificationToDto(notification1);
        verify(dtoMapperService, times(1)).notificationToDto(notification2);
    }
}