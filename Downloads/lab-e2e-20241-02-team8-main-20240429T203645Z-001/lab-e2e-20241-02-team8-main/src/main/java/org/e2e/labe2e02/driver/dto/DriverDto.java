package org.e2e.labe2e02.driver.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.vehicle.dto.VehicleBasicDto;

@Data
public class DriverDto {
    private Long id;
    @NotNull
    private Category category;
    @NotNull
    @Size(min = 2,max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2,max = 50)
    private String lastName;
    @NotNull
    @Positive
    private Integer trips;
    @NotNull
    @Positive
    private Float avgRating;
    @NotNull
    @Valid
    private VehicleBasicDto vehicle;
}