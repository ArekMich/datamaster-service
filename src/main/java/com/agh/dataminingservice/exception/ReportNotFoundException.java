package com.agh.dataminingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Report not found exception which throw "Not Found" httpStatus response.
 * The server throw ReportNotFoundException when user get non existing report from database.
 *
 * @author Arkadiusz Michalik
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
