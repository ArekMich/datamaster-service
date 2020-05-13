package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserReportsResponse {

    private String username;
    private Set<ReportResponse> reports;

    public UserReportsResponse(String username, Set<ReportResponse> reports) {
        this.username = username;
        this.reports = reports;
    }
}
