package nl.novi.automate.repository;

import nl.novi.automate.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.senderUsername = :sender AND m.receiverUsername = :receiver) OR (m.senderUsername = :receiver AND m.receiverUsername = :sender)")
    List<Message> findAllBySenderUsernameAndReceiverUsername(String sender, String receiver);
}


