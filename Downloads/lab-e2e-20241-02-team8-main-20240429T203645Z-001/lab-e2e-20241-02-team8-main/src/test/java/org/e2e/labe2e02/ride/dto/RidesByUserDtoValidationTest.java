package org.e2e.labe2e02.ride.dto;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RidesByUserDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(null);
        ridesByUserDto.setOriginName("Origin");
        ridesByUserDto.setDestinationName("Destination");
        ridesByUserDto.setPrice(1.0);
        ridesByUserDto.setDepartureDate(LocalDateTime.now());

        var violations = validator.validate(ridesByUserDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenOriginNameAndDestinationNameAreNull() {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setOriginName(null);
        ridesByUserDto.setDestinationName(null);
        ridesByUserDto.setPrice(1.0);
        ridesByUserDto.setDepartureDate(LocalDateTime.now());

        var violations = validator.validate(ridesByUserDto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenOriginNameAndDestinationNameAreInvalid() {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setOriginName("a");
        ridesByUserDto.setDestinationName("a");
        ridesByUserDto.setPrice(1.0);
        ridesByUserDto.setDepartureDate(LocalDateTime.now());

        var violations = validator.validate(ridesByUserDto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenPriceIsNegative() {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setOriginName("Origin");
        ridesByUserDto.setDestinationName("Destination");
        ridesByUserDto.setPrice(-1.0);
        ridesByUserDto.setDepartureDate(LocalDateTime.now());

        var violations = validator.validate(ridesByUserDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenDepartureDateIsNull() {
        RidesByUserDto ridesByUserDto = new RidesByUserDto();
        ridesByUserDto.setId(1L);
        ridesByUserDto.setOriginName("Origin");
        ridesByUserDto.setDestinationName("Destination");
        ridesByUserDto.setPrice(1.0);
        ridesByUserDto.setDepartureDate(null);

        var violations = validator.validate(ridesByUserDto);

        assertEquals(1, violations.size());
    }

}
