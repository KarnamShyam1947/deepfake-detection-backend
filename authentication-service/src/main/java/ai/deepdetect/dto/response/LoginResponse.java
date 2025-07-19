package ai.deepdetect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private String jwtToken; 
    private String name;
    private String role;
    private String email;
    private String phoneNumber;  
}
