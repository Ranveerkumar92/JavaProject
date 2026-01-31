package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing doctors.
 * Handles CRUD operations and doctor-related business logic.
 */
public class DoctorService {
    
    private DataStore<Doctor> doctorStore;
    
    /**
     * Constructs a DoctorService with an empty data store.
     */
    public DoctorService() {
        this.doctorStore = new DataStore<>();
    }
    
    /**
     * Registers a new doctor in the system.
     *
     * @param name the doctor's name
     * @param email the doctor's email
     * @param phoneNumber the doctor's phone number
     * @param specialty the doctor's specialty
     * @param licenseNumber the doctor's license number
     * @return the registered doctor
     * @throws InvalidDataException if any field is invalid
     */
    public Doctor registerDoctor(String name, String email, String phoneNumber, 
                                 String specialty, String licenseNumber) throws InvalidDataException {
        Validator.validateDoctor(name, email, phoneNumber);
        
        String doctorId = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phoneNumber, specialty, licenseNumber);
        doctorStore.add(doctor);
        
        return doctor;
    }
    
    /**
     * Retrieves a doctor by ID.
     *
     * @param doctorId the doctor's ID
     * @return the doctor if found, null otherwise
     */
    public Doctor getDoctorById(String doctorId) {
        return doctorStore.getAll().stream()
                .filter(d -> d.matchesId(doctorId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves a doctor by name.
     *
     * @param name the doctor's name
     * @return the doctor if found, null otherwise
     */
    public Doctor getDoctorByName(String name) {
        return doctorStore.getAll().stream()
                .filter(d -> d.matchesName(name))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves all doctors with a specific specialty.
     *
     * @param specialty the specialty
     * @return a list of doctors with that specialty
     */
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorStore.getAll().stream()
                .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves all available doctors.
     *
     * @return a list of available doctors
     */
    public List<Doctor> getAvailableDoctors() {
        return doctorStore.getAll().stream()
                .filter(Doctor::isAvailable)
                .collect(Collectors.toList());
    }
    
    /**
     * Updates a doctor's information.
     *
     * @param doctorId the doctor's ID
     * @param email the new email
     * @param phoneNumber the new phone number
     * @throws InvalidDataException if any field is invalid
     */
    public void updateDoctor(String doctorId, String email, String phoneNumber) throws InvalidDataException {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor != null) {
            Validator.validateDoctor(doctor.getName(), email, phoneNumber);
            doctor.setEmail(email);
            doctor.setPhoneNumber(phoneNumber);
        }
    }
    
    /**
     * Sets a doctor's availability status.
     *
     * @param doctorId the doctor's ID
     * @param available the availability status
     */
    public void setDoctorAvailability(String doctorId, boolean available) {
        Doctor doctor = getDoctorById(doctorId);
        if (doctor != null) {
            doctor.setAvailable(available);
        }
    }
    
    /**
     * Removes a doctor from the system.
     *
     * @param doctorId the doctor's ID
     * @return true if removed, false otherwise
     */
    public boolean removeDoctor(String doctorId) {
        Doctor doctor = getDoctorById(doctorId);
        return doctor != null && doctorStore.remove(doctor);
    }
    
    /**
     * Gets all doctors in the system.
     *
     * @return a list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
}
