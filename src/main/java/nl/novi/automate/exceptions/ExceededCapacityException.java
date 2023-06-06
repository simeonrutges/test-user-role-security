package nl.novi.automate.exceptions;

public class ExceededCapacityException extends RuntimeException{
    public ExceededCapacityException(String message) {
        super(message);
    }
}
