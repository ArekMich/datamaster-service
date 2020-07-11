package com.agh.dataminingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * File not found exception which throw "Not Found" httpStatus response.
 * The server throw MyFileNotFoundException when user get non existing file from repository.
 *
 * @author Arkadiusz Michalik
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
