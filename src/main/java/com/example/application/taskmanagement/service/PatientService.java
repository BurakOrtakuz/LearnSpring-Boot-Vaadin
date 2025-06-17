package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Patient;
import com.example.application.taskmanagement.repository.IPatientRepository;
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
}
