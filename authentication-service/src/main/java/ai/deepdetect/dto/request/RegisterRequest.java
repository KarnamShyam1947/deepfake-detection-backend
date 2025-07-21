package ai.deepdetect.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "name is required")
    private String name;
    
    @NotBlank(message = "email is required")
    @Email(message = "provide valid email")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "minimum password length is 8")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must be at least 8 chars, include uppercase, lowercase, digit and special char."
    )
    private String password;

    @Pattern(
        regexp = "^\\d{10}$",
        message = "Phone number must be exactly 10 digits"
    )
    private String phoneNumber;
    
}
