package com.example.les16.repository;

import com.example.les16.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver_Username(String username);

    List<Message> findBySender_UsernameOrReceiver_Username(String senderUsername, String receiverUsername);

    List<Message> findBySender_UsernameAndReceiver_Username(String senderUsername, String receiverUsername);

}

