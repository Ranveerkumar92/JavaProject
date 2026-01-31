package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an Appointment between a Doctor and Patient.
 */
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String appointmentId;
    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentDateTime;
    private String status;
    private String notes;
    
    /**
     * Constructs an Appointment with the specified details.
     *
     * @param appointmentId the unique appointment identifier
     * @param doctorId the doctor's ID
     * @param patientId the patient's ID
     * @param appointmentDateTime the appointment date and time
     * @param status the appointment status
     * @param notes any notes about the appointment
     */
    public Appointment(String appointmentId, String doctorId, String patientId,
                       LocalDateTime appointmentDateTime, String status, String notes) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = status;
        this.notes = notes;
    }
    
    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }
    
    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public String getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
    
    public String getPatientId() {
        return patientId;
    }
    
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
    
    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }
    
    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", appointmentDateTime=" + appointmentDateTime +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
