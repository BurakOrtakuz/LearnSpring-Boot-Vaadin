package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Patient;
import java.util.List;
import java.util.Optional;

public interface IPatientService {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    void deleteById(Long id);
}

