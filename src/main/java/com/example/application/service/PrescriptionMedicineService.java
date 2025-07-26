package com.example.application.service;

import com.example.application.domain.PrescriptionMedicine;
import com.example.application.dto.IPrescriptionMedicineByPersonResult;
import com.example.application.repository.IPrescriptionMedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionMedicineService implements IPrescriptionMedicineService {
    private final IPrescriptionMedicineRepository prescriptionMedicineRepository;

    public PrescriptionMedicineService(IPrescriptionMedicineRepository prescriptionMedicineRepository) {
        this.prescriptionMedicineRepository = prescriptionMedicineRepository;
    }

    @Override
    public List<PrescriptionMedicine> findAll() {
        return prescriptionMedicineRepository.findAll();
    }

    @Override
    public List<IPrescriptionMedicineByPersonResult> findByPrescription_Person_PersonId(Long personId) {
        return prescriptionMedicineRepository.findByPrescription_Person_PersonId(personId);
    }

    @Override
    public Optional<PrescriptionMedicine> findById(Long id) {
        return prescriptionMedicineRepository.findById(id);
    }

    @Override
    public PrescriptionMedicine save(PrescriptionMedicine prescriptionMedicine) {
        return prescriptionMedicineRepository.save(prescriptionMedicine);
    }

    @Override
    public void deleteById(Long id) {
        prescriptionMedicineRepository.deleteById(id);
    }
}
