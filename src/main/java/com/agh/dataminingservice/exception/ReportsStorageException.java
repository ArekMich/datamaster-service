package com.agh.dataminingservice.exception;

/**
 * The server throw ReportsStorageException when problem occurs while storing report in database.
 *
 * @author Arkadiusz Michalik
 * @see RuntimeException
 */
public class ReportsStorageException extends RuntimeException {

    public ReportsStorageException(String message) {
        super(message);
    }

    public ReportsStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
