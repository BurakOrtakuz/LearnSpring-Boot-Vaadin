package com.example.application.repository;

import com.example.application.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPatientRepository extends JpaRepository<Patient, Long> , JpaSpecificationExecutor<Patient> {
}
