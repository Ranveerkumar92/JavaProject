package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Searchable;

/**
 * Represents a Patient in the clinic.
 * Patients have medical history and appointment records.
 */
public class Patient extends Person implements Searchable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private String medicalHistory;
    private int age;
    
    /**
     * Constructs a Patient with the specified details.
     *
     * @param id the unique identifier
     * @param name the patient's name
     * @param email the patient's email
     * @param phoneNumber the patient's phone number
     * @param age the patient's age
     * @param medicalHistory the patient's medical history
     */
    public Patient(String id, String name, String email, String phoneNumber, 
                   int age, String medicalHistory) {
        super(id, name, email, phoneNumber);
        this.age = age;
        this.medicalHistory = medicalHistory;
    }
    
    // Getters and Setters
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public boolean matchesId(String id) {
        if (id == null || this.id == null) return false;
        return this.id.equalsIgnoreCase(id);
    }
    
    @Override
    public boolean matchesName(String name) {
        if (name == null || this.name == null) return false;
        return this.name.equalsIgnoreCase(name);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Patient{");
        sb.append("id=\"").append(id).append('"');
        sb.append(", name=\"").append(name).append('"');
        sb.append(", email=\"").append(email).append('"');
        sb.append(", phoneNumber=\"").append(phoneNumber).append('"');
        sb.append(", age=").append(age);
        sb.append(", medicalHistory=\"").append(medicalHistory).append('"');
        sb.append('}');
        return sb.toString();
    }
}
