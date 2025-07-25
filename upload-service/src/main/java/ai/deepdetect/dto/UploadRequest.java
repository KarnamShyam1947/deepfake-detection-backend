package ai.deepdetect.dto;

import org.springframework.web.multipart.MultipartFile;

import ai.deepdetect.validators.FileExtensionAllowed;
import ai.deepdetect.validators.FileRequired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {
    
    @FileRequired(message = "video file is required.")
    @FileExtensionAllowed(extensions = {"mp4"}, message = "Only Video files are allowed")
    private MultipartFile video;

}
