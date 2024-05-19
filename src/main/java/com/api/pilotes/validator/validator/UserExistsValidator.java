package com.api.pilotes.validator.validator;

import com.api.pilotes.entity.User;
import com.api.pilotes.persistence.UserRepository;
import com.api.pilotes.validator.annotation.UserExistsConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author Pedro Silva on 19/05/2024
 */
public class UserExistsValidator implements ConstraintValidator<UserExistsConstraint, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserExistsConstraint contactNumber) {
    }

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {

        if (userId == null) {
            return false;
        }

        Optional<User> findRes = userRepository.findById(userId);

        return findRes.isPresent();
    }
}
