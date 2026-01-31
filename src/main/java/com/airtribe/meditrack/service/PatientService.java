package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing patients.
 * Handles CRUD operations and patient-related business logic.
 */
public class PatientService {
    
    private DataStore<Patient> patientStore;
    
    /**
     * Constructs a PatientService with an empty data store.
     */
    public PatientService() {
        this.patientStore = new DataStore<>();
    }
    
    /**
     * Registers a new patient in the system.
     *
     * @param name the patient's name
     * @param email the patient's email
     * @param phoneNumber the patient's phone number
     * @param age the patient's age
     * @param medicalHistory the patient's medical history
     * @return the registered patient
     * @throws InvalidDataException if any field is invalid
     */
    public Patient registerPatient(String name, String email, String phoneNumber, 
                                   int age, String medicalHistory) throws InvalidDataException {
        Validator.validatePatient(name, email, phoneNumber, age);
        
        String patientId = IdGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, email, phoneNumber, age, medicalHistory);
        patientStore.add(patient);
        
        return patient;
    }
    
    /**
     * Retrieves a patient by ID.
     *
     * @param patientId the patient's ID
     * @return the patient if found, null otherwise
     */
    public Patient getPatientById(String patientId) {
        return patientStore.getAll().stream()
                .filter(p -> p.matchesId(patientId))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves a patient by name.
     *
     * @param name the patient's name
     * @return the patient if found, null otherwise
     */
    public Patient getPatientByName(String name) {
        return patientStore.getAll().stream()
                .filter(p -> p.matchesName(name))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Retrieves all patients within a specific age range.
     *
     * @param minAge the minimum age
     * @param maxAge the maximum age
     * @return a list of patients within the age range
     */
    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge) {
        return patientStore.getAll().stream()
                .filter(p -> p.getAge() >= minAge && p.getAge() <= maxAge)
                .collect(Collectors.toList());
    }
    
    /**
     * Updates a patient's information.
     *
     * @param patientId the patient's ID
     * @param email the new email
     * @param phoneNumber the new phone number
     * @throws InvalidDataException if any field is invalid
     */
    public void updatePatient(String patientId, String email, String phoneNumber) throws InvalidDataException {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            Validator.validatePatient(patient.getName(), email, phoneNumber, patient.getAge());
            patient.setEmail(email);
            patient.setPhoneNumber(phoneNumber);
        }
    }
    
    /**
     * Updates a patient's medical history.
     *
     * @param patientId the patient's ID
     * @param medicalHistory the new medical history
     */
    public void updateMedicalHistory(String patientId, String medicalHistory) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.setMedicalHistory(medicalHistory);
        }
    }
    
    /**
     * Removes a patient from the system.
     *
     * @param patientId the patient's ID
     * @return true if removed, false otherwise
     */
    public boolean removePatient(String patientId) {
        Patient patient = getPatientById(patientId);
        return patient != null && patientStore.remove(patient);
    }
    
    /**
     * Gets all patients in the system.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
}
