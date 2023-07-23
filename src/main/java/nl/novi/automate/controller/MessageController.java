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

}
