package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    private String id;
    private String fileName;
    private String fileType;

    public FileDto(String id, String fileName, String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
    }
}
