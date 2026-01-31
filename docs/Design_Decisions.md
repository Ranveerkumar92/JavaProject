# Design Decisions - MediTrack

## Table of Contents
1. [Architecture Overview](#architecture-overview)
2. [Design Patterns](#design-patterns)
3. [SOLID Principles](#solid-principles)
4. [Class Design Decisions](#class-design-decisions)
5. [Service Layer Design](#service-layer-design)
6. [Exception Handling Strategy](#exception-handling-strategy)
7. [Data Storage Approach](#data-storage-approach)
8. [Future Scalability](#future-scalability)

---

## Architecture Overview

### Layered Architecture

MediTrack follows a **3-tier layered architecture**:

```
┌─────────────────────────────────┐
│   Presentation Layer (Main.java)│ ← User Interface & Input
├─────────────────────────────────┤
│   Service Layer (Services)      │ ← Business Logic & Validation
├─────────────────────────────────┤
│   Persistence Layer (DataStore) │ ← Data Storage & Retrieval
├─────────────────────────────────┤
│   Entity Layer (Entities)       │ ← Domain Models
└─────────────────────────────────┘
```

### Rationale
- **Separation of Concerns**: Each layer has distinct responsibility
- **Testability**: Easy to test each layer independently
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Can replace DataStore with database layer later

---

## Design Patterns

### 1. **Service Layer Pattern**

```java
// Business logic separated from presentation
public class DoctorService {
    private DataStore<Doctor> doctorStore;
    
    public Doctor registerDoctor(...) {
        // Validation
        // Business logic
        // Persistence
    }
}
```

**Benefit**: Reusable across different UIs (CLI, GUI, REST API)

### 2. **Generic Data Access Object (DAO) Pattern**

```java
public class DataStore<T> {
    private List<T> data = new ArrayList<>();
    
    public void add(T entity) { data.add(entity); }
    public List<T> getAll() { return new ArrayList<>(data); }
}
```

**Benefit**: Type-safe storage, easily replaceable with database

### 3. **Factory Pattern**

```java
public class IdGenerator {
    public static String generateDoctorId() {
        return "DOC" + doctorIdCounter.getAndIncrement();
    }
}
```

**Benefit**: Centralized ID generation, ensures uniqueness

### 4. **Immutable Object Pattern**

```java
public final class BillSummary implements Serializable {
    private final String billId;      // final - cannot be changed
    private final double totalAmount;
    
    public BillSummary(Bill bill) {   // Only way to create
        this.billId = bill.getBillId();
    }
    
    // Only getters, no setters
    public String getBillId() { return billId; }
}
```

**Benefit**: Thread-safe, prevents accidental modifications

### 5. **Strategy Pattern**

```java
// Two strategies for searching
public interface Searchable {
    boolean matchesId(String id);      // Strategy 1
    boolean matchesName(String name);  // Strategy 2
}

public class Doctor implements Searchable {
    // Implements different search strategies
}
```

**Benefit**: Flexible search mechanisms

### 6. **Dependency Injection Pattern**

```java
public class AppointmentService {
    private DoctorService doctorService;
    private PatientService patientService;
    
    // Dependencies injected via constructor
    public AppointmentService(DoctorService doctorService, 
                             PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }
}
```

**Benefit**: Loose coupling, easy to test with mock objects

---

## SOLID Principles

### 1. **Single Responsibility Principle (SRP)**

Each class has ONE reason to change:

```java
// DoctorService - only responsible for Doctor operations
public class DoctorService {
    public Doctor registerDoctor(...) { }
    public Doctor getDoctorById(...) { }
    public List<Doctor> getDoctorsBySpecialty(...) { }
}

// PatientService - only responsible for Patient operations
public class PatientService {
    public Patient registerPatient(...) { }
    public Patient getPatientById(...) { }
}

// IdGenerator - only responsible for ID generation
public class IdGenerator {
    public static String generateDoctorId() { }
}
```

### 2. **Open/Closed Principle (OCP)**

Classes open for extension, closed for modification:

```java
// Abstract base class - open for extension
public abstract class Person implements Serializable {
    protected String id;
    protected String name;
    // Common functionality
}

// Extended without modifying Person
public class Doctor extends Person implements Searchable {
    private String specialty;
    // Doctor-specific functionality
}

public class Patient extends Person implements Searchable {
    private int age;
    // Patient-specific functionality
}
```

### 3. **Liskov Substitution Principle (LSP)**

Subclasses can substitute superclasses:

```java
Person person = new Doctor("DOC1", "Dr. Jane", "jane@example.com", "9876543210", "CARDIOLOGY", "LIC001", DoctorAvailability.AVAILABLE);        // Valid - Doctor is a Person
person = new Patient("PAT1", "John Doe", "john@example.com", "8765432109", 35, "No known allergies");              // Valid - Patient is a Person

// Both can be used interchangeably where Person is expected
```

### 4. **Interface Segregation Principle (ISP)**

Clients shouldn't depend on interfaces they don't use:

```java
// Focused interface - only what's needed for searching
public interface Searchable {
    boolean matchesId(String id);
    boolean matchesName(String name);
}

// Focused interface - only what's needed for payment
public interface Payable {
    double getAmount();
    void markAsPaid();
    boolean isPaid();
}

// Bill implements Payable (not Searchable)
public class Bill implements Payable { }

// Doctor implements Searchable (not Payable)
public class Doctor extends Person implements Searchable { }
```

### 5. **Dependency Inversion Principle (DIP)**

Depend on abstractions, not concretions:

```java
// ❌ WRONG - Tightly coupled
public class AppointmentService {
    DoctorService doctorService = new DoctorService();  // Concrete
}

// ✅ CORRECT - Loosely coupled
public class AppointmentService {
    private DoctorService doctorService;  // Abstraction/Interface
    
    public AppointmentService(DoctorService doctorService) {
        this.doctorService = doctorService;  // Injected
    }
}
```

---

## Class Design Decisions

### 1. **Person as Abstract Base Class**

**Decision**: Made `Person` abstract, not interface

```java
public abstract class Person implements Serializable {
    protected String id;
    protected String name;
    protected String email;
    protected String phoneNumber;
    
    // Common implementation
    public String getId() { return id; }
}
```

**Rationale**:
- Doctor and Patient share common fields (id, name, email, phone)
- Common getters/setters can be implemented once
- Better for serialization
- More cohesive than just interface

### 2. **Doctor and Patient Implement Searchable**

**Decision**: Made both searchable with ID and name

```java
public class Doctor extends Person implements Searchable {
    @Override
    public boolean matchesId(String id) {
        return this.id.equalsIgnoreCase(id);
    }
    
    @Override
    public boolean matchesName(String name) {
        return this.name.equalsIgnoreCase(name);
    }
}
```

**Rationale**:
- Common search functionality
- Consistent interface for searching
- Easy to add more search strategies later

### 3. **Cloneable Support in Doctor and Patient**

```java
public class Doctor extends Person implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
```

**Rationale**:
- Useful for creating copies without modification
- Future feature: "Duplicate doctor profile" functionality
- Supports deep cloning patterns

### 4. **Immutable BillSummary Class**

**Decision**: Made `BillSummary` immutable with `final` keyword

```java
public final class BillSummary implements Serializable {
    private final String billId;
    private final String patientId;
    private final double totalAmount;
    private final boolean paid;
    
    // Only getters, no setters
    public String getBillId() { return billId; }
}
```

**Rationale**:
- Bill summaries represent historical records
- Immutability ensures data integrity
- Thread-safe without synchronization
- Prevents accidental modifications

---

## Service Layer Design

### 1. **DoctorService**

```java
public class DoctorService {
    private DataStore<Doctor> doctorStore;
    
    // CRUD Operations
    public Doctor registerDoctor(...);
    public Doctor getDoctorById(String doctorId);
    public boolean removeDoctor(String doctorId);
    
    // Business Logic
    public List<Doctor> getDoctorsBySpecialty(String specialty);
    public List<Doctor> getAvailableDoctors();
    public void setDoctorAvailability(String doctorId, boolean available);
}
```

**Design Rationale**:
- Encapsulates all doctor-related operations
- Validates input before persistence
- Returns meaningful results with proper error handling

### 2. **PatientService**

```java
public class PatientService {
    private DataStore<Patient> patientStore;
    
    // CRUD Operations
    public Patient registerPatient(...);
    public Patient getPatientById(String patientId);
    public boolean removePatient(String patientId);
    
    // Business Logic
    public List<Patient> getPatientsByAgeRange(int minAge, int maxAge);
    public void updateMedicalHistory(String patientId, String medicalHistory);
}
```

**Design Rationale**:
- Similar structure to DoctorService
- Validates age using Validator utility
- Easy to extend with more patient-specific operations

### 3. **AppointmentService**

```java
public class AppointmentService {
    private AppointmentStore appointmentStore;
    private DoctorService doctorService;      // Dependency
    private PatientService patientService;    // Dependency
    
    // CRUD Operations
    public Appointment bookAppointment(...);
    public void cancelAppointment(String appointmentId);
    public void completeAppointment(String appointmentId);
    
    // Business Logic
    public List<Appointment> getAppointmentsByPatient(String patientId);
    public List<Appointment> getAppointmentsByDoctor(String doctorId);
}
```

**Design Rationale**:
- Depends on both DoctorService and PatientService
- Validates doctor and patient exist before booking
- Checks doctor availability
- Ensures appointment is in future

---

## Exception Handling Strategy

### 1. **Custom Exceptions**

```java
// Specific exception for not found scenarios
public class AppointmentNotFoundException extends Exception {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}

// General exception for invalid data
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}
```

**Rationale**:
- Specific exceptions allow precise error handling
- Generic `Exception` too broad
- Helps distinguish error types in catch blocks

### 2. **Validation at Service Layer**

```java
public Doctor registerDoctor(...) throws InvalidDataException {
    // Validate before creating object
    Validator.validateDoctor(name, email, phoneNumber);
    
    // Only proceed if validation passes
    String doctorId = IdGenerator.generateDoctorId();
    // Example: create doctor with explicit availability
    Doctor doctor = new Doctor(doctorId, "Dr. Example", "doc@example.com", "9876543210", "GENERAL", "LIC123", DoctorAvailability.AVAILABLE);
    doctorStore.add(doctor);
    return doctor;
}
```

**Rationale**:
- Prevents invalid data from entering the system
- Clear error messages for users
- Validates once at entry point

### 3. **User-Friendly Error Messages**

```java
if (!isValidEmail(email)) {
    throw new InvalidDataException("Invalid email format");
}

if (!isValidPhoneNumber(phone)) {
    throw new InvalidDataException("Phone number must be 10 digits");
}
```

**Rationale**:
- Clear messages for debugging
- Helps users understand what went wrong

---

## Data Storage Approach

### 1. **Generic DataStore<T> Implementation**

```java
public class DataStore<T> {
    private List<T> data = new ArrayList<>();
    
    public void add(T entity) { }
    public boolean remove(T entity) { }
    public List<T> getAll() { }
    public boolean contains(T entity) { }
}
```

**Rationale**:
- Type-safe generic implementation
- Easy to replace with database (DAO pattern)
- Works for any entity type
- Supports in-memory storage for prototyping

### 2. **In-Memory Storage (Current)**

```java
// Services use DataStore for in-memory storage
public class DoctorService {
    private DataStore<Doctor> doctorStore = new DataStore<>();
}
```

**Tradeoff Analysis**:

| Aspect | In-Memory | Database |
|--------|-----------|----------|
| Speed | Very fast | Slower |
| Data Persistence | Lost on restart | Persistent |
| Scalability | Limited | Excellent |
| Complexity | Simple | Complex |

**Decision**: In-memory for MVP, easily upgradeable to database

### 3. **Future Database Migration**

```java
// Future implementation
public interface IDataStore<T> {
    void add(T entity);
    T getById(String id);
    List<T> getAll();
}

// In-memory implementation
public class InMemoryDataStore<T> implements IDataStore<T> { }

// Database implementation
public class DatabaseDataStore<T> implements IDataStore<T> { }

// Services use interface, not concrete class
public class DoctorService {
    private IDataStore<Doctor> doctorStore;
}
```

---

## Thread Safety Considerations

### 1. **AtomicLong for ID Generation**

```java
private static final AtomicLong doctorIdCounter = new AtomicLong(1000);

public static String generateDoctorId() {
    return "DOC" + doctorIdCounter.getAndIncrement();  // Thread-safe
}
```

**Rationale**:
- Multiple threads might call simultaneously
- AtomicLong ensures atomic increment
- No ID duplication possible
- Better than synchronized blocks

### 2. **Immutable BillSummary**

```java
public final class BillSummary {
    private final String billId;  // Immutable
    // No setters - thread-safe by design
}
```

**Rationale**:
- Multiple threads can safely read
- No synchronization needed
- Better performance

### 3. **ArrayList vs CopyOnWriteArrayList**

```java
// Current: ArrayList - faster for reads, single-threaded
private List<T> data = new ArrayList<>();

// Future: For concurrent access
private List<T> data = new CopyOnWriteArrayList<>();
```

**Decision**: ArrayList sufficient for current design

---

## Validation Strategy

### 1. **Layered Validation**

```java
// Input layer
if (!Validator.isNotEmpty(name)) {
    throw new InvalidDataException(...);
}

// Business layer
if (doctorService.getDoctorById(doctorId) == null) {
    throw new InvalidDataException(...);
}

// Entity layer - could add annotations for future ORM
@Email
private String email;

@Phone
private String phoneNumber;
```

**Rationale**:
- Multiple validation points catch errors early
- Clear error messages at each level
- Prevents invalid data propagation

### 2. **Batch Validation**

```java
public static void validateDoctor(String name, String email, String phone) 
    throws InvalidDataException {
    if (!isNotEmpty(name)) throw new InvalidDataException(...);
    if (!isValidEmail(email)) throw new InvalidDataException(...);
    if (!isValidPhoneNumber(phone)) throw new InvalidDataException(...);
}
```

**Rationale**:
- One method call for all validations
- Consistent error handling
- Easy to enhance with more rules

---

## Future Scalability

### 1. **Database Integration Path**

```
Phase 1: In-Memory Storage (Current)
    ↓
Phase 2: File-Based Storage (CSV/JSON)
    ↓
Phase 3: Relational Database (MySQL, PostgreSQL)
    ↓
Phase 4: NoSQL Database (MongoDB, Firebase)
```

### 2. **API Layer Addition**

```java
// REST endpoints (future with Spring Boot)
@PostMapping("/doctors")
public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorDTO dto) {
    return ResponseEntity.ok(doctorService.registerDoctor(...));
}

@GetMapping("/doctors/{id}")
public ResponseEntity<Doctor> getDoctor(@PathVariable String id) {
    return ResponseEntity.ok(doctorService.getDoctorById(id));
}
```

### 3. **Caching Strategy**

```java
// Future: Add caching for frequently accessed data
@Cacheable("doctors")
public Doctor getDoctorById(String doctorId) {
    return doctorStore.getById(doctorId);
}
```

### 4. **Concurrency Enhancement**

```java
// Future: For multi-threaded environment
private final List<Doctor> doctors = Collections.synchronizedList(new ArrayList<>());

// Or use ConcurrentHashMap for indexed access
private final Map<String, Doctor> doctorMap = new ConcurrentHashMap<>();
```

---

## Performance Optimization Decisions

### 1. **Stream API Usage**

```java
// Efficient filtering with streams
public List<Doctor> getDoctorsBySpecialty(String specialty) {
    return doctorStore.getAll().stream()
            .filter(d -> d.getSpecialty().equalsIgnoreCase(specialty))
            .collect(Collectors.toList());
}
```

**Benefit**: Lazy evaluation, parallelizable, readable

### 2. **Optional Streaming**

```java
// Better than null checks
public Doctor getDoctorById(String doctorId) {
    return doctorStore.getAll().stream()
            .filter(d -> d.matchesId(doctorId))
            .findFirst()
            .orElse(null);  // Or throw exception
}
```

**Benefit**: Functional style, fewer null pointer errors

### 3. **Defensive Copying**

```java
public List<T> getAll() {
    return new ArrayList<>(data);  // Return copy, not reference
}
```

**Benefit**: Prevents external modification of internal data

---

## Conclusion

MediTrack's design emphasizes:
- ✅ **Modularity**: Each component has clear responsibility
- ✅ **Extensibility**: Easy to add new features
- ✅ **Maintainability**: Clean code, clear patterns
- ✅ **Testability**: Loosely coupled components
- ✅ **Scalability**: Designed for future growth
- ✅ **Thread-Safety**: Critical operations protected
- ✅ **Performance**: Efficient data structures and algorithms

The architecture supports evolution from a prototype to a production system without major refactoring.

---

**Last Updated**: January 31, 2026
**Version**: 1.0.0
