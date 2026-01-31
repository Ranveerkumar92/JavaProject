package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique IDs for entities.
 */
public class IdGenerator {
    private static final AtomicLong doctorIdCounter = new AtomicLong(1000);
    private static final AtomicLong patientIdCounter = new AtomicLong(2000);
    private static final AtomicLong appointmentIdCounter = new AtomicLong(3000);
    private static final AtomicLong billIdCounter = new AtomicLong(4000);
    
    /**
     * Generates a unique doctor ID.
     *
     * @return a unique doctor ID
     */
    public static String generateDoctorId() {
        return "DOC" + doctorIdCounter.getAndIncrement();
    }
    
    /**
     * Generates a unique patient ID.
     *
     * @return a unique patient ID
     */
    public static String generatePatientId() {
        return "PAT" + patientIdCounter.getAndIncrement();
    }
    
    /**
     * Generates a unique appointment ID.
     *
     * @return a unique appointment ID
     */
    public static String generateAppointmentId() {
        return "APT" + appointmentIdCounter.getAndIncrement();
    }
    
    /**
     * Generates a unique bill ID.
     *
     * @return a unique bill ID
     */
    public static String generateBillId() {
        return "BILL" + billIdCounter.getAndIncrement();
    }
}
