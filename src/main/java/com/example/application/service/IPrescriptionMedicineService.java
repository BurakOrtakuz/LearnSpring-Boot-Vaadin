package com.example.application.service;

import com.example.application.domain.PrescriptionMedicine;
import java.util.List;
import java.util.Optional;

public interface IPrescriptionMedicineService {
    List<PrescriptionMedicine> findAll();
    Optional<PrescriptionMedicine> findById(Long id);
    PrescriptionMedicine save(PrescriptionMedicine prescriptionMedicine);
    void deleteById(Long id);
}

