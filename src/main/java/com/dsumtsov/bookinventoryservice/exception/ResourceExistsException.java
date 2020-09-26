package com.dsumtsov.bookinventoryservice.exception;

public class ResourceExistsException extends RuntimeException {
    public ResourceExistsException(String message) {
        super(message);
    }
}
