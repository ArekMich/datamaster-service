package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * File DTO with id, name and content type.
 * Class is used to exchange information about user repository.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class FileDto {

    private String id;
    private String fileName;
    private String fileType;

    /**
     * Creates file dto to exchange information about user repository with client.
     *
     * @param id       File id.
     * @param fileName file name.
     * @param fileType Content type of file.
     */
    public FileDto(String id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
