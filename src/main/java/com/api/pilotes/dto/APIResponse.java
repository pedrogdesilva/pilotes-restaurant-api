package com.api.pilotes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Pedro Silva on 19/05/2024
 */

@Getter
@Setter
public class APIResponse<T> {

    private T data;

    private boolean hasError;

    private String errorMessage;

    public APIResponse(T data) {
        this.data = data;
        this.hasError = false;
        this.errorMessage = "";
    }

    public APIResponse(String errorMessage) {
        this.hasError = true;
        this.errorMessage = errorMessage;
    }
}