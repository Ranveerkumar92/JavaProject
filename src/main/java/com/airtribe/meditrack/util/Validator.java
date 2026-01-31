package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

/**
 * Utility class for validating input data.
 */
public class Validator {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    
    /**
     * Validates email format.
     *
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }
    
    /**
     * Validates phone number format.
     *
     * @param phone the phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches(PHONE_REGEX);
    }
    
    /**
     * Validates if a string is not empty or null.
     *
     * @param value the string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validates if an age is reasonable.
     *
     * @param age the age to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidAge(int age) {
        return age > 0 && age <= 150;
    }
    
    /**
     * Validates if a value is positive.
     *
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }
    
    /**
     * Validates doctor details and throws exception if invalid.
     *
     * @param name the doctor's name
     * @param email the doctor's email
     * @param phone the doctor's phone number
     * @throws InvalidDataException if any field is invalid
     */
    public static void validateDoctor(String name, String email, String phone) throws InvalidDataException {
        if (!isNotEmpty(name)) {
            throw new InvalidDataException("Doctor name cannot be empty");
        }
        if (!isValidEmail(email)) {
            throw new InvalidDataException("Invalid email format");
        }
        if (!isValidPhoneNumber(phone)) {
            throw new InvalidDataException("Phone number must be 10 digits");
        }
    }
    
    /**
     * Validates patient details and throws exception if invalid.
     *
     * @param name the patient's name
     * @param email the patient's email
     * @param phone the patient's phone number
     * @param age the patient's age
     * @throws InvalidDataException if any field is invalid
     */
    public static void validatePatient(String name, String email, String phone, int age) throws InvalidDataException {
        if (!isNotEmpty(name)) {
            throw new InvalidDataException("Patient name cannot be empty");
        }
        if (!isValidEmail(email)) {
            throw new InvalidDataException("Invalid email format");
        }
        if (!isValidPhoneNumber(phone)) {
            throw new InvalidDataException("Phone number must be 10 digits");
        }
        if (!isValidAge(age)) {
            throw new InvalidDataException("Age must be between 1 and 150");
        }
    }
}
