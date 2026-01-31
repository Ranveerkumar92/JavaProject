package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Searchable;

/**
 * Represents a Doctor in the clinic.
 * Doctors have specialties and availability information.
 */
public class Doctor extends Person implements Searchable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private String specialty;
    private boolean available;
    private String licenseNumber;
    
    /**
     * Constructs a Doctor with the specified details.
     *
     * @param id the unique identifier
     * @param name the doctor's name
     * @param email the doctor's email
     * @param phoneNumber the doctor's phone number
     * @param specialty the doctor's specialty
     * @param licenseNumber the doctor's license number
     */
    public Doctor(String id, String name, String email, String phoneNumber, 
                  String specialty, String licenseNumber) {
        super(id, name, email, phoneNumber);
        this.specialty = specialty;
        this.available = true;
        this.licenseNumber = licenseNumber;
    }
    
    // Getters and Setters
    public String getSpecialty() {
        return specialty;
    }
    
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    @Override
    public boolean matchesId(String id) {
        return this.id.equalsIgnoreCase(id);
    }
    
    @Override
    public boolean matchesName(String name) {
        return this.name.equalsIgnoreCase(name);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", specialty='" + specialty + '\'' +
                ", available=" + available +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}
