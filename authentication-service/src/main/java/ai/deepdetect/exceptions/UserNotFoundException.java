package ai.deepdetect.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Exception {

    private String email;
    
    public UserNotFoundException() {
        super("User not found");
    }
    
    public UserNotFoundException(String email) {
        super("User not found with email : " + email);
        this.email = email;
    }

}
