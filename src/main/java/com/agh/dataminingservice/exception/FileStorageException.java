package com.agh.dataminingservice.exception;

/**
 * The server throw FileStorageException when problem occurs while storing file in database.
 *
 * @author Arkadiusz Michalik
 * @see RuntimeException
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
