package org.e2e.labe2e02.review.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewReviewDtoValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenCommentIsNull() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment(null);
        newReviewDto.setRating(5);
        newReviewDto.setRideId(123L);
        newReviewDto.setTargetId(456L);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenRatingIsNull() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment("Great service");
        newReviewDto.setRating(null);
        newReviewDto.setRideId(123L);
        newReviewDto.setTargetId(456L);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenRatingIsBelowMinimum() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment("Great service");
        newReviewDto.setRating(-1);
        newReviewDto.setRideId(123L);
        newReviewDto.setTargetId(456L);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenRatingIsAboveMaximum() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment("Great service");
        newReviewDto.setRating(6);
        newReviewDto.setRideId(123L);
        newReviewDto.setTargetId(456L);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenRideIdIsNull() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment("Great service");
        newReviewDto.setRating(5);
        newReviewDto.setRideId(null);
        newReviewDto.setTargetId(456L);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenTargetIdIsNull() {
        NewReviewDto newReviewDto = new NewReviewDto();
        newReviewDto.setComment("Great service");
        newReviewDto.setRating(5);
        newReviewDto.setRideId(123L);
        newReviewDto.setTargetId(null);

        Set<ConstraintViolation<NewReviewDto>> violations = validator.validate(newReviewDto);

        assertEquals(1, violations.size());
    }



}
