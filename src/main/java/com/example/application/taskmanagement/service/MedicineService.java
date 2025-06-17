package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Medicine;
import com.example.application.taskmanagement.repository.IMedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService implements IMedicineService {
    private final IMedicineRepository medicineRepository;

    public MedicineService(IMedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @Override
    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    @Override
    public Optional<Medicine> findById(Long id) {
        return medicineRepository.findById(id);
    }

    @Override
    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }
}
