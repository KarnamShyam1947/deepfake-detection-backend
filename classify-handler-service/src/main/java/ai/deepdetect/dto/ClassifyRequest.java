package ai.deepdetect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassifyRequest {

    @NotBlank(message = "size is required")
    private double size;

    @NotBlank(message = "url is required")
    private String url;

    @NotBlank(message = "filename is required")
    private String filename;
    
}
