package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * UserReports payload response.
 * UserReportsResponse give client all reports saved in database by user as a set of {@link ReportResponse} objects.
 *
 * @author Arkadiusz Michalik
 * @see ReportResponse
 */
@Getter
@Setter
public class UserReportsResponse {

    /**
     * User login.
     */
    private String username;

    /**
     * Set of {@link ReportResponse} i.e. all reports saved in database by user.
     */
    private Set<ReportResponse> reports;

    /**
     * Creates response object with user login and set of {@link ReportResponse} objects.
     *
     * @param username User login.
     * @param reports  Set of {@link ReportResponse} objects.
     */
    public UserReportsResponse(String username, Set<ReportResponse> reports) {
        this.username = username;
        this.reports = reports;
    }
}
