package org.e2e.labe2e02.vehicle.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleBasicDto {
    @NotNull
    private String brand;
    @NotNull
    private String model;
    @NotNull
    private String licensePlate;
    @NotNull
    @DecimalMin("1900")
    @DecimalMax("2024")
    private Integer fabricationYear;
    private Integer capacity;
}