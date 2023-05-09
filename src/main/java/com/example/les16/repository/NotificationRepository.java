package com.example.les16.repository;

import com.example.les16.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.receiver.username = :receiverUsername")
    List<Notification> findByReceiverUsername(@Param("receiverUsername") String receiverUsername);
}
