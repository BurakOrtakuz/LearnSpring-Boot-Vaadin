package com.example.application.repository;

import com.example.application.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPrescriptionRepository extends JpaRepository<Prescription, Long> {
    Prescription findByPrescriptionId(Long id);
}


