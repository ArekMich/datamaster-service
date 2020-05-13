package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadReportsResponse {
    private String reportName;
    private String reportDownloadUri;
    private String contentType;

    public UploadReportsResponse(String reportName, String reportDownloadUri, String contentType) {
        this.reportName = reportName;
        this.reportDownloadUri = reportDownloadUri;
        this.contentType = contentType;
    }
}
