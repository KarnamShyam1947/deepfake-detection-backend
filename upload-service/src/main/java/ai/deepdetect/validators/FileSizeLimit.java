package ai.deepdetect.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface FileSizeLimit {
    String message() default "File size exceeds the allowed limit";
    long max();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
