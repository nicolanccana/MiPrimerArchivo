package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PassengerDtoValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;
    }


    @Test
    void shouldFailValidationWhenFirstNameAndLastNameIsLessThan2() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("J");
        passengerRequestDto.setLastName("D");
        passengerRequestDto.setEmail("validemail@email.com");
        passengerRequestDto.setPassword("password");
        passengerRequestDto.setPhoneNumber("123456789");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("John");
        passengerRequestDto.setLastName("Doe");
        passengerRequestDto.setEmail("invalidemail");
        passengerRequestDto.setPassword("password");
        passengerRequestDto.setPhoneNumber("123456789");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPhoneNumberIsInvalid() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("John");
        passengerRequestDto.setLastName("Doe");
        passengerRequestDto.setEmail("validemail@email.com");
        passengerRequestDto.setPassword("password");
        passengerRequestDto.setPhoneNumber("123");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldFailValidationWhenPasswordIsLessThan6() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("John");
        passengerRequestDto.setLastName("Doe");
        passengerRequestDto.setEmail("validemail@email.com");
        passengerRequestDto.setPassword("pass");
        passengerRequestDto.setPhoneNumber("123456789");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(1, violations.size());

    }


    @Test
    void requestDtoshouldFailValidationWhenLastNameAndFirstNameIsNull() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setEmail("validemail@email.com");
        passengerRequestDto.setPassword("password");
        passengerRequestDto.setPhoneNumber("123456789");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(2, violations.size());
    }

    @Test
    void requestDtoshouldFailValidationWhenEmailAndPasswordAreInvalid() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("John");
        passengerRequestDto.setLastName("Doe");
        passengerRequestDto.setPhoneNumber("123456789");
        passengerRequestDto.setPassword("pass");
        passengerRequestDto.setEmail("invalidemail");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(2, violations.size());
    }

    @Test
    void requestDtoshouldFailValidationWhenPhoneNumberIsNull() {
        PassengerRequestDto passengerRequestDto = new PassengerRequestDto();
        passengerRequestDto.setFirstName("John");
        passengerRequestDto.setLastName("Doe");
        passengerRequestDto.setEmail("validemail@email.com");
        passengerRequestDto.setPassword("password");

        Set<ConstraintViolation<PassengerRequestDto>> violations = validator.validate(passengerRequestDto);

        assertEquals(1, violations.size());
    }

}
