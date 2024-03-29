package nl.novi.automate.dto;

import nl.novi.automate.model.NotificationType;

import java.time.LocalDateTime;

public class NotificationDto {
    private Long id;
    private UserDto sender;
    private UserDto receiver;
    private NotificationType type;
    private LocalDateTime sentDate;
    private boolean isRead;
    private String rideDetails;
    private Long rideId;

    public NotificationDto() {
    }

    public NotificationDto(Long id, UserDto sender, UserDto receiver, NotificationType type, LocalDateTime sentDate, boolean isRead, String rideDetails, Long rideId) {
        this.id = id;
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

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
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
