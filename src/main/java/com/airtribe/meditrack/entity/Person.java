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
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
