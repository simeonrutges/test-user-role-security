package com.example.les16.service;

import com.example.les16.dto.MessageDto;
import com.example.les16.exceptions.RecordNotFoundException;
import com.example.les16.model.Message;
import com.example.les16.model.User;
import com.example.les16.repository.MessageRepository;
import com.example.les16.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final DtoMapperService dtoMapperService;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, DtoMapperService dtoMapperService, UserRepository userRepository) {  // Voeg DtoMapperService dtoMapperService toe aan de constructor
        this.messageRepository = messageRepository;
        this.dtoMapperService = dtoMapperService;
        this.userRepository = userRepository;
    }

    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(dtoMapperService::messageToDto)
                .collect(Collectors.toList());
    }

    public MessageDto getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Message with id " + id + "not found"));
        return dtoMapperService.messageToDto(message);
    }

    public MessageDto createMessage(MessageDto messageDto) {
        // Controleer of de sender en receiver bestaan
        User sender = userRepository.findByUsername(messageDto.getSenderUsername().username)
                .orElseThrow(() -> new RecordNotFoundException("Sender with username " + messageDto.getSenderUsername().getUsername() + " not found"));
        User receiver = userRepository.findByUsername(messageDto.getReceiverUsername().username)
                .orElseThrow(() -> new RecordNotFoundException("Receiver with username " + messageDto.getReceiverUsername().getUsername() + " not found"));

        // Maak de Message en sla deze op
        Message message = dtoMapperService.dtoToMessage(messageDto);
        Message savedMessage = messageRepository.save(message);

        return dtoMapperService.messageToDto(savedMessage);
    }


    public List<MessageDto> getMessagesForUser(String username) {
        return messageRepository.findBySender_UsernameOrReceiver_Username(username, username).stream()
                .map(dtoMapperService::messageToDto)
                .collect(Collectors.toList());
    }
    public List<MessageDto> getMessagesForConversation(String sender, String receiver) {
        return messageRepository.findBySender_UsernameAndReceiver_Username(sender, receiver).stream()
                .map(dtoMapperService::messageToDto)
                .collect(Collectors.toList());
    }






    // Andere methoden indien nodig (bijv. update, delete)
}