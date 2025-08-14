# Smart Clinic Management System — MySQL Database Schema

This document describes the database design for the Smart Clinic app.  
Core entities: **users, doctors, patients, appointments, vitals, prescriptions, medications, prescription_items**.

## 1) Tables & Columns

### users
- `id` INT PK AUTO_INCREMENT  
- `role` ENUM('ADMIN','DOCTOR','PATIENT') NOT NULL  
- `full_name` VARCHAR(100) NOT NULL  
- `email` VARCHAR(120) NOT NULL UNIQUE  
- `phone` VARCHAR(20)  
- `password_hash` VARCHAR(255) NOT NULL  
- `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### doctors
- `user_id` INT PK (FK → users.id)  
- `specialization` VARCHAR(100)  
- `license_no` VARCHAR(50) UNIQUE

### patients
- `user_id` INT PK (FK → users.id)  
- `dob` DATE  
- `gender` ENUM('M','F','O')  
- `blood_group` VARCHAR(5)  
- `allergies` TEXT

### appointments
- `id` INT PK AUTO_INCREMENT  
- `doctor_id` INT NOT NULL (FK → doctors.user_id)  
- `patient_id` INT NOT NULL (FK → patients.user_id)  
- `appointment_time` DATETIME NOT NULL  
- `reason` VARCHAR(255)  
- `status` ENUM('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED'  
- `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### vitals
- `id` INT PK AUTO_INCREMENT  
- `appointment_id` INT NOT NULL (FK → appointments.id)  
- `height_cm` DECIMAL(5,2)  
- `weight_kg` DECIMAL(5,2)  
- `bp_systolic` INT  
- `bp_diastolic` INT  
- `pulse` INT  
- `temperature_c` DECIMAL(4,1)

### prescriptions
- `id` INT PK AUTO_INCREMENT  
- `appointment_id` INT NOT NULL (FK → appointments.id)  
- `notes` TEXT  
- `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP

### medications
- `id` INT PK AUTO_INCREMENT  
- `name` VARCHAR(120) NOT NULL UNIQUE  
- `dosage_form` VARCHAR(50)

### prescription_items
- `id` INT PK AUTO_INCREMENT  
- `prescription_id` INT NOT NULL (FK → prescriptions.id)  
- `medication_id` INT NOT NULL (FK → medications.id)  
- `dose` VARCHAR(50)  
- `frequency` VARCHAR(50)     <!-- e.g., "1-0-1" -->
- `duration_days` INT  
- `instructions` VARCHAR(255)

## 2) Relationships (ER)
- `users 1–1 doctors`, `users 1–1 patients`  
- `doctors 1–M appointments`  
- `patients 1–M appointments`  
- `appointments 1–1 prescriptions`  
- `prescriptions 1–M prescription_items`  
- `medications 1–M prescription_items`

## 3) MySQL DDL (ready to run)
```sql
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  role ENUM('ADMIN','DOCTOR','PATIENT') NOT NULL,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  phone VARCHAR(20),
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE doctors (
  user_id INT PRIMARY KEY,
  specialization VARCHAR(100),
  license_no VARCHAR(50) UNIQUE,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE patients (
  user_id INT PRIMARY KEY,
  dob DATE,
  gender ENUM('M','F','O'),
  blood_group VARCHAR(5),
  allergies TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB;

CREATE TABLE appointments (
  id INT PRIMARY KEY AUTO_INCREMENT,
  doctor_id INT NOT NULL,
  patient_id INT NOT NULL,
  appointment_time DATETIME NOT NULL,
  reason VARCHAR(255),
  status ENUM('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_doc_time (doctor_id, appointment_time),
  INDEX idx_pat_time (patient_id, appointment_time),
  FOREIGN KEY (doctor_id) REFERENCES doctors(user_id),
  FOREIGN KEY (patient_id) REFERENCES patients(user_id)
) ENGINE=InnoDB;

CREATE TABLE vitals (
  id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  height_cm DECIMAL(5,2),
  weight_kg DECIMAL(5,2),
  bp_systolic INT,
  bp_diastolic INT,
  pulse INT,
  temperature_c DECIMAL(4,1),
  FOREIGN KEY (appointment_id) REFERENCES appointments(id)
) ENGINE=InnoDB;

CREATE TABLE prescriptions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL,
  notes TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY uniq_appointment (appointment_id),
  FOREIGN KEY (appointment_id) REFERENCES appointments(id)
) ENGINE=InnoDB;

CREATE TABLE medications (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL UNIQUE,
  dosage_form VARCHAR(50)
) ENGINE=InnoDB;

CREATE TABLE prescription_items (
  id INT PRIMARY KEY AUTO_INCREMENT,
  prescription_id INT NOT NULL,
  medication_id INT NOT NULL,
  dose VARCHAR(50),
  frequency VARCHAR(50),
  duration_days INT,
  instructions VARCHAR(255),
  INDEX idx_prescription (prescription_id),
  INDEX idx_medication (medication_id),
  FOREIGN KEY (prescription_id) REFERENCES prescriptions(id),
  FOREIGN KEY (medication_id) REFERENCES medications(id)
) ENGINE=InnoDB;
