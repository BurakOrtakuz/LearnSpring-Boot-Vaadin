package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Prescription;
import com.example.application.taskmanagement.repository.IPrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService implements IPrescriptionService {
    private final IPrescriptionRepository prescriptionRepository;

    public PrescriptionService(IPrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public List<Prescription> findAll() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> findById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription save(Prescription prescription) {
        // Sadece examination ve document alanları ile kaydetme işlemi yapılır
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deleteById(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public Prescription saveDocument(Long prescriptionId, byte[] document) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));
        prescription.setDocument(document);
        return prescriptionRepository.save(prescription);
    }

    public byte[] getDocument(Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Prescription not found"));
        return prescription.getDocument();
    }
}
