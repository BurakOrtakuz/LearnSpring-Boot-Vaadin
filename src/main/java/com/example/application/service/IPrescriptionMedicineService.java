package com.example.application.service;

import com.example.application.domain.PrescriptionMedicine;
import com.example.application.dto.IPrescriptionMedicineByPersonResult;

import java.util.List;
import java.util.Optional;

public interface IPrescriptionMedicineService {
    List<PrescriptionMedicine> findAll();
    List<IPrescriptionMedicineByPersonResult> findByPrescription_Person_PersonId(Long personId);
    Optional<PrescriptionMedicine> findById(Long id);
    PrescriptionMedicine save(PrescriptionMedicine prescriptionMedicine);
    void deleteById(Long id);

    List<PrescriptionMedicine> getMedicinesByPrescriptionId(Long id);
}

