package ai.deepdetect.validators.impl;

import org.springframework.web.multipart.MultipartFile;

import ai.deepdetect.validators.FileRequired;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileRequiredImpl implements ConstraintValidator<FileRequired, MultipartFile> {
    @SuppressWarnings("null")
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext ctx) {
        return file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0;
    }   
}
