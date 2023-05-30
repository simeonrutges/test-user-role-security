package nl.novi.automate.controller;

import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("")
    public ResponseEntity<MessageDto> createMessage(@RequestBody MessageDto messageDTO) {
        MessageDto createdMessage = messageService.createMessage(messageDTO);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/{sender}/{receiver}")
    public ResponseEntity<List<MessageDto>> getMessagesForConversation(@PathVariable String sender, @PathVariable String receiver) {
        List<MessageDto> messages = messageService.getMessagesForConversation(sender, receiver);
        return ResponseEntity.ok(messages);
    }

    //    @GetMapping("")
//    public ResponseEntity<List<MessageDto>> getAllMessages() {
//        List<MessageDto> messages = messageService.getAllMessages();
//        return ResponseEntity.ok(messages);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id) {
//        MessageDto message = messageService.getMessageById(id);
//        return ResponseEntity.ok(message);
//    }

    //    @GetMapping("/user/{username}")
//    public ResponseEntity<List<MessageDto>> getMessagesForUser(@PathVariable String username) {
//        List<MessageDto> messages = messageService.getMessagesForUser(username);
//        return ResponseEntity.ok(messages);
//    }

    // Andere endpoints indien nodig (update, delete,....)
}
