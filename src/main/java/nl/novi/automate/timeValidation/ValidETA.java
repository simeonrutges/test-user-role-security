package nl.novi.automate.timeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ETAValidator.class)

public @interface ValidETA {
    String message() default "Ongeldige geschatte aankomsttijd";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
