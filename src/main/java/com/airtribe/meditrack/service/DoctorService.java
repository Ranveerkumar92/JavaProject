package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        this.doctorStore = new DataStore<Doctor>();
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
        return registerDoctor(name, email, phoneNumber, specialty, licenseNumber, com.airtribe.meditrack.entity.DoctorAvailability.AVAILABLE);
    }

    /**
     * Register a doctor specifying initial availability.
     */
    public Doctor registerDoctor(String name, String email, String phoneNumber,
                                 String specialty, String licenseNumber, com.airtribe.meditrack.entity.DoctorAvailability availability) throws InvalidDataException {
        Validator.validateDoctor(name, email, phoneNumber);

        String doctorId = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phoneNumber, specialty, licenseNumber, availability);
        doctorStore.add(doctor);

        return doctor;
    }
    
    /**
     * Retrieves a doctor by ID.
     *
     * @param doctorId the doctor's ID
     * @return an Optional containing the doctor if found, empty otherwise
     */
    public Optional<Doctor> getDoctorById(String doctorId) {
        return doctorStore.getAll().stream()
                .filter(d -> d.matchesId(doctorId))
                .findFirst();
    }
    
    /**
     * Retrieves a doctor by name.
     *
     * @param name the doctor's name
     * @return an Optional containing the doctor if found, empty otherwise
     */
    public Optional<Doctor> getDoctorByName(String name) {
        return doctorStore.getAll().stream()
                .filter(d -> d.matchesName(name))
                .findFirst();
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
                .collect(toList());
    }
    
    /**
     * Retrieves all available doctors.
     *
     * @return a list of available doctors
     */
    public List<Doctor> getAvailableDoctors() {
        return doctorStore.getAll().stream()
                .filter(Doctor::isAvailable)
                .collect(toList());
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
        getDoctorById(doctorId).ifPresent(doctor -> {
            try {
                Validator.validateDoctor(doctor.getName(), email, phoneNumber);
                doctor.setEmail(email);
                doctor.setPhoneNumber(phoneNumber);
            } catch (InvalidDataException e) {
                throw new RuntimeException(e);
            }
        });
    }
    
    /**
     * Sets a doctor's availability status.
     *
     * @param doctorId the doctor's ID
     * @param available the availability status
     */
    public void setDoctorAvailability(String doctorId, boolean available) {
        getDoctorById(doctorId).ifPresent(doctor -> doctor.setAvailable(available));
    }

    /**
     * Sets a doctor's availability using the enum.
     *
     * @param doctorId the doctor's ID
     * @param availability the availability state
     */
    public void setDoctorAvailability(String doctorId, com.airtribe.meditrack.entity.DoctorAvailability availability) {
        getDoctorById(doctorId).ifPresent(doctor -> doctor.setAvailability(availability));
    }
    
    /**
     * Removes a doctor from the system.
     *
     * @param doctorId the doctor's ID
     * @return true if removed, false otherwise
     */
    public boolean removeDoctor(String doctorId) {
        return getDoctorById(doctorId).map(doctorStore::remove).orElse(false);
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
