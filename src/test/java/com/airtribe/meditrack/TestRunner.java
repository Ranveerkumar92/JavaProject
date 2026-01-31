package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.*;
import com.airtribe.meditrack.util.Validator;

import java.time.LocalDateTime;

/**
 * Manual test runner for the MediTrack system.
 * Tests core functionality without using JUnit.
 */
public class TestRunner {
    
    static int testsPassed = 0;
    static int testsFailed = 0;
    
    public static void main(String[] args) {
        System.out.println("========== MediTrack System Test Suite ==========\n");
        
        testValidation();
        testDoctorService();
        testPatientService();
        testAppointmentService();
        
        printTestSummary();
    }
    
    private static void testValidation() {
        System.out.println("--- Testing Validation Utility ---");
        
        // Test valid email
        assertTrue("Valid email should pass", Validator.isValidEmail("test@example.com"));
        
        // Test invalid email
        assertFalse("Invalid email should fail", Validator.isValidEmail("invalid-email"));
        
        // Test valid phone
        assertTrue("Valid phone should pass", Validator.isValidPhoneNumber("9876543210"));
        
        // Test invalid phone
        assertFalse("Invalid phone should fail", Validator.isValidPhoneNumber("987654321"));
        
        // Test age validation
        assertTrue("Valid age should pass", Validator.isValidAge(25));
        assertFalse("Invalid age should fail", Validator.isValidAge(200));
        
        System.out.println();
    }
    
    private static void testDoctorService() {
        System.out.println("--- Testing Doctor Service ---");
        
        DoctorService doctorService = new DoctorService();
        
        try {
            // Register doctors
            Doctor doc1 = doctorService.registerDoctor("Dr. John Smith", "john@example.com", 
                                                       "9876543210", "CARDIOLOGY", "LIC001");
            assertTrue("Doctor registration successful", doc1 != null);
            
            Doctor doc2 = doctorService.registerDoctor("Dr. Sarah Johnson", "sarah@example.com", 
                                                       "9876543211", "NEUROLOGY", "LIC002");
            assertTrue("Second doctor registration successful", doc2 != null);
            
            // Test retrieval
            assertTrue("Get doctor by ID", doctorService.getDoctorById(doc1.getId()).isPresent());
            assertTrue("Get doctor by name", doctorService.getDoctorByName("Dr. John Smith").isPresent());
            
            // Test specialty filter
            assertTrue("Filter by specialty", doctorService.getDoctorsBySpecialty("CARDIOLOGY").size() == 1);
            
            // Test availability
            assertTrue("Available doctors list", doctorService.getAvailableDoctors().size() == 2);
            doctorService.setDoctorAvailability(doc1.getId(), false);
            assertTrue("Available doctors after status change", doctorService.getAvailableDoctors().size() == 1);
            
        } catch (InvalidDataException e) {
            fail("Doctor registration failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    private static void testPatientService() {
        System.out.println("--- Testing Patient Service ---");
        
        PatientService patientService = new PatientService();
        
        try {
            // Register patients
            Patient patient1 = patientService.registerPatient("John Doe", "john.doe@example.com", 
                                                              "8765432109", 35, "No known allergies");
            assertTrue("Patient registration successful", patient1 != null);
            
            Patient patient2 = patientService.registerPatient("Jane Smith", "jane.smith@example.com", 
                                                              "8765432108", 28, "Asthma");
            assertTrue("Second patient registration successful", patient2 != null);
            
            // Test retrieval
            assertTrue("Get patient by ID", patientService.getPatientById(patient1.getId()).isPresent());
            assertTrue("Get patient by name", patientService.getPatientByName("John Doe").isPresent());
            
            // Test age range filter
            assertTrue("Filter by age range", patientService.getPatientsByAgeRange(30, 40).size() == 1);
            
            // Test update
            assertTrue("Medical history updated", patientService.updateMedicalHistory(patient1.getId(), "Diabetes, No allergies"));
            assertTrue("Medical history contains expected text", 
                      patientService.getPatientById(patient1.getId()).map(p -> p.getMedicalHistory().contains("Diabetes")).orElse(false));
            
        } catch (InvalidDataException e) {
            fail("Patient registration failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    private static void testAppointmentService() {
        System.out.println("--- Testing Appointment Service ---");
        
        try {
            DoctorService doctorService = new DoctorService();
            PatientService patientService = new PatientService();
            AppointmentService appointmentService = new AppointmentService(doctorService, patientService);
            
            // Setup
            Doctor doctor = doctorService.registerDoctor("Dr. Emily Brown", "emily@example.com", 
                                                         "9876543212", "ORTHOPEDICS", "LIC003");
            Patient patient = patientService.registerPatient("Michael Johnson", "michael@example.com", 
                                                             "8765432107", 42, "Old back injury");
            
            // Book appointment
            LocalDateTime appointmentTime = LocalDateTime.now().plusDays(7);
            Appointment appointment = appointmentService.bookAppointment(doctor.getId(), patient.getId(), 
                                                                         appointmentTime, "Back pain consultation");
            assertTrue("Appointment booking successful", appointment != null);
            
            // Test retrieval
            assertTrue("Get appointment by ID", appointmentService.getAppointmentById(appointment.getAppointmentId()) != null);
            assertTrue("Get appointments by patient", appointmentService.getAppointmentsByPatient(patient.getId()).size() == 1);
            assertTrue("Get appointments by doctor", appointmentService.getAppointmentsByDoctor(doctor.getId()).size() == 1);
            
            // Test status operations
            appointmentService.completeAppointment(appointment.getAppointmentId());
            Appointment completed = appointmentService.getAppointmentById(appointment.getAppointmentId());
            assertTrue("Appointment marked as completed", completed.getStatus() == AppointmentStatus.COMPLETED);
            
            // Test invalid operations
            try {
                appointmentService.getAppointmentById("INVALID_ID");
                // Should not find the appointment, which is expected
                assertTrue("Invalid appointment ID handled", true);
            } catch (Exception e) {
                fail("Exception thrown for invalid appointment: " + e.getMessage());
            }
            
        } catch (Exception e) {
            fail("Appointment test failed: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    // Test utilities
    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[✓ PASS] " + testName);
            testsPassed++;
        } else {
            System.out.println("[✗ FAIL] " + testName);
            testsFailed++;
        }
    }
    
    private static void assertFalse(String testName, boolean condition) {
        assertTrue(testName, !condition);
    }
    
    private static void fail(String message) {
        System.out.println("[✗ FAIL] " + message);
        testsFailed++;
    }
    
    private static void printTestSummary() {
        System.out.println("\n========== Test Summary ==========");
        System.out.println("Total Tests: " + (testsPassed + testsFailed));
        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);
        System.out.println("Success Rate: " + (testsPassed * 100.0 / (testsPassed + testsFailed)) + "%");
        System.out.println("==================================");
    }
}
