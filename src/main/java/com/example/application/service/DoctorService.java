package com.example.application.service;

import com.example.application.domain.Doctor;
import com.example.application.domain.Person;
import com.example.application.repository.IDoctorRepository;
import com.example.application.repository.IPersonRepository;
import org.springframework.stereotype.Service;

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
}
