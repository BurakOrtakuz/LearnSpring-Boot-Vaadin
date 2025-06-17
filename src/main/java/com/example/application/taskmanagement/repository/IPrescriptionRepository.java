package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrescriptionRepository extends JpaRepository<Prescription, Long> {
}
