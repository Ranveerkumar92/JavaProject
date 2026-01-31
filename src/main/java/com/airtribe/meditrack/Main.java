package com.airtribe.meditrack;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.*;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Main entry point for the MediTrack Clinic & Appointment Management System.
 * Provides a menu-driven interface for users to interact with the system.
 */
public class Main {
    
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        // Initialize services
        doctorService = new DoctorService();
        patientService = new PatientService();
        appointmentService = new AppointmentService(doctorService, patientService);
        scanner = new Scanner(System.in);
        
        // Display welcome message
        displayWelcome();
        
        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    handleDoctorOperations();
                    break;
                case 2:
                    handlePatientOperations();
                    break;
                case 3:
                    handleAppointmentOperations();
                    break;
                case 4:
                    System.out.println("Thank you for using MediTrack. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
 
    }
    
    private static void displayWelcome() {
        System.out.println("================================================");
        System.out.println("  Welcome to MediTrack - Clinic Management System");
        System.out.println("================================================\n");
    }
    
    private static void displayMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static void handleDoctorOperations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Doctor Management ---");
            System.out.println("1. Register Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctor by ID");
            System.out.println("4. Update Doctor Availability");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    registerDoctor();
                    break;
                case 2:
                    viewAllDoctors();
                    break;
                case 3:
                    searchDoctorById();
                    break;
                case 4:
                    updateDoctorAvailability();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void handlePatientOperations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Patient Management ---");
            System.out.println("1. Register Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient by ID");
            System.out.println("4. Update Medical History");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    searchPatientById();
                    break;
                case 4:
                    updateMedicalHistory();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void handleAppointmentOperations() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Appointment Management ---");
            System.out.println("1. Book Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Patient Appointments");
            System.out.println("4. Complete Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    viewAllAppointments();
                    break;
                case 3:
                    viewPatientAppointments();
                    break;
                case 4:
                    completeAppointment();
                    break;
                case 5:
                    cancelAppointment();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void registerDoctor() {
        try {
            System.out.print("Enter doctor name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone number (10 digits): ");
            String phone = scanner.nextLine();
            System.out.print("Enter specialty: ");
            String specialty = scanner.nextLine();
            System.out.print("Enter license number: ");
            String license = scanner.nextLine();
            
            Doctor doctor = doctorService.registerDoctor(name, email, phone, specialty, license);
            System.out.println("\n✓ Doctor registered successfully!");
            System.out.println("Doctor ID: " + doctor.getId());
        } catch (InvalidDataException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void viewAllDoctors() {
        var doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found in the system.");
        } else {
            System.out.println("\n--- All Doctors ---");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
    }
    
    private static void searchDoctorById() {
        System.out.print("Enter doctor ID: ");
        String id = scanner.nextLine();
        Doctor doctor = doctorService.getDoctorById(id).orElse(null);
        if (doctor != null) {
            System.out.println("\n" + doctor);
        } else {
            System.out.println("Doctor not found.");
        }
    }
    
    private static void updateDoctorAvailability() {
        System.out.print("Enter doctor ID: ");
        String id = scanner.nextLine();
        Doctor doctor = doctorService.getDoctorById(id).orElse(null);
        if (doctor != null) {
            System.out.print("Set available (true/false): ");
            boolean available = Boolean.parseBoolean(scanner.nextLine());
            doctorService.setDoctorAvailability(id, available);
            System.out.println("✓ Doctor availability updated.");
        } else {
            System.out.println("Doctor not found.");
        }
    }
    
    private static void registerPatient() {
        try {
            System.out.print("Enter patient name: ");
            String name = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter phone number (10 digits): ");
            String phone = scanner.nextLine();
            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter medical history: ");
            String history = scanner.nextLine();
            
            Patient patient = patientService.registerPatient(name, email, phone, age, history);
            System.out.println("\n✓ Patient registered successfully!");
            System.out.println("Patient ID: " + patient.getId());
        } catch (InvalidDataException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void viewAllPatients() {
        var patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found in the system.");
        } else {
            System.out.println("\n--- All Patients ---");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }
    
    private static void searchPatientById() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        Patient patient = patientService.getPatientById(id).orElse(null);
        if (patient != null) {
            System.out.println("\n" + patient);
        } else {
            System.out.println("Patient not found.");
        }
    }
    
    private static void updateMedicalHistory() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new medical history: ");
        String history = scanner.nextLine();
        if (patientService.updateMedicalHistory(id, history)) {
            System.out.println("✓ Medical history updated.");
        } else {
            System.out.println("Patient not found.");
        }
    }
    
    private static void bookAppointment() {
        try {
            System.out.print("Enter doctor ID: ");
            String doctorId = scanner.nextLine();
            System.out.print("Enter patient ID: ");
            String patientId = scanner.nextLine();
            System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm:ss): ");
            String dateTimeStr = scanner.nextLine();
            System.out.print("Enter notes: ");
            String notes = scanner.nextLine();
            
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(dateTimeStr, formatter);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("✗ Error: Invalid date/time format. Please use: yyyy-MM-dd HH:mm:ss");
                return;
            }

            Appointment appointment = appointmentService.bookAppointment(doctorId, patientId, dateTime, notes);
            System.out.println("\n✓ Appointment booked successfully!");
            System.out.println("Appointment ID: " + appointment.getAppointmentId());
        } catch (InvalidDataException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void viewAllAppointments() {
        var appointments = appointmentService.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found in the system.");
        } else {
            System.out.println("\n--- All Appointments ---");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
    
    private static void viewPatientAppointments() {
        System.out.print("Enter patient ID: ");
        String patientId = scanner.nextLine();
        var appointments = appointmentService.getAppointmentsByPatient(patientId);
        if (appointments.isEmpty()) {
            System.out.println("No appointments found for this patient.");
        } else {
            System.out.println("\n--- Patient Appointments ---");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
    }
    
    private static void completeAppointment() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine();
            appointmentService.completeAppointment(appointmentId);
            System.out.println("✓ Appointment marked as completed.");
        } catch (AppointmentNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static void cancelAppointment() {
        try {
            System.out.print("Enter appointment ID: ");
            String appointmentId = scanner.nextLine();
            appointmentService.cancelAppointment(appointmentId);
            System.out.println("✓ Appointment cancelled successfully.");
        } catch (AppointmentNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }
    
    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
