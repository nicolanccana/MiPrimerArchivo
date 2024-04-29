package org.e2e.labe2e02.driver.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.user.domain.Role;
import org.e2e.labe2e02.vehicle.domain.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NewDriverRequestDtoValidationTest {
    private Validator validator;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        vehicle = new Vehicle();
        vehicle.setLicensePlate("ABC123");
        vehicle.setBrand("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setFabricationYear(2020);
        vehicle.setCapacity(5);

    }

    @Test
    void shouldFailValidationWhenCategoryIsNull() {

        NewDriverRequestDto newDriverRequestDto = new NewDriverRequestDto();
        newDriverRequestDto.setVehicle(vehicle);
        newDriverRequestDto.setCategory(null);
        newDriverRequestDto.setRole(Role.DRIVER);
        newDriverRequestDto.setFirstName("John");
        newDriverRequestDto.setLastName("Doe");
        newDriverRequestDto.setEmail("john@example.com");
        newDriverRequestDto.setPhoneNumber("123456789");
        newDriverRequestDto.setPassword("password");
        newDriverRequestDto.setCreatedAt(ZonedDateTime.now());

        Set<ConstraintViolation<NewDriverRequestDto>> violations = validator.validate(newDriverRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenFirstNameIsNull() {
        NewDriverRequestDto newDriverRequestDto = new NewDriverRequestDto();
        newDriverRequestDto.setCategory(Category.X);
        newDriverRequestDto.setVehicle(vehicle);
        newDriverRequestDto.setRole(Role.DRIVER);
        newDriverRequestDto.setLastName("Doe");
        newDriverRequestDto.setEmail("john@example.com");
        newDriverRequestDto.setPhoneNumber("123456789");
        newDriverRequestDto.setPassword("password");
        newDriverRequestDto.setCreatedAt(ZonedDateTime.now());

        Set<ConstraintViolation<NewDriverRequestDto>> violations = validator.validate(newDriverRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        NewDriverRequestDto newDriverRequestDto = new NewDriverRequestDto();
        newDriverRequestDto.setCategory(Category.X);
        newDriverRequestDto.setVehicle(vehicle);
        newDriverRequestDto.setRole(Role.DRIVER);
        newDriverRequestDto.setFirstName("John");
        newDriverRequestDto.setLastName("Doe");
        newDriverRequestDto.setEmail("invalid-email");
        newDriverRequestDto.setPhoneNumber("123456789");
        newDriverRequestDto.setPassword("password");
        newDriverRequestDto.setCreatedAt(ZonedDateTime.now());

        Set<ConstraintViolation<NewDriverRequestDto>> violations = validator.validate(newDriverRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPhoneNumberLengthExceedsLimit() {
        NewDriverRequestDto newDriverRequestDto = new NewDriverRequestDto();
        newDriverRequestDto.setCategory(Category.X);
        newDriverRequestDto.setVehicle(vehicle);
        newDriverRequestDto.setRole(Role.DRIVER);
        newDriverRequestDto.setFirstName("John");
        newDriverRequestDto.setLastName("Doe");
        newDriverRequestDto.setEmail("john@example.com");
        newDriverRequestDto.setPhoneNumber("1234567890123456");
        newDriverRequestDto.setPassword("password");
        newDriverRequestDto.setCreatedAt(ZonedDateTime.now());

        Set<ConstraintViolation<NewDriverRequestDto>> violations = validator.validate(newDriverRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPasswordLengthIsLessThanMinimum() {
        NewDriverRequestDto newDriverRequestDto = new NewDriverRequestDto();
        newDriverRequestDto.setCategory(Category.X);
        newDriverRequestDto.setVehicle(vehicle);
        newDriverRequestDto.setRole(Role.DRIVER);
        newDriverRequestDto.setFirstName("John");
        newDriverRequestDto.setLastName("Doe");
        newDriverRequestDto.setEmail("john@example.com");
        newDriverRequestDto.setPhoneNumber("123456789");
        newDriverRequestDto.setPassword("pass");
        newDriverRequestDto.setCreatedAt(ZonedDateTime.now());

        Set<ConstraintViolation<NewDriverRequestDto>> violations = validator.validate(newDriverRequestDto);

        assertEquals(1, violations.size());
    }
}
