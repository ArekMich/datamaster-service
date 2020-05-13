package com.agh.dataminingservice.exception;

public class ReportsStorageException extends RuntimeException{

    public ReportsStorageException(String message) {
        super(message);
    }

    public ReportsStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
