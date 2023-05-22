package nl.novi.automate.exceptions;

public class UserNotInRideException extends RuntimeException{
    public UserNotInRideException(String message) {
        super(message);
    }
}
