package org.e2e.labe2e02.coordinate.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class CoordinateDto {
    private Long id;
    @NotNull
    @DecimalMin("-90")
    @DecimalMax("90")
    private Double latitude;
    @NotNull
    @DecimalMin("-180")
    @DecimalMax("180")
    private Double longitude;
}