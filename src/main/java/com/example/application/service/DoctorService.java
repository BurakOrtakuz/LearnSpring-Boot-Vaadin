package com.example.application.service;

import com.example.application.domain.Doctor;
import com.example.application.domain.Patient;
import com.example.application.domain.Person;
import com.example.application.repository.IDoctorRepository;
import com.example.application.repository.IPersonRepository;
import com.example.application.specifications.DoctorSpecification;
import com.example.application.specifications.PatientSpecification;
import com.example.application.specifications.criteria.DoctorFilterCriteria;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements IDoctorService {
    private final IDoctorRepository doctorRepository;
    private final IPersonRepository personRepository;

    public DoctorService(IDoctorRepository doctorRepository, IPersonRepository personRepository) {
        this.doctorRepository = doctorRepository;
        this.personRepository = personRepository;
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
        System.out.println("Saving doctor with person: " + doctor.getPerson());
        return doctorRepository.save(doctor);
    }

    @Override
    public void deleteById(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public Optional<Person> findPersonByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public Page<Doctor> findWithFilters(DoctorFilterCriteria criteria, Pageable pageable) {
        Specification<Doctor> spec = DoctorSpecification.searchWithCriteria(criteria);
        return doctorRepository.findAll(spec, pageable);
    }
}
