package ai.deepdetect.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ai.deepdetect.validators.impl.FileRequiredImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = { FileRequiredImpl.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileRequired {
    String message() default "File Required";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
