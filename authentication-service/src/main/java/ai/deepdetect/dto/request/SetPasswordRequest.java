package ai.deepdetect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPasswordRequest {
    @NotBlank(message = "token is required")
    private String token;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "minimum password length is 8")
    private String password;
}
