package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PassengerResponseDto{
    @NotNull
    @Size(min = 2,max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2,max = 50)
    private String lastName;
    @NotNull
    @Size(min = 9,max = 15)
    private String phoneNumber;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Float avgRating;
}