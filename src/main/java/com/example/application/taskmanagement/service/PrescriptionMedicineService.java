package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.PrescriptionMedicine;
import com.example.application.taskmanagement.repository.IPrescriptionMedicineRepository;
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
