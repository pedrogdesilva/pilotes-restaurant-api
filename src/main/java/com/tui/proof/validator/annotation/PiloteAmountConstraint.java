package com.tui.proof.validator.annotation;

import com.tui.proof.validator.validator.PiloteAmountValidator;
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
@Constraint(validatedBy = PiloteAmountValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PiloteAmountConstraint {
    String message() default "The order must inclue 5, 10 or 15 pilotes";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
