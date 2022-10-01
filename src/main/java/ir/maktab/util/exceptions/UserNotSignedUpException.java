package ir.maktab.util.exceptions;

public class UserNotSignedUpException extends RuntimeException{
    public UserNotSignedUpException(String message) {
        super(message);
    }
}
