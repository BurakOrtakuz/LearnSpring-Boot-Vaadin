package com.example.application.service;

import com.example.application.domain.Patient;
import com.example.application.repository.IPatientRepository;
import com.example.application.specifications.PatientSpecification;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {
    private final IPatientRepository patientRepository;

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Page<Patient> findWithFilters(PatientFilterCriteria criteria, Pageable pageable) {
        Specification<Patient> spec = PatientSpecification.searchWithCriteria(criteria);
        return patientRepository.findAll(spec, pageable);
    }
}
