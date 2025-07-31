package com.example.application.service;

import com.example.application.domain.Patient;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPatientService {
    List<Patient> findAll();
    Optional<Patient> findById(Long id);
    Patient save(Patient patient);
    void deleteById(Long id);

    Page<Patient> findWithFilters(PatientFilterCriteria criteria, Pageable pageable);
}

