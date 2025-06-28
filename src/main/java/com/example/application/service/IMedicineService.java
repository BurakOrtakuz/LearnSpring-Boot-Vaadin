package com.example.application.service;

import com.example.application.domain.Medicine;
import java.util.List;
import java.util.Optional;

public interface IMedicineService {
    List<Medicine> findAll();
    Optional<Medicine> findById(Long id);
    Medicine save(Medicine medicine);
    void deleteById(Long id);
}

