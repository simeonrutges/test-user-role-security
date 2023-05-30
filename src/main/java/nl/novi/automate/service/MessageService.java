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
    // Controleer of de sender en receiver bestaan
    User sender = userRepository.findByUsername(messageDto.getSenderUsername())
            .orElseThrow(() -> new RecordNotFoundException("Sender with username " + messageDto.getSenderUsername() + " not found"));
    User receiver = userRepository.findByUsername(messageDto.getReceiverUsername())
            .orElseThrow(() -> new RecordNotFoundException("Receiver with username " + messageDto.getReceiverUsername() + " not found"));

    // Maak de Message en sla deze op
    Message message = dtoMapperService.dtoToMessage(messageDto);
    message.setSenderUsername(sender.getUsername());
    message.setReceiverUsername(receiver.getUsername());
    message.setTimestamp(LocalDateTime.now());
    Message savedMessage = messageRepository.save(message);

    // Maak de Notification en sla deze op
    Notification notification = new Notification();
    notification.setSender(sender);
    notification.setReceiver(receiver);
    notification.setType(NotificationType.NEW_MESSAGE);
    notification.setSentDate(LocalDateTime.now());
    notification.setRead(false);
    notification.setRideId(null); // Of zet dit op een geldige rit ID als dit relevant is
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


////29-5 tijdens test uitgecomm.
//    public List<MessageDto> getMessagesForUser(String username) {
//        return messageRepository.findBySenderUsernameOrReceiverUsername(username, username).stream()
//                .map(dtoMapperService::messageToDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<MessageDto> getAllMessages() {
//        return messageRepository.findAll().stream()
//                .map(dtoMapperService::messageToDto)
//                .collect(Collectors.toList());
//    }
//
//    public MessageDto getMessageById(Long id) {
//        Message message = messageRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException("Message with id " + id + " not found"));
//        return dtoMapperService.messageToDto(message);
//    }



//    ruim voor 19/5 uitgrcomm..":
    //    public MessageDto createMessage(MessageDto messageDto) {
//        // Controleer of de sender en receiver bestaan
//        User sender = userRepository.findByUsername(messageDto.getSenderUsername())
//                .orElseThrow(() -> new RecordNotFoundException("Sender with username " + messageDto.getSenderUsername() + " not found"));
//        User receiver = userRepository.findByUsername(messageDto.getReceiverUsername())
//                .orElseThrow(() -> new RecordNotFoundException("Receiver with username " + messageDto.getReceiverUsername() + " not found"));
//
//        // Maak de Message en sla deze op
//        Message message = dtoMapperService.dtoToMessage(messageDto);
//        message.setSenderUsername(sender.getUsername());
//        message.setReceiverUsername(receiver.getUsername());
//        message.setTimestamp(LocalDateTime.now());
//        Message savedMessage = messageRepository.save(message);
//
//        return dtoMapperService.messageToDto(savedMessage);
//    }
    //
//    public List<MessageDto> getMessagesForConversation(String sender, String receiver) {
//        return messageRepository
//                .findAllBySenderUsernameAndReceiverUsernameOrSenderUsernameAndReceiverUsername(sender, receiver, receiver, sender)
//                .stream()
//                .map(dtoMapperService::messageToDto)
//                .collect(Collectors.toList());
//    }

}

