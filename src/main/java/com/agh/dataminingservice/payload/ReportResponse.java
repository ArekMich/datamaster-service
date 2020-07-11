package com.agh.dataminingservice.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Report payload response.
 * ReportResponse give client all information about saved and then selected report in database.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
@Builder
public class ReportResponse {

    /**
     * Report id.
     */
    private String id;

    /**
     * Report name.
     */
    private String reportName;

    /**
     * Content type of report.
     * Mainly "image/png".
     */
    private String contentType;

    /**
     * Image source of report encode by base64.
     */
    private String imgSource;
}
