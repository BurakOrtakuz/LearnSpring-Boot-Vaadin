package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPatientRepository extends JpaRepository<Patient, Long> {
}
