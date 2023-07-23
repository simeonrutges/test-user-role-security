package nl.novi.automate.service;

import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.exceptions.RecordNotFoundException;
import nl.novi.automate.model.Message;
import nl.novi.automate.model.Notification;
import nl.novi.automate.model.NotificationType;
import nl.novi.automate.model.User;
import nl.novi.automate.repository.MessageRepository;
import nl.novi.automate.repository.NotificationRepository;
import nl.novi.automate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public MessageService(MessageRepository messageRepository, DtoMapperService dtoMapperService, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.messageRepository = messageRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

public MessageDto createMessage(MessageDto messageDto) {
    User sender = userRepository.findByUsername(messageDto.getSenderUsername())
            .orElseThrow(() -> new RecordNotFoundException("Sender with username " + messageDto.getSenderUsername() + " not found"));
    User receiver = userRepository.findByUsername(messageDto.getReceiverUsername())
            .orElseThrow(() -> new RecordNotFoundException("Receiver with username " + messageDto.getReceiverUsername() + " not found"));

    Message message = dtoMapperService.dtoToMessage(messageDto);
    message.setSenderUsername(sender.getUsername());
    message.setReceiverUsername(receiver.getUsername());
    message.setTimestamp(LocalDateTime.now());
    Message savedMessage = messageRepository.save(message);

    Notification notification = new Notification();
    notification.setSender(sender);
    notification.setReceiver(receiver);
    notification.setType(NotificationType.NEW_MESSAGE);
    notification.setSentDate(LocalDateTime.now());
    notification.setRead(false);
    notification.setRideId(null);
    notificationRepository.save(notification);

    return dtoMapperService.messageToDto(savedMessage);
}

    public List<MessageDto> getMessagesForConversation(String sender, String receiver) {
        return messageRepository
                .findAllBySenderUsernameAndReceiverUsername(sender, receiver)
                .stream()
                .map(dtoMapperService::messageToDto)
                .collect(Collectors.toList());
    }

}

