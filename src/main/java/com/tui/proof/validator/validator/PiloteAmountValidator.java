package com.tui.proof.validator.validator;

import com.tui.proof.validator.annotation.PiloteAmountConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author Pedro Silva on 19/05/2024
 */
public class PiloteAmountValidator implements ConstraintValidator<PiloteAmountConstraint, Integer> {

    @Override
    public void initialize(PiloteAmountConstraint contactNumber) {
    }

    @Override
    public boolean isValid(Integer amount, ConstraintValidatorContext constraintValidatorContext) {
        return amount == 5 || amount == 10 || amount == 15;
    }
}
