package org.e2e.labe2e02.ride.dto;

import jakarta.validation.Validator;
import org.e2e.labe2e02.coordinate.dto.CoordinateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RideRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }


    @Test
    void shouldFailValidationWhenOriginCoordinatesIsNull() {
        CoordinateDto destinationCoordinate = new CoordinateDto();
        destinationCoordinate.setLatitude(1.0);
        destinationCoordinate.setLongitude(1.0);

        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(null);
        rideRequestDto.setDestinationCoordinates(destinationCoordinate);
        rideRequestDto.setOriginName("Origin");
        rideRequestDto.setDestinationName("Destination");
        rideRequestDto.setPrice(1.0);
        rideRequestDto.setPassengerId(1L);
        rideRequestDto.setDriverId(2L);

        var violations = validator.validate(rideRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenDestinationCoordinatesIsNull() {
        CoordinateDto originCoordinates = new CoordinateDto();
        originCoordinates.setLatitude(1.0);
        originCoordinates.setLongitude(1.0);


        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(originCoordinates);
        rideRequestDto.setDestinationCoordinates(null);
        rideRequestDto.setOriginName("Origin");
        rideRequestDto.setPrice(1.0);
        rideRequestDto.setDestinationName("Destination");
        rideRequestDto.setPassengerId(1L);
        rideRequestDto.setDriverId(2L);

        var violations = validator.validate(rideRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailWjenOriginNameAndDestinationNameAreNull() {
        CoordinateDto originCoordinates = new CoordinateDto();
        originCoordinates.setLatitude(1.0);
        originCoordinates.setLongitude(1.0);

        CoordinateDto destinationCoordinates = new CoordinateDto();
        destinationCoordinates.setLatitude(1.0);
        destinationCoordinates.setLongitude(1.0);

        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(originCoordinates);
        rideRequestDto.setDestinationCoordinates(destinationCoordinates);
        rideRequestDto.setOriginName(null);
        rideRequestDto.setDestinationName(null);
        rideRequestDto.setPrice(10.0);
        rideRequestDto.setPassengerId(1L);
        rideRequestDto.setDriverId(2L);

        var violations = validator.validate(rideRequestDto);

        assertEquals(2, violations.size());
    }


    @Test
    void shouldFailValidationWhenPassengerIdOrDriverIdIsNull() {
        CoordinateDto originCoordinates = new CoordinateDto();
        originCoordinates.setLatitude(1.0);
        originCoordinates.setLongitude(1.0);

        CoordinateDto destinationCoordinates = new CoordinateDto();
        destinationCoordinates.setLatitude(1.0);
        destinationCoordinates.setLongitude(1.0);

        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(originCoordinates);
        rideRequestDto.setDestinationCoordinates(destinationCoordinates);
        rideRequestDto.setOriginName("Origin");
        rideRequestDto.setDestinationName("Destination");
        rideRequestDto.setPrice(10.0);
        rideRequestDto.setPassengerId(null);
        rideRequestDto.setDriverId(null);

        var violations = validator.validate(rideRequestDto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenPriceIsNegative() {
        CoordinateDto originCoordinates = new CoordinateDto();
        originCoordinates.setLatitude(1.0);
        originCoordinates.setLongitude(1.0);

        CoordinateDto destinationCoordinates = new CoordinateDto();
        destinationCoordinates.setLatitude(1.0);
        destinationCoordinates.setLongitude(1.0);

        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(originCoordinates);
        rideRequestDto.setDestinationCoordinates(destinationCoordinates);
        rideRequestDto.setOriginName("Origin");
        rideRequestDto.setDestinationName("Destination");
        rideRequestDto.setPassengerId(1L);
        rideRequestDto.setDriverId(2L);
        rideRequestDto.setPrice(-1.0);

        var violations = validator.validate(rideRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPriceIsNull() {
        CoordinateDto originCoordinates = new CoordinateDto();
        originCoordinates.setLatitude(1.0);
        originCoordinates.setLongitude(1.0);

        CoordinateDto destinationCoordinates = new CoordinateDto();
        destinationCoordinates.setLatitude(1.0);
        destinationCoordinates.setLongitude(1.0);

        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setOriginCoordinates(originCoordinates);
        rideRequestDto.setDestinationCoordinates(destinationCoordinates);
        rideRequestDto.setOriginName("Origin");
        rideRequestDto.setDestinationName("Destination");
        rideRequestDto.setPassengerId(1L);
        rideRequestDto.setDriverId(2L);
        rideRequestDto.setPrice(null);

        var violations = validator.validate(rideRequestDto);

        assertEquals(1, violations.size());
    }



}
