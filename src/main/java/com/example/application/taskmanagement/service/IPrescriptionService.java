package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Prescription;
import java.util.List;
import java.util.Optional;

public interface IPrescriptionService {
    List<Prescription> findAll();
    Optional<Prescription> findById(Long id);
    Prescription save(Prescription prescription);
    void deleteById(Long id);
}

