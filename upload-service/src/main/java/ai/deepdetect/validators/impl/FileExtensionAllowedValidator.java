package ai.deepdetect.validators.impl;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import ai.deepdetect.utils.FileUtils;
import ai.deepdetect.utils.StringUtils;
import ai.deepdetect.validators.FileExtensionAllowed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileExtensionAllowedValidator implements ConstraintValidator<FileExtensionAllowed, MultipartFile> {
    private Set<String> allowedExt;

    @Override
    public void initialize(FileExtensionAllowed ann) {
        allowedExt = Arrays.stream(ann.extensions())
            .map(String::toLowerCase).collect(Collectors.toSet());
    }

    @SuppressWarnings("null")
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext ctx) {
        if (file == null || file.isEmpty()) return true;
        String name = file.getOriginalFilename();
        if (!StringUtils.hasText(name) || !name.contains(".")) return false;
        String ext = FileUtils.getExtension(name).toLowerCase();
        return allowedExt.contains(ext);
    }
}
