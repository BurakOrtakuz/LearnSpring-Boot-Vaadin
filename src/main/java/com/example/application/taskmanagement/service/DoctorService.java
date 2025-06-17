package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Doctor;
import com.example.application.taskmanagement.repository.IDoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements IDoctorService {
    private final IDoctorRepository doctorRepository;

    public DoctorService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }
}
