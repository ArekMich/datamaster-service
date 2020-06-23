package com.agh.dataminingservice.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "Field with usernameOrEmail could not be blank.")
    private String usernameOrEmail;

    @NotBlank(message = "Field with password could not be blank.")
    private String password;
}
