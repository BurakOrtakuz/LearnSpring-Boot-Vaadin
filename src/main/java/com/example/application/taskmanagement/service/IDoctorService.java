package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Doctor;
import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    List<Doctor> findAll();
    Optional<Doctor> findById(Long id);
    Doctor save(Doctor doctor);
    void deleteById(Long id);
}

