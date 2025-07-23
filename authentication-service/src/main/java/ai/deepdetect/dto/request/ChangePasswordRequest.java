package ai.deepdetect.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "old password is required")
    @Size(min = 8, message = "minimum password length is 8")
    private String oldPassword;

    @NotBlank(message = "new password is required")
    @Size(min = 8, message = "minimum password length is 8")
    private String newPassword;
}
