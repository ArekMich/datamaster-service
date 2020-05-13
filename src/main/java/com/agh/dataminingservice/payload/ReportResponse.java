package com.agh.dataminingservice.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportResponse {
    private String id;
    private String reportName;
    private String contentType;
    private String imgSource;
}
