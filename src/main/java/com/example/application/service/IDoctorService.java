package com.example.application.service;

import com.example.application.domain.Doctor;
import com.example.application.domain.Patient;
import com.example.application.domain.Person;
import com.example.application.specifications.criteria.DoctorFilterCriteria;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface IDoctorService {
    List<Doctor> findAll();
    Optional<Doctor> findById(Long id);
    Doctor save(Doctor doctor);
    void deleteById(Long id);

    Optional<Person> findPersonByUsername(String username);
    Page<Doctor> findWithFilters(DoctorFilterCriteria criteria, Pageable pageable);

}

