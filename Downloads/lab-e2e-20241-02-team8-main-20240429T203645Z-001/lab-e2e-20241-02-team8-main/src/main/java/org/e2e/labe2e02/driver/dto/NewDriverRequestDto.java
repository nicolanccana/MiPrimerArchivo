package org.e2e.labe2e02.driver.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.user.domain.Role;
import org.e2e.labe2e02.vehicle.domain.Vehicle;

import java.time.ZonedDateTime;

@Data
public class NewDriverRequestDto {
    @NotNull
    private Category category;
    @NotNull
    @Valid
    private Vehicle vehicle;
    @NotNull
    private Role role;
    @NotNull
    @Size(min = 2,max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2,max=50)
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 9,max = 15)
    private String phoneNumber;
    @NotNull
    @Size(min = 6,max = 50)
    private String password;
    @NotNull
    private ZonedDateTime createdAt;
}
