package com.tui.proof.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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