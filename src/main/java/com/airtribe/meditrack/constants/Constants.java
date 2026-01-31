package com.airtribe.meditrack.constants;

/**
 * Constants used across the MediTrack application.
 */
public class Constants {
    // Date and time formats
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    // Appointment statuses
    public static final String APPOINTMENT_SCHEDULED = "SCHEDULED";
    public static final String APPOINTMENT_COMPLETED = "COMPLETED";
    public static final String APPOINTMENT_CANCELLED = "CANCELLED";
    
    // Doctor specialties
    public static final String SPECIALTY_CARDIOLOGY = "CARDIOLOGY";
    public static final String SPECIALTY_NEUROLOGY = "NEUROLOGY";
    public static final String SPECIALTY_ORTHOPEDICS = "ORTHOPEDICS";
    public static final String SPECIALTY_DERMATOLOGY = "DERMATOLOGY";
    public static final String SPECIALTY_GENERAL = "GENERAL";
    
    // Appointment fee
    public static final double APPOINTMENT_FEE = 500.0;
    public static final double LAB_CHARGES = 200.0;
    
    // Error messages
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number";
    public static final String INVALID_DATE = "Invalid date format";
    public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
    public static final String DOCTOR_NOT_AVAILABLE = "Doctor is not available at this time";
    
    // CSV file paths
    public static final String DOCTORS_CSV = "data/doctors.csv";
    public static final String PATIENTS_CSV = "data/patients.csv";
    public static final String APPOINTMENTS_CSV = "data/appointments.csv";
    public static final String BILLS_CSV = "data/bills.csv";
}
