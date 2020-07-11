package com.agh.dataminingservice.payload;

import com.agh.dataminingservice.security.UserPrincipal;
import lombok.Getter;
import lombok.Setter;

/**
 * UserSummary payload response.
 * This class provide only major information about user like id, login(username) and name(first name and last name).
 * Used in {@link com.agh.dataminingservice.controller.UserController#getCurrentUser(UserPrincipal)} method for getting
 * information about currently log in user.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UserSummary {

    /**
     * User id.
     */
    private Long id;

    /**
     * User login.
     */
    private String username;

    /**
     * User first name and last name.
     */
    private String name;

    /**
     * Creates object response with User major information.
     *
     * @param id       User id.
     * @param username User login.
     * @param name     User first name and last name.
     */
    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

}
