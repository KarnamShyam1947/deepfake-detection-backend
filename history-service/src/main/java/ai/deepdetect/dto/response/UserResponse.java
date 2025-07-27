package ai.deepdetect.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String role;
    private String email;
    private String token;
    private String password;
    private String phoneNumber;
    private Date expirationDate;
}
