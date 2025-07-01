package com.example.application.service;

import com.example.application.domain.Prescription;
import java.util.List;
import java.util.Optional;

public interface IPrescriptionService {
    List<Prescription> findAll();
    Optional<Prescription> findById(Long id);
    Prescription save(Prescription prescription);
    void deleteById(Long id);

    //Document
    Prescription saveDocument(Long prescriptionId, byte[] document);
    byte[] getDocument(Long prescriptionId);

    Optional<Prescription> getByExaminationId(Long examinationId);
}

