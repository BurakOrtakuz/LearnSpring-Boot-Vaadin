package com.example.application.service;

import com.example.application.domain.Doctor;
import com.example.application.domain.Person;

import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    List<Doctor> findAll();
    Optional<Doctor> findById(Long id);
    Doctor save(Doctor doctor);
    void deleteById(Long id);

    Optional<Person> findPersonByUsername(String username);
}

