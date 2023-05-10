package com.example.les16.exceptions;

public class UserAlreadyAddedToRideException extends RuntimeException{
    public UserAlreadyAddedToRideException(String message) {
        super(message);
    }
}
