package com.agh.dataminingservice.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Login payload request.
 * LoginRequest accept username and also email to authenticate.
 *
 * @author Arkadiusz Michalik
 */
@Data
public class LoginRequest {

    /**
     * Username or email
     */
    @NotBlank(message = "Field with usernameOrEmail could not be blank.")
    private String usernameOrEmail;

    @NotBlank(message = "Field with password could not be blank.")
    private String password;
}
