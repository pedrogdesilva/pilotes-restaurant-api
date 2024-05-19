package com.api.pilotes.validator.validator;

import com.api.pilotes.entity.User;
import com.api.pilotes.persistence.UserRepository;
import com.api.pilotes.validator.annotation.PiloteAmountConstraint;
import com.api.pilotes.validator.annotation.UserExistsConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

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
