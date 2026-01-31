package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
        this.patientStore = new DataStore<Patient>();
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
     * @return an Optional containing the patient if found, empty otherwise
     */
    public Optional<Patient> getPatientById(String patientId) {
        return patientStore.getAll().stream()
                .filter(p -> p.matchesId(patientId))
                .findFirst();
    }
    
    /**
     * Retrieves a patient by name.
     *
     * @param name the patient's name
     * @return an Optional containing the patient if found, empty otherwise
     */
    public Optional<Patient> getPatientByName(String name) {
        return patientStore.getAll().stream()
                .filter(p -> p.matchesName(name))
                .findFirst();
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
                .collect(toList());
    }
    
    /**
     * Updates a patient's information.
     *
     * @param patientId the patient's ID
     * @param email the new email
     * @param phoneNumber the new phone number
     * @return true if update was successful, false if patient not found
     * @throws InvalidDataException if any field is invalid
     */
    public boolean updatePatient(String patientId, String email, String phoneNumber) throws InvalidDataException {
        var optionalPatient = getPatientById(patientId);
        if (optionalPatient.isEmpty()) {
            return false;
        }
        Patient patient = optionalPatient.get();
        Validator.validatePatient(patient.getName(), email, phoneNumber, patient.getAge());
        patient.setEmail(email);
        patient.setPhoneNumber(phoneNumber);
        return true;
    }
    
    /**
     * Updates a patient's medical history.
     *
     * @param patientId the patient's ID
     * @param medicalHistory the new medical history
     * @return true if update was successful, false if patient not found
     */
    public boolean updateMedicalHistory(String patientId, String medicalHistory) {
        return getPatientById(patientId).map(patient -> {
            patient.setMedicalHistory(medicalHistory);
            return true;
        }).orElse(false);
    }
    
    /**
     * Removes a patient from the system.
     *
     * @param patientId the patient's ID
     * @return true if removed, false otherwise
     */
    public boolean removePatient(String patientId) {
        return getPatientById(patientId).map(patientStore::remove).orElse(false);
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
