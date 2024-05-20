package com.tui.proof.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tui.proof.validator.annotation.PiloteAmountConstraint;
import com.tui.proof.validator.annotation.UserExistsConstraint;
import jakarta.validation.Valid;
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
    @Valid
    private AddressDTO deliveryAddress;

    @NotNull
    @PiloteAmountConstraint
    private Integer amountPilotes;

    @NotNull
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;

    private Float total;
}