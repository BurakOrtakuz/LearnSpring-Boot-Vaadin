package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
}
