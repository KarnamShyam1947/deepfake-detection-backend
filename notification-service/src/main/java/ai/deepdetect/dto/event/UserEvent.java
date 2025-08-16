package ai.deepdetect.dto.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEvent {
    private int id;
    private String name;
    private String role;
    private String email;
    private String token;
    private String password;
    private String phoneNumber;
    private Date expirationDate;
}
