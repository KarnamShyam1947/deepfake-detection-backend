package ai.deepdetect.services;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import ai.deepdetect.utils.FileUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final Cloudinary cloudinary;

    public Map<String,Object> generateSignature() {
        
        Map<String, Object> paramsToSign = new LinkedHashMap<>();
        String publicId = UUID.randomUUID().toString();
        
        long timestamp = System.currentTimeMillis() / 1000;
        paramsToSign.put("timestamp", timestamp);
        paramsToSign.put("public_id", publicId);
        paramsToSign.put("folder", "deepfake-uploads");
        

        String signature = cloudinary.apiSignRequest(paramsToSign, cloudinary.config.apiSecret);

        return Map.of(
            "timestamp", timestamp,
            "signature", signature,
            "folder", "deepfake-uploads",
            "api_key", cloudinary.config.apiKey,
            "cloud_name", cloudinary.config.cloudName
        );
    }

    public String uploadVideoFile(MultipartFile multipartVideo) throws IOException {
        File videoFile = FileUtils.convert(multipartVideo);

        Map<?, ?> result = cloudinary.uploader()
        .upload(videoFile, ObjectUtils.asMap(
            "resource_type", "video",
            "folder", "deepfake-detect",        
            "public_id", FileUtils.extractPublicId(multipartVideo.getOriginalFilename()),
            "allowed_formats", new String[]{"mp4","mov","webm"}
        ));

        videoFile.delete();

        return result.get("secure_url").toString();
    }

    public String uploadVideoFile(MultipartFile multipartVideo, String folderName) throws IOException {
        File videoFile = FileUtils.convert(multipartVideo);

        Map<?, ?> result = cloudinary.uploader()
        .upload(videoFile, ObjectUtils.asMap(
            "resource_type", "video",
            "folder", folderName,        
            "public_id", FileUtils.extractPublicId(multipartVideo.getOriginalFilename()),
            "allowed_formats", new String[]{"mp4","mov","webm"}
            
        ));

        videoFile.delete();

        return result.get("secure_url").toString();
    }

}
