package org.e2e.labe2e02.passenger.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PassengerRequestDto {
    @NotNull
    @Size(min = 2,max = 50)
    private String firstName;
    @NotNull
    @Size(min = 2,max = 50)
    private String lastName;
    @Email
    private String email;
    @NotNull
    @Size(min = 6,max = 50)
    private String password;
    @NotNull
    @Size(min = 9,max = 15)
    private String phoneNumber;
}
