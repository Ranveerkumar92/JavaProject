package com.airtribe.meditrack.exception;

/**
 * Exception thrown when invalid data is encountered in the system.
 */
public class InvalidDataException extends Exception {
    
    /**
     * Constructs an InvalidDataException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidDataException(String message) {
        super(message);
    }
    
    /**
     * Constructs an InvalidDataException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause (which is saved for later retrieval)
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
