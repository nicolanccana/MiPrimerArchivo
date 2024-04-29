package org.e2e.labe2e02.ride.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;
import org.e2e.labe2e02.ride.domain.Status;

@Data
public class RideRequestDto {
    @NotNull
    private String originName;
    @NotNull
    @Size(min = 2,max = 255)
    private String destinationName;
    private Status status;
    @NotNull
    @Positive
    private Double price;
    @NotNull
    private CoordinateDto originCoordinates;
    @NotNull
    private CoordinateDto destinationCoordinates;
    @NotNull
    private Long passengerId;
    @NotNull
    private Long driverId;
}