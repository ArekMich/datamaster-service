package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRepositoryFiles {

    private String username;
    private Set<FileDto> files;

    public UserRepositoryFiles(String username, Set<FileDto> files) {
        this.username = username;
        this.files = files;
    }
}
