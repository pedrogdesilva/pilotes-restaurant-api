package com.api.pilotes.dto;

import com.api.pilotes.validator.annotation.PiloteAmountConstraint;
import com.api.pilotes.validator.annotation.UserExistsConstraint;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class OrderDTO {

    private Long id;

    @NotNull
    @UserExistsConstraint
    private Long userId;

    @NotNull
    @NotBlank
    private String deliveryAddress;

    @NotNull
    @PiloteAmountConstraint
    private Integer amountPilotes;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;

    private Float total;
}