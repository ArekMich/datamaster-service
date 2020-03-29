package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRepositoryFilesId {

    private String username;

    private Set<String> filesId;

    public UserRepositoryFilesId(String username, Set<String> filesId) {
        this.username = username;
        this.filesId = filesId;
    }
}
