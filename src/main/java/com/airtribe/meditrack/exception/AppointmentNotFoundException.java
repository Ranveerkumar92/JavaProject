package com.airtribe.meditrack.exception;

/**
 * Exception thrown when an appointment is not found in the system.
 */
public class AppointmentNotFoundException extends Exception {
    
    /**
     * Constructs an AppointmentNotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructs an AppointmentNotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause (which is saved for later retrieval)
     */
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
