package nl.novi.automate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.novi.automate.dto.MessageDto;
import nl.novi.automate.model.Message;
import nl.novi.automate.security.JwtService;
import nl.novi.automate.service.MessageService;
import nl.novi.automate.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @MockBean
    private MessageService messageService;

    Message message1;
    Message message2;

    MessageDto messageDto1;
    MessageDto messageDto2;

    @BeforeEach
    void setUp() {
        LocalDateTime currentTimestamp = LocalDateTime.now();

        message1 = new Message(1L, "John","Annet",  "hello Annet",  currentTimestamp, false);
        message2 = new Message(2L, "Annet", "John", "Hi John!", currentTimestamp, false);

        messageDto1 = new MessageDto();
        messageDto1.setId(1L);
        messageDto1.setSenderUsername("John");
        messageDto1.setReceiverUsername("Annet");
        messageDto1.setContent("hello Annet");
        messageDto1.setTimestamp(currentTimestamp);
        messageDto1.setRead(false);

        messageDto2 = new MessageDto();
        messageDto2.setId(2L);
        messageDto2.setSenderUsername("Annet");
        messageDto2.setReceiverUsername("John");
        messageDto2.setContent("Hi John!");
        messageDto2.setTimestamp(currentTimestamp);
        messageDto2.setRead(false);
    }

    @Test
    void shouldGetMessagesForConversation() throws Exception {
        List<MessageDto> messageDtos = Arrays.asList(messageDto1, messageDto2);

        when(messageService.getMessagesForConversation(anyString(), anyString())).thenReturn(messageDtos);

        mockMvc.perform(get("/messages/John/Annet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(messageDto1.getId()))
                .andExpect(jsonPath("$[0].senderUsername").value(messageDto1.getSenderUsername()))
                .andExpect(jsonPath("$[0].receiverUsername").value(messageDto1.getReceiverUsername()))
                .andExpect(jsonPath("$[0].content").value(messageDto1.getContent()))
                // Check the timestamp pattern
                .andExpect(jsonPath("$[0].timestamp", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+")))
                .andExpect(jsonPath("$[0].read").value(messageDto1.isRead()))
                .andExpect(jsonPath("$[1].id").value(messageDto2.getId()))
                .andExpect(jsonPath("$[1].senderUsername").value(messageDto2.getSenderUsername()))
                .andExpect(jsonPath("$[1].receiverUsername").value(messageDto2.getReceiverUsername()))
                .andExpect(jsonPath("$[1].content").value(messageDto2.getContent()))
                // Check the timestamp pattern
                .andExpect(jsonPath("$[1].timestamp", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+")))
                .andExpect(jsonPath("$[1].read").value(messageDto2.isRead()));

        verify(messageService).getMessagesForConversation(anyString(), anyString());
    }

}
