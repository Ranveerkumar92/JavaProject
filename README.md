# MediTrack - Clinic & Appointment Management System

## Overview

**MediTrack** is a comprehensive, modular Clinic & Appointment Management System built with **Core Java**. It demonstrates strong object-oriented design principles, SOLID principles, and best practices in Java development. The system manages patients, doctors, appointments, and billing with a clean, professional interface.

## ✨ Features

✅ **Doctor Management**
- Register and manage doctors
- Track specialty and license information
- Manage doctor availability status
- Search doctors by ID, name, or specialty

✅ **Patient Management**
- Register and manage patients
- Maintain medical history
- Track patient demographics
- Search patients by ID or name

✅ **Appointment Management**
- Book appointments between doctors and patients
- Track appointment status (SCHEDULED, COMPLETED, CANCELLED)
- View patient/doctor appointment history
- Reschedule or cancel appointments

✅ **Billing System**
- Generate bills for appointments
- Track consultation fees and lab charges
- Mark payments as completed
- Generate immutable bill summaries

✅ **Robust Validation**
- Email and phone number validation
- Age and data validation
- Exception handling with custom exceptions
- Input data validation at service layer

---

## 🚀 Quick Start (3 Steps)

### Prerequisites
- Java 11 or higher
- Maven 3.6+ (recommended)

### Step 1: Clone
```bash
git clone https://github.com/yourusername/meditrack.git
cd meditrack
```

### Step 2: Compile
```bash
mvn clean compile
```

### Step 3: Run
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

---

## 📁 Project Structure

```
src/main/java/com/airtribe/meditrack/
├── Main.java                          # Interactive CLI application
├── constants/Constants.java           # Application constants
├── entity/
│   ├── Person.java                    # Abstract base class
│   ├── Doctor.java                    # Doctor entity
│   ├── Patient.java                   # Patient entity
│   ├── Appointment.java               # Appointment entity
│   ├── Bill.java                      # Bill entity
│   └── BillSummary.java               # Immutable bill summary
├── service/
│   ├── DoctorService.java             # Doctor business logic
│   ├── PatientService.java            # Patient business logic
│   └── AppointmentService.java        # Appointment business logic
├── util/
│   ├── Validator.java                 # Input validation
│   ├── DateUtil.java                  # Date/time utilities
│   ├── CSVUtil.java                   # CSV file operations
│   ├── IdGenerator.java               # Thread-safe ID generation
│   ├── DataStore.java                 # Generic data storage<T>
│   └── AIHelper.java                  # Optional AI utilities
├── exception/
│   ├── AppointmentNotFoundException.java
│   └── InvalidDataException.java
└── interface_impl/
    ├── Searchable.java
    └── Payable.java
```

---

## 📖 Usage Guide

### Starting the Application

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### Main Menu

```
╔══════════════════════════════════════════════════════╗
║    Welcome to MediTrack - Clinic Management System   ║
╚══════════════════════════════════════════════════════╝

--- Main Menu ---
1. Doctor Management
2. Patient Management
3. Appointment Management
4. Exit
Enter your choice:
```

---

## 🎬 Demo Walkthrough - Complete Scenario

### Scenario: New Patient First Appointment

#### Step 1: Register a Doctor

**Input:**
```
Main Menu → 1 (Doctor Management) → 1 (Register Doctor)
Enter doctor name: Dr. John Smith
Enter email: john.smith@hospital.com
Enter phone number (10 digits): 9876543210
Enter specialty: CARDIOLOGY
Enter license number: LIC-2024-001
```

**Output:**
```
✓ Doctor registered successfully!
Doctor ID: DOC1000
```

---

#### Step 2: Register a Patient

**Input:**
```
Main Menu → 2 (Patient Management) → 1 (Register Patient)
Enter patient name: Alice Johnson
Enter email: alice.johnson@email.com
Enter phone number (10 digits): 8765432109
Enter age: 35
Enter medical history: Diabetic, no known allergies
```

**Output:**
```
✓ Patient registered successfully!
Patient ID: PAT2000
```

---

#### Step 3: View Available Doctors

**Input:**
```
Main Menu → 1 (Doctor Management) → 2 (View All Doctors)
```

**Output:**
```
--- All Doctors ---
Doctor{id='DOC1000', name='Dr. John Smith', email='john.smith@hospital.com', 
phoneNumber='9876543210', specialty='CARDIOLOGY', available=true, 
licenseNumber='LIC-2024-001'}
```

---

#### Step 4: Book an Appointment

**Input:**
```
Main Menu → 3 (Appointment Management) → 1 (Book Appointment)
Enter doctor ID: DOC1000
Enter patient ID: PAT2000
Enter appointment date and time (yyyy-MM-dd HH:mm:ss): 2026-02-15 10:00:00
Enter notes: Regular checkup for cardiac health
```

