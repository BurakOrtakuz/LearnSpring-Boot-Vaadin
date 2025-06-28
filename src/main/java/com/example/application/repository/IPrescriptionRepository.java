package com.example.application.repository;

import com.example.application.domain.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrescriptionRepository extends JpaRepository<Prescription, Long> {
}
