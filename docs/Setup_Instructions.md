# Setup Instructions - MediTrack

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Configuration](#configuration)
4. [Compilation](#compilation)
5. [Execution](#execution)
6. [Troubleshooting](#troubleshooting)

---

## Prerequisites

### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **Java Development Kit (JDK)**: Version 11 or higher
- **Memory**: Minimum 256 MB RAM, recommended 512 MB or more
- **Disk Space**: 100 MB for installation and data

### Software Requirements
1. **JDK 11+** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/)
2. **Apache Maven 3.6+** (optional) - [Download from Maven](https://maven.apache.org/download.cgi)
3. **Git** (optional) - [Download from Git](https://git-scm.com/downloads)
4. **IDE** (optional) - IntelliJ IDEA, Eclipse, or VS Code

---

## Installation

### Step 1: Install Java Development Kit (JDK)

#### On Windows
1. Download JDK 11 or higher from [Oracle website](https://www.oracle.com/java/technologies/downloads/)
2. Run the installer (.exe file)
3. Follow the installation wizard and complete the setup
4. Verify installation:
   ```bash
   java -version
   javac -version
   ```

#### On macOS
```bash
# Using Homebrew (recommended)
brew install openjdk@11
brew link openjdk@11

# Verify
java -version
```

#### On Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk

# Verify
java -version
javac -version
```

### Step 2: Install Apache Maven (Optional but Recommended)

#### On Windows
1. Download Apache Maven from [maven.apache.org](https://maven.apache.org/download.cgi)
2. Extract to a directory (e.g., `C:\Maven`)
3. Add Maven to system PATH:
   - Right-click "This PC" → Properties → Advanced system settings
   - Click "Environment Variables"
   - Add new variable: `MAVEN_HOME = C:\Maven`
   - Edit PATH and add: `%MAVEN_HOME%\bin`
4. Verify:
   ```bash
   mvn -version
   ```

#### On macOS
```bash
brew install maven

# Verify
mvn -version
```

#### On Linux
```bash
sudo apt-get install maven

# Verify
mvn -version
```

### Step 3: Clone the Repository

```bash
# Using Git
git clone https://github.com/yourusername/meditrack.git
cd meditrack

# Or download as ZIP and extract manually
```

---

## Configuration

### Set JAVA_HOME Environment Variable

#### On Windows
```bash
# Add to system PATH or set in terminal
set JAVA_HOME=C:\Program Files\Java\jdk-11.0.x

# Verify
echo %JAVA_HOME%
```

#### On macOS/Linux
```bash
# Add to ~/.bash_profile or ~/.bashrc
export JAVA_HOME=$(/usr/libexec/java_home -v 11)

# Apply changes
source ~/.bash_profile

# Verify
echo $JAVA_HOME
```

### Project Structure Verification

```
meditrack/
├── pom.xml
├── README.md
├── docs/
│   ├── JVM_Report.md
│   ├── Setup_Instructions.md
│   └── Design_Decisions.md
├── src/
│   ├── main/java/com/airtribe/meditrack/
│   │   ├── Main.java
│   │   ├── constants/
│   │   ├── entity/
│   │   ├── service/
│   │   ├── util/
│   │   ├── exception/
│   │   └── interface_impl/
│   └── test/java/com/airtribe/meditrack/
│       └── TestRunner.java
└── target/ (created after compilation)
```

---

## Compilation

### Option 1: Using Maven (Recommended)

```bash
# Navigate to project directory
cd meditrack

# Clean and compile
mvn clean compile

# Expected output:
# [INFO] BUILD SUCCESS
```

### Option 2: Using javac Manually

```bash
# Create output directory
mkdir -p bin

# Compile all Java files
javac -d bin -sourcepath src/main/java src/main/java/com/airtribe/meditrack/**/*.java

# Or compile specific directory
cd src/main/java
javac -d ../../../bin com/airtribe/meditrack/**/*.java
```

### Verify Compilation

Check that `.class` files were created:

```bash
# Using Maven
ls target/classes/com/airtribe/meditrack/**/*.class

# Using javac
ls bin/com/airtribe/meditrack/**/*.class
```

---

## Execution

### Option 1: Running with Maven

#### Start the Application
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

#### Run Test Suite
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.TestRunner"
```

#### Package as JAR
```bash
mvn clean package

# Run the packaged JAR
java -jar target/meditrack-1.0.0.jar
```

### Option 2: Running with Java Directly

#### Start the Application
```bash
java -cp target/classes com.airtribe.meditrack.Main
```

#### Run Test Suite
```bash
java -cp target/classes com.airtribe.meditrack.TestRunner
```

#### From Manually Compiled Classes
```bash
java -cp bin com.airtribe.meditrack.Main
```

### Interactive Menu Options

Once started, you'll see:

```
╔══════════════════════════════════════════════════════╗
║    Welcome to MediTrack - Clinic Management System   ║
╚══════════════════════════════════════════════════════╝

--- Main Menu ---
1. Doctor Management
2. Patient Management
3. Appointment Management
4. Exit
Enter your choice: _
```

---

## Troubleshooting

### Issue: "Java command not found"
**Solution**: 
- Verify JDK installation: `java -version`
- Add Java to PATH environment variable
- Restart terminal/IDE after setting PATH

### Issue: "Maven command not found"
**Solution**:
- Verify Maven installation: `mvn -version`
- Check MAVEN_HOME environment variable
- Add Maven to PATH

### Issue: "Compilation errors"
**Solution**:
```bash
# Ensure correct JDK version
javac -version  # Should be 11 or higher

# Clean and recompile
mvn clean compile -X  # -X for debug output
```

### Issue: "Class not found exception"
**Solution**:
- Check classpath is set correctly
- Verify `.class` files exist in target/classes or bin directory
- Use absolute paths when specifying classpath

### Issue: "Port already in use" (if API server added later)
**Solution**:
- Change port in configuration file
- Kill process using the port:
  ```bash
  # Windows
  netstat -ano | findstr :8080
  taskkill /PID <PID> /F
  
  # macOS/Linux
  lsof -i :8080
  kill -9 <PID>
  ```

### Issue: "Memory insufficient"
**Solution**:
```bash
# Increase heap size
java -Xmx1024m -cp target/classes com.airtribe.meditrack.Main
```

### Issue: "Permission denied" (Linux/macOS)
**Solution**:
```bash
# Make script executable
chmod +x run.sh

# Or run with bash
bash run.sh
```

---

## Quick Start Script

### Windows (create `run.bat`)
```batch
@echo off
echo Compiling MediTrack...
mvn clean compile

echo.
echo Running MediTrack...
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

### macOS/Linux (create `run.sh`)
```bash
#!/bin/bash
echo "Compiling MediTrack..."
mvn clean compile

echo ""
echo "Running MediTrack..."
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
```

---

## Verification Steps

### 1. Verify Java Installation
```bash
java -version
# Should output: openjdk version "11.0.x" or similar
```

### 2. Verify Compilation
```bash
mvn clean compile
# Should output: [INFO] BUILD SUCCESS
```

### 3. Verify Test Suite
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.TestRunner"
# Should output: Test Summary with 100% pass rate
```

### 4. Verify Application Start
```bash
mvn exec:java -Dexec.mainClass="com.airtribe.meditrack.Main"
# Should display interactive menu
```

---

## Development Environment Setup

### Using IntelliJ IDEA
1. Open IntelliJ IDEA
2. File → Open → Select meditrack directory
3. Mark `src/main/java` as Sources Root
4. Mark `src/test/java` as Test Sources Root
5. Run → Run 'Main' to start the application

### Using Eclipse
1. File → Import → Existing Maven Projects
2. Select meditrack directory
3. Click Finish
4. Run → Run As → Java Application
5. Select Main.java

### Using VS Code
1. Open meditrack folder
2. Install "Extension Pack for Java" extension
3. Create `.vscode/launch.json`:
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "name": "MediTrack",
         "type": "java",
         "name": "Launch Main",
         "request": "launch",
         "mainClass": "com.airtribe.meditrack.Main",
         "projectName": "meditrack"
       }
     ]
   }
   ```
4. Press F5 to run

---

## Next Steps

After successful setup:
1. Read [Design_Decisions.md](Design_Decisions.md) for architecture details
2. Review [JVM_Report.md](JVM_Report.md) for JVM concepts
3. Explore the source code in `src/main/java`
4. Run the test suite to verify functionality
5. Extend the system with new features

---

**Last Updated**: January 31, 2026
**Version**: 1.0.0