**Output:**
```
✓ Appointment booked successfully!
Appointment ID: APT3000
```

---

#### Step 5: View Patient Appointments

**Input:**
```
Main Menu → 3 (Appointment Management) → 3 (View Patient Appointments)
Enter patient ID: PAT2000
```

**Output:**
```
--- Patient Appointments ---
Appointment{appointmentId='APT3000', doctorId='DOC1000', patientId='PAT2000', 
appointmentDateTime=2026-02-15T10:00, status='SCHEDULED', 
notes='Regular checkup for cardiac health'}
```

---

#### Step 6: Complete the Appointment

**Input:**
```
Main Menu → 3 (Appointment Management) → 4 (Complete Appointment)
Enter appointment ID: APT3000
```

**Output:**
```
✓ Appointment marked as completed.
```

---

## 🧪 Running Tests

Run the comprehensive manual test suite:

```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.TestRunner"
```

### Test Output

```
========== MediTrack System Test Suite ==========

--- Testing Validation Utility ---
[✓ PASS] Valid email should pass
[✓ PASS] Invalid email should fail
[✓ PASS] Valid phone should pass
[✓ PASS] Invalid phone should fail
[✓ PASS] Valid age should pass
[✓ PASS] Invalid age should fail

--- Testing Doctor Service ---
[✓ PASS] Doctor registration successful
[✓ PASS] Second doctor registration successful
[✓ PASS] Get doctor by ID
[✓ PASS] Filter by specialty
[✓ PASS] Available doctors list
[✓ PASS] Available doctors after status change

--- Testing Patient Service ---
[✓ PASS] Patient registration successful
[✓ PASS] Second patient registration successful
[✓ PASS] Get patient by ID
[✓ PASS] Filter by age range
[✓ PASS] Medical history updated

--- Testing Appointment Service ---
[✓ PASS] Appointment booking successful
[✓ PASS] Get appointment by ID
[✓ PASS] Get appointments by patient
[✓ PASS] Get appointments by doctor
[✓ PASS] Appointment marked as completed
[✓ PASS] Invalid appointment ID handled

========== Test Summary ==========
Total Tests: 27
Passed: 27
Failed: 0
Success Rate: 100.0%
==================================
```

---

## ✔️ Input Validation Examples

### Valid Input
```
Doctor Name: Dr. John Smith ✓
Email: john@hospital.com ✓
Phone: 9876543210 ✓ (10 digits)
Age: 35 ✓ (between 1-150)
```

### Invalid Input (Error Messages)
```
Email: invalid-email ✗ 
Error: Invalid email format

Phone: 98765432 ✗
Error: Phone number must be 10 digits

Age: 200 ✗
Error: Age must be between 1 and 150

Doctor ID: INVALID ✗
Error: Doctor not found
```

---

## 🏗️ Key OOP Concepts Demonstrated

### Inheritance & Polymorphism
- `Doctor` and `Patient` extend `Person` abstract base class
- `Bill` implements `Payable` interface
- Interface implementation (`Searchable`, `Payable`)

### Encapsulation
- Private fields with public getters/setters
- Service layer encapsulates business logic
- Immutable `BillSummary` class

### SOLID Principles
- **S**: Each service handles one domain (Doctor, Patient, Appointment)
- **O**: Abstract `Person` class open for extension
- **L**: Consistent interface contracts
- **I**: Focused interfaces (`Searchable`, `Payable`)
- **D**: Dependency injection in services

### Advanced Java Features
- ⚡ **Thread-safe ID generation** using `AtomicLong`
- 🔄 **Streams & Lambdas** for filtering and searching
- 📦 **Generics** with `DataStore<T>`
- 💾 **Serialization** for persistence
- ✨ **Immutability** in `BillSummary`
- 🔀 **Cloning** support in entities

---

## 🛠️ Technologies Used

- **Language**: Java 11+
- **Build Tool**: Maven 3.6+
- **Collections**: ArrayList, Streams
- **Concurrency**: AtomicLong
- **Date/Time**: LocalDateTime
- **Exception Handling**: Custom exceptions

---

## 📚 Documentation

- [JVM_Report.md](docs/JVM_Report.md) - JVM concepts & Java internals
- [Setup_Instructions.md](docs/Setup_Instructions.md) - Complete setup guide
- [Design_Decisions.md](docs/Design_Decisions.md) - Architecture & patterns

---

## 🔮 Future Enhancements

- 🗄️ Database integration (MySQL, PostgreSQL)
- 🌐 REST API with Spring Boot
- 🔐 Authentication & authorization
- 📊 Analytics dashboard
- 🤖 AI recommendations
- 🧪 JUnit & Mockito tests

---

## 🤝 Contributing

Contributions welcome! Fork → commit → submit PR

---

## 📄 License

MIT License

---

## 📞 Contact

**Project**: MediTrack v1.0.0  
**Updated**: January 31, 2026