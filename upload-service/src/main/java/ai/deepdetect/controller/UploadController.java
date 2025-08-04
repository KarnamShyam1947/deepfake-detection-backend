package ai.deepdetect.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ai.deepdetect.dto.UploadRequest;
import ai.deepdetect.services.UploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class UploadController {

    private final UploadService uploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> upload(@Valid UploadRequest uploadRequest) throws IOException {
        MultipartFile video = uploadRequest.getVideo();
        String url = uploadService.uploadVideoFile(video);

        String filename = video.getOriginalFilename();
        double sizeInKB = video.getSize() / 1024;

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of(
                        "message", "video uploaded successfully",
                        "publicUrl", url,
                        "filename", filename,
                        "size", sizeInKB));

    }

}
