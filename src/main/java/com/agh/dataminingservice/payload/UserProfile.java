package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * UserProfile payload response.
 * Response contains information about user profile which includes id, username, first name, last name
 * and date when user register successful.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UserProfile {

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
     * Date and time when user register successful.
     */
    private Instant joinedAt;

    /**
     * Creates response object with user profile.
     *
     * @param id       User id.
     * @param username User login.
     * @param name     User first name and last name.
     * @param joinedAt Date and time when user register successful.
     */
    public UserProfile(Long id, String username, String name, Instant joinedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
    }

}
