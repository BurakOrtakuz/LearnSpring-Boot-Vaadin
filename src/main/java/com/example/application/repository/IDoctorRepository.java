package com.example.application.repository;

import com.example.application.domain.Doctor;
import com.example.application.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IDoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
}
