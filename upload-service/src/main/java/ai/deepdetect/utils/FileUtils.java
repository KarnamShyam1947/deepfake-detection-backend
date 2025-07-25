package ai.deepdetect.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    public static String getExtension(String filename) {
        if (filename == null)
            return null;
        int index = filename.lastIndexOf('.');
        return (index == -1 || index == filename.length() - 1) ? "" : filename.substring(index + 1);
    }

    public static File convert(MultipartFile file) throws IOException {
        File conv = File.createTempFile("upload-", "-" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(conv)) {
            fos.write(file.getBytes());
        }
        return conv;
    }

    public static String extractPublicId(String filename) {
        if (filename == null)
            return null;
        int dot = filename.lastIndexOf('.');
        return dot > 0 ? filename.substring(0, dot) : filename;
    }
}
