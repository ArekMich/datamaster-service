package com.agh.dataminingservice.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * SignUp payload request.
 * Client sends signup payload requests with user information for registration.
 * Every field in this payload is required.
 *
 * @author Arkadiusz Michalik
 */
@Data
public class SignUpRequest {

    @NotBlank(message = "Fields with first name and last name could not be blank.")
    @Size(min = 4, max = 60,
            message = "Number of characters in the first name and last name is incorrect. The intended range is between 4 and 60 chars.")
    private String name;

    @NotBlank(message = "Field with username could not be blank.")
    @Size(min = 3, max = 15,
            message = "Number of characters in username is incorrect. The intended range is between 3 and 15 chars.")
    private String username;

    @NotBlank(message = "Field with email could not be blank.")
    @Size(min = 3, max = 40,
            message = "Number of characters in email adress is incorrect. The intended range is between 3 and 40 chars.")
    @Email(message = "Incorrect email address.")
    private String email;

    @NotBlank(message = "Field with password could not be blank.")
    @Size(min = 6, max = 20, message = "Number of characters in password is incorrect. The intended range is between 6 and 20 chars.")
    private String password;
}
