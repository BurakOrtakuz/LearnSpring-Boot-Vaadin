package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Medicine;
import java.util.List;
import java.util.Optional;

public interface IMedicineService {
    List<Medicine> findAll();
    Optional<Medicine> findById(Long id);
    Medicine save(Medicine medicine);
    void deleteById(Long id);
}

