package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * UploadReports payload response.
 * Response object passes information about saved report in database to the client.
 * The most important information which UploadReportsResponse provide are report name, type and Uri to download report.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UploadReportsResponse {

    /**
     * Report name.
     */
    private String reportName;

    /**
     * Uri under which we can download the report.
     */
    private String reportDownloadUri;

    /**
     * Report type.
     */
    private String contentType;

    /**
     * Creates response object with information about saved report to database.
     *
     * @param reportName        Report name.
     * @param reportDownloadUri Uri under which we can download the report.
     * @param contentType       Report type.
     */
    public UploadReportsResponse(String reportName, String reportDownloadUri, String contentType) {
        this.reportName = reportName;
        this.reportDownloadUri = reportDownloadUri;
        this.contentType = contentType;
    }
}
