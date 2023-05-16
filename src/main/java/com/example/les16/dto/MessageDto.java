package com.example.les16.dto;

import com.example.les16.model.User;

import java.time.LocalDateTime;

public class MessageDto {
    public Long id;
    public UserDto senderUsername;
    public UserDto receiverUsername;
    public String content;
    public LocalDateTime timestamp;
    public boolean read;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(UserDto senderUsername) {
        this.senderUsername = senderUsername;
    }

    public UserDto getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(UserDto receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}