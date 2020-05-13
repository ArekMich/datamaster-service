package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UploadReportsRequest {

    @NotBlank
    private String reportName;
    @NotBlank
    private String contentType;
    @NotBlank
    private String imgSource;
}
