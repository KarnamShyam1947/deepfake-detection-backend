package ai.deepdetect.exceptions;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
        super("User Already exists");
    }

    public UserAlreadyExistsException(String errorMsg) {
        super(errorMsg);
    }
    
}
