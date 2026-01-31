package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Searchable;

/**
 * Represents a Doctor in the clinic.
 * Doctors have specialties and availability information.
 */
public class Doctor extends Person implements Searchable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    private String specialty;
    private DoctorAvailability availability;
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
        this(id, name, email, phoneNumber, specialty, licenseNumber, DoctorAvailability.AVAILABLE);
    }

    /**
     * Constructs a Doctor with explicit availability.
     *
     * @param id the unique identifier
     * @param name the doctor's name
     * @param email the doctor's email
     * @param phoneNumber the doctor's phone number
     * @param specialty the doctor's specialty
     * @param licenseNumber the doctor's license number
     * @param availability initial availability state
     */
    public Doctor(String id, String name, String email, String phoneNumber,
                  String specialty, String licenseNumber, DoctorAvailability availability) {
        super(id, name, email, phoneNumber);
        this.specialty = specialty;
        this.availability = availability == null ? DoctorAvailability.NOT_AVAILABLE : availability;
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
        return this.availability == DoctorAvailability.AVAILABLE;
    }

    public DoctorAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(DoctorAvailability availability) {
        this.availability = availability;
    }

    // Backwards-compatible setter for boolean flag
    public void setAvailable(boolean available) {
        this.availability = available ? DoctorAvailability.AVAILABLE : DoctorAvailability.NOT_AVAILABLE;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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
        sb.append("Doctor{");
        sb.append("id=\"").append(id).append('"');
        sb.append(", name=\"").append(name).append('"');
        sb.append(", email=\"").append(email).append('"');
        sb.append(", phoneNumber=\"").append(phoneNumber).append('"');
        sb.append(", specialty=\"").append(specialty).append('"');
        sb.append(", availability=\"").append(availability != null ? availability.name() : "null").append('"');
        sb.append(", licenseNumber=\"").append(licenseNumber).append('"');
        sb.append('}');
        return sb.toString();
    }
}
