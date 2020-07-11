package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * UploadReports payload request.
 * Request contains information about Report to save in database.
 * Every field is required to properly process report.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UploadReportsRequest {

    /**
     * Report name.
     */
    @NotBlank
    private String reportName;

    /**
     * Content type of report.
     */
    @NotBlank
    private String contentType;

    /**
     * Image source of report.
     */
    @NotBlank
    private String imgSource;
}
