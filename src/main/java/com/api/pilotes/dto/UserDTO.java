package com.api.pilotes.dto;

import com.api.pilotes.validator.annotation.UserExistsConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author Pedro Silva on 19/05/2024
 */

@Getter
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotNull
    @NotBlank
    private String fstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String phoneNumber;

}