package com.example.application.repository;

import com.example.application.domain.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
}
