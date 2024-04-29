package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;

@Data
public class PassengerLocationResponseDto {
    @NotNull
    @Valid
    private CoordinateDto coordinate;
    @NotNull
    @Size(min = 2,max = 255)
    private String description;
}
