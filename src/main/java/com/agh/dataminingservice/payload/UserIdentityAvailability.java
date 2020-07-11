package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * UserIdentityAvailability payload response.
 * Response give client information about user identity availability.
 * Used in {@link com.agh.dataminingservice.controller.UserController#checkUsernameAvailability(String)} and
 * {@link com.agh.dataminingservice.controller.UserController#checkEmailAvailability(String)}.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UserIdentityAvailability {

    /**
     * Information on whether the user's identity is available or not.
     */
    private Boolean available;

    /**
     * Creates response object with notification about checking availability information.
     *
     * @param available Information on whether the user's identity is available or not.
     */
    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

}
