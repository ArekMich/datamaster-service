package com.agh.dataminingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Bad request exception which throw "Bad Request" httpStatus response.
 * The server cannot or will not process the request due to something that is perceived to be a client error
 *
 * @author Arkadiusz Michalik
 * @see RuntimeException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
