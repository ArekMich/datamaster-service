package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * UserRepositoryFiles payload response.
 * UserRepositoryFiles give client all information about files saved in database by user as a set of {@link FileDto} objects.
 *
 * @author Arkadiusz Michalik
 * @see FileDto
 */
@Getter
@Setter
public class UserRepositoryFiles {

    /**
     * User login.
     */
    private String username;

    /**
     * Set of {@link FileDto} objects.
     */
    private Set<FileDto> files;

    /**
     * Creates response object with user login and set of {@link FileDto} objects.
     *
     * @param username User login.
     * @param files Set of {@link FileDto} objects.
     */
    public UserRepositoryFiles(String username, Set<FileDto> files) {
        this.username = username;
        this.files = files;
    }
}
