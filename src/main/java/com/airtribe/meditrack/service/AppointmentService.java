package com.airtribe.meditrack.service;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing appointments.
 * Handles CRUD operations and appointment-related business logic.
 */
public class AppointmentService {
    
    private DataStore<Appointment> appointmentStore;
    private DoctorService doctorService;
    private PatientService patientService;
    
    /**
     * Constructs an AppointmentService with empty data store.
     *
     * @param doctorService the doctor service instance
     * @param patientService the patient service instance
     */
    public AppointmentService(DoctorService doctorService, PatientService patientService) {
        this.appointmentStore = new DataStore<>();
        this.doctorService = doctorService;
        this.patientService = patientService;
    }
    
    /**
     * Books a new appointment.
     *
     * @param doctorId the doctor's ID
     * @param patientId the patient's ID
     * @param appointmentDateTime the appointment date and time
     * @param notes any notes about the appointment
     * @return the booked appointment
     * @throws InvalidDataException if doctor or patient not found, or appointment time is invalid
     */
    public Appointment bookAppointment(String doctorId, String patientId, 
                                       LocalDateTime appointmentDateTime, String notes) throws InvalidDataException {
        if (doctorService.getDoctorById(doctorId) == null) {
            throw new InvalidDataException("Doctor not found");
        }
        if (patientService.getPatientById(patientId) == null) {
            throw new InvalidDataException("Patient not found");
        }
        if (!doctorService.getDoctorById(doctorId).isAvailable()) {
            throw new InvalidDataException(Constants.DOCTOR_NOT_AVAILABLE);
        }
        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("Appointment cannot be booked in the past");
        }
        
        String appointmentId = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId, 
                                                 appointmentDateTime, Constants.APPOINTMENT_SCHEDULED, notes);
        appointmentStore.add(appointment);
        
        return appointment;
    }
    
    /**
     * Retrieves an appointment by ID.
     *
     * @param appointmentId the appointment's ID
     * @return the appointment if found, null otherwise
     */
    public Appointment getAppointmentById(String appointmentId) {
        return appointmentStore.getAll().stream()
                .filter(a -> a.getAppointmentId().equalsIgnoreCase(appointmentId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves all appointments for a patient.
     *
     * @param patientId the patient's ID
     * @return a list of patient's appointments
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.getAll().stream()
                .filter(a -> a.getPatientId().equalsIgnoreCase(patientId))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves all appointments for a doctor.
     *
     * @param doctorId the doctor's ID
     * @return a list of doctor's appointments
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.getAll().stream()
                .filter(a -> a.getDoctorId().equalsIgnoreCase(doctorId))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves all appointments with a specific status.
     *
     * @param status the appointment status
     * @return a list of appointments with that status
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentStore.getAll().stream()
                .filter(a -> a.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
    
    /**
     * Cancels an appointment.
     *
     * @param appointmentId the appointment's ID
     * @throws AppointmentNotFoundException if appointment not found
     */
    public void cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(Constants.APPOINTMENT_NOT_FOUND);
        }
        appointment.setStatus(Constants.APPOINTMENT_CANCELLED);
    }
    
    /**
     * Completes an appointment.
     *
     * @param appointmentId the appointment's ID
     * @throws AppointmentNotFoundException if appointment not found
     */
    public void completeAppointment(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(Constants.APPOINTMENT_NOT_FOUND);
        }
        appointment.setStatus(Constants.APPOINTMENT_COMPLETED);
    }
    
    /**
     * Reschedules an appointment.
     *
     * @param appointmentId the appointment's ID
     * @param newDateTime the new date and time
     * @throws AppointmentNotFoundException if appointment not found
     * @throws InvalidDataException if new date/time is invalid
     */
    public void rescheduleAppointment(String appointmentId, LocalDateTime newDateTime) 
            throws AppointmentNotFoundException, InvalidDataException {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new AppointmentNotFoundException(Constants.APPOINTMENT_NOT_FOUND);
        }
        if (newDateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidDataException("New appointment time cannot be in the past");
        }
        appointment.setAppointmentDateTime(newDateTime);
    }
    
    /**
     * Gets all appointments in the system.
     *
     * @return a list of all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }
}
