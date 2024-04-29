package org.e2e.labe2e02.driver.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.vehicle.dto.VehicleBasicDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class DriverDtoValidationTest {

    private Validator validator;

    @Mock
    private VehicleBasicDto vehicle;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        vehicle = new VehicleBasicDto();
        vehicle.setBrand("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setLicensePlate("ABC1234");
        vehicle.setFabricationYear(2020);
        vehicle.setCapacity(5);

    }

    @Test
    void shouldFailValidationWhenCategoryIsNull() {
        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setTrips(10);
        driverDto.setAvgRating(4.5f);
        driverDto.setVehicle(vehicle);

        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driverDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenFirstNameIsNull() {
        DriverDto driverDto = new DriverDto();
        driverDto.setCategory(Category.X);
        driverDto.setLastName("Doe");
        driverDto.setTrips(10);
        driverDto.setAvgRating(4.5f);
        driverDto.setVehicle(vehicle);

        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driverDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenTripsIsNegative() {
        DriverDto driverDto = new DriverDto();
        driverDto.setCategory(Category.X);
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setTrips(-5);
        driverDto.setAvgRating(4.5f);
        driverDto.setVehicle(vehicle);

        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driverDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenAvgRatingIsNegative() {
        DriverDto driverDto = new DriverDto();
        driverDto.setCategory(Category.X);
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setTrips(10);
        driverDto.setAvgRating(-4.5f);
        driverDto.setVehicle(vehicle);

        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driverDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenVehicleIsNullAndTripsIsNegative() {
        DriverDto driverDto = new DriverDto();
        driverDto.setCategory(Category.XL);
        driverDto.setFirstName("John");
        driverDto.setLastName("Doe");
        driverDto.setTrips(-1);
        driverDto.setAvgRating(4.5f);

        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driverDto);

        assertEquals(2, violations.size());
    }
}
