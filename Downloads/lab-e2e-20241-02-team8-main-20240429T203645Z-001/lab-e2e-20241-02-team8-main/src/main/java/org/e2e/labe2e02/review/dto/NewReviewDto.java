package org.e2e.labe2e02.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewReviewDto {
    @NotNull
    private String comment;
    @NotNull
    @DecimalMin("0")
    @DecimalMax("5")
    private Integer rating;
    @NotNull
    private Long rideId;
    @NotNull
    private Long targetId;
}