package ai.deepdetect.validators.impl;

import org.springframework.web.multipart.MultipartFile;

import ai.deepdetect.validators.FileSizeLimit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileSizeLimitValidator implements ConstraintValidator<FileSizeLimit, MultipartFile>  {

    private long size;

    @Override
    public void initialize(FileSizeLimit constraintAnnotation) {
        this.size = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) return true;
        return file.getSize() <= size; 
    }
    
}
