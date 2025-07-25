package ai.deepdetect.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ai.deepdetect.validators.impl.FileExtensionAllowedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { FileExtensionAllowedValidator.class })
public @interface FileExtensionAllowed {
    String message() default "Invalid file extension";
    String[] extensions();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
