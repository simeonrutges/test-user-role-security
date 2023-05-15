package com.example.les16.exceptions;

public class UserNotInRideException extends RuntimeException{
    public UserNotInRideException(String message) {
        super(message);
    }
}
