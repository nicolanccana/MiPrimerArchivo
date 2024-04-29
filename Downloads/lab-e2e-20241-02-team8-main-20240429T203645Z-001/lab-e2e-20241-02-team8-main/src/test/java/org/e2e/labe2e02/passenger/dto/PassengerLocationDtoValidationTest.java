package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PassengerLocationDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }


    @Test
    void shouldFailValidationWhenCoordinateIsNull() {
        PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto();
        passengerLocationResponseDto.setCoordinate(null);
        passengerLocationResponseDto.setDescription("Description");

        Set<ConstraintViolation<PassengerLocationResponseDto>> violations = validator.validate(passengerLocationResponseDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCoordinateLatitudeIsTooLow() {
        CoordinateDto invalidCoordinate = new CoordinateDto();
        invalidCoordinate.setLatitude(-100.0);
        invalidCoordinate.setLongitude(-74.0060);
        PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto();
        passengerLocationResponseDto.setCoordinate(invalidCoordinate);
        passengerLocationResponseDto.setDescription("Description");

        Set<ConstraintViolation<PassengerLocationResponseDto>> violations = validator.validate(passengerLocationResponseDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCoordinateLatitudeIsTooHigh() {
        CoordinateDto invalidCoordinate = new CoordinateDto();
        invalidCoordinate.setLatitude(100.0);
        invalidCoordinate.setLongitude(-74.0060);
        PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto();
        passengerLocationResponseDto.setCoordinate(invalidCoordinate);
        passengerLocationResponseDto.setDescription("Description");

        Set<ConstraintViolation<PassengerLocationResponseDto>> violations = validator.validate(passengerLocationResponseDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCoordinateLongitudeIsTooLow() {
        CoordinateDto invalidCoordinate = new CoordinateDto();
        invalidCoordinate.setLatitude(40.7128);
        invalidCoordinate.setLongitude(-200.0);
        PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto();
        passengerLocationResponseDto.setCoordinate(invalidCoordinate);
        passengerLocationResponseDto.setDescription("Description");

        Set<ConstraintViolation<PassengerLocationResponseDto>> violations = validator.validate(passengerLocationResponseDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCoordinateLongitudeIsTooHigh() {
        CoordinateDto invalidCoordinate = new CoordinateDto();
        invalidCoordinate.setLatitude(40.7128);
        invalidCoordinate.setLongitude(200.0);
        PassengerLocationResponseDto passengerLocationResponseDto = new PassengerLocationResponseDto();
        passengerLocationResponseDto.setCoordinate(invalidCoordinate);
        passengerLocationResponseDto.setDescription("Description");

        Set<ConstraintViolation<PassengerLocationResponseDto>> violations = validator.validate(passengerLocationResponseDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenCoordinateIsNullInCoordinateDto() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(null);
        coordinateDto.setLongitude(null);

        Set<ConstraintViolation<CoordinateDto>> violations = validator.validate(coordinateDto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenLatitudeIsTooLowInCoordinateDto() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(-100.0);
        coordinateDto.setLongitude(-74.0060);

        Set<ConstraintViolation<CoordinateDto>> violations = validator.validate(coordinateDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenLatitudeIsTooHighInCoordinateDto() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(100.0);
        coordinateDto.setLongitude(-74.0060);

        Set<ConstraintViolation<CoordinateDto>> violations = validator.validate(coordinateDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenLongitudeIsTooLowInCoordinateDto() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(40.7128);
        coordinateDto.setLongitude(-200.0);

        Set<ConstraintViolation<CoordinateDto>> violations = validator.validate(coordinateDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenLongitudeIsTooHighInCoordinateDto() {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLatitude(40.7128);
        coordinateDto.setLongitude(200.0);

        Set<ConstraintViolation<CoordinateDto>> violations = validator.validate(coordinateDto);

        assertEquals(1, violations.size());
    }
}
