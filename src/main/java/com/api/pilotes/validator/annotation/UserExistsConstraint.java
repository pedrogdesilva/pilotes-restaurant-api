package com.api.pilotes.validator.annotation;

import com.api.pilotes.validator.validator.UserExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pedro Silva on 19/05/2024
 */

@Documented
@Constraint(validatedBy = UserExistsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExistsConstraint {
    String message() default "User does not exist in system";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
