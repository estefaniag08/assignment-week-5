package dev.applaudostudios.examples.assignmentweek5.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumberValid {
    String countryIndicator() default "";
    int numberLength();

    String message() default "Invalid phone number.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
