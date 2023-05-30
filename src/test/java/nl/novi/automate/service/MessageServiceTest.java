package nl.novi.automate.service;

import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.model.Message;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.MessageRepository;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private DtoMapperService dtoMapperService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private MessageService messageService;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;
    @Captor
    private ArgumentCaptor<Notification> notificationCaptor;

    private User sender;
    private User receiver;
    private MessageDto messageDto;
    private Message message;

    @BeforeEach
    void setUp() {
        sender = new User();
        sender.setUsername("sender");

        receiver = new User();
        receiver.setUsername("receiver");

        messageDto = new MessageDto();
        messageDto.setSenderUsername("sender");
        messageDto.setReceiverUsername("receiver");

        message = new Message();
        message.setSenderUsername("sender");
        message.setReceiverUsername("receiver");
    }

    @Test
    void createMessage() {
        when(userRepository.findByUsername("sender")).thenReturn(Optional.of(sender));
        when(userRepository.findByUsername("receiver")).thenReturn(Optional.of(receiver));
        when(dtoMapperService.dtoToMessage(messageDto)).thenReturn(message);
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        when(dtoMapperService.messageToDto(any(Message.class))).thenReturn(messageDto);

        // Act
        MessageDto createdMessageDto = messageService.createMessage(messageDto);

        // Assert
        verify(messageRepository).save(messageCaptor.capture());
        verify(notificationRepository).save(notificationCaptor.capture());
        Message capturedMessage = messageCaptor.getValue();
        Notification capturedNotification = notificationCaptor.getValue();

        assertEquals("sender", capturedMessage.getSenderUsername());
        assertEquals("receiver", capturedMessage.getReceiverUsername());
        assertEquals("sender", capturedNotification.getSender().getUsername());
        assertEquals("receiver", capturedNotification.getReceiver().getUsername());
        assertEquals(messageDto, createdMessageDto);
    }

    @Test
    void getMessagesForConversation() {
        // Arrange
        String senderUsername = "sender";
        String receiverUsername = "receiver";

        Message message1 = new Message();
        message1.setSenderUsername(senderUsername);
        message1.setReceiverUsername(receiverUsername);

        Message message2 = new Message();
        message2.setSenderUsername(senderUsername);
        message2.setReceiverUsername(receiverUsername);

        MessageDto messageDto1 = new MessageDto();
        messageDto1.setSenderUsername(senderUsername);
        messageDto1.setReceiverUsername(receiverUsername);

        MessageDto messageDto2 = new MessageDto();
        messageDto2.setSenderUsername(senderUsername);
        messageDto2.setReceiverUsername(receiverUsername);

        when(messageRepository.findAllBySenderUsernameAndReceiverUsername(senderUsername, receiverUsername)).thenReturn(Arrays.asList(message1, message2));
        when(dtoMapperService.messageToDto(message1)).thenReturn(messageDto1);
        when(dtoMapperService.messageToDto(message2)).thenReturn(messageDto2);

        // Act
        List<MessageDto> messages = messageService.getMessagesForConversation(senderUsername, receiverUsername);

        // Assert
        assertEquals(2, messages.size());
        assertEquals(senderUsername, messages.get(0).getSenderUsername());
        assertEquals(receiverUsername, messages.get(0).getReceiverUsername());
        assertEquals(senderUsername, messages.get(1).getSenderUsername());
        assertEquals(receiverUsername, messages.get(1).getReceiverUsername());
    }
}
