package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
}
