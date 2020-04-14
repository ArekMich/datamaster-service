package com.agh.dataminingservice.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileDto {
    private String id;
    private String fileName;
    private String fileType;
}
