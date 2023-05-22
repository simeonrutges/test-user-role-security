package nl.novi.automate.exceptions;

public class UserAlreadyAddedToRideException extends RuntimeException{
    public UserAlreadyAddedToRideException(String message) {
        super(message);
    }
}
