package nl.novi.automate.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime sentDate;

    private boolean isRead;

    private String rideDetails;

    private Long rideId;


    public Notification() {
    }

    public Notification(User sender, User receiver, NotificationType type, LocalDateTime sentDate, boolean isRead, String rideDetails, Long rideId) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.sentDate = sentDate;
        this.isRead = isRead;
        this.rideDetails = rideDetails;
        this.rideId = rideId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getRideDetails() {
        return rideDetails;
    }

    public void setRideDetails(String rideDetails) {
        this.rideDetails = rideDetails;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
