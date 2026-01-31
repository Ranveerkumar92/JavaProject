package com.airtribe.meditrack.entity;

import java.io.Serializable;

/**
 * Abstract base class representing a Person in the system.
 * This class provides common attributes for both Doctor and Patient.
 */
public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    
    /**
     * Constructs a Person with the specified details.
     *
     * @param id the unique identifier
     * @param name the person's name
     * @param email the person's email
     * @param phoneNumber the person's phone number
     */
    public Person(String id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id cannot be null or empty");
        }
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be null or empty");
        }
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("email cannot be null or empty");
        }
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("phoneNumber cannot be null or empty");
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * Null-safe check whether this person's id matches the given id.
     */
    public boolean matchesId(String id) {
        return this.id != null && id != null && this.id.equals(id);
    }

    /**
     * Null-safe case-insensitive name comparison.
     */
    public boolean matchesName(String name) {
        return this.name != null && name != null && this.name.equalsIgnoreCase(name);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Person{");
        sb.append("id=\"").append(id).append('"');
        sb.append(", name=\"").append(name).append('"');
        sb.append(", email=\"").append(email).append('"');
        sb.append(", phoneNumber=\"").append(phoneNumber).append('"');
        sb.append('}');
        return sb.toString();
    }
}
