package com.example.application.service;

import com.example.application.domain.Medicine;
import com.example.application.dto.IMedicineResult;

import java.util.List;
import java.util.Optional;

public interface IMedicineService {
    List<Medicine> findAll();
    Optional<Medicine> findById(Long id);
    Medicine save(Medicine medicine);
    void deleteById(Long id);
    List<IMedicineResult> findAllMedicines();
    Medicine saveIfNotExists(Medicine medicine);
}

