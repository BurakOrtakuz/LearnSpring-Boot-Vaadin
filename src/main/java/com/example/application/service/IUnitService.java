package com.example.application.service;

import com.example.application.domain.Unit;
import java.util.List;
import java.util.Optional;

public interface IUnitService {
    List<Unit> findAll();
    Optional<Unit> findById(Long id);
    Unit save(Unit unit);
    void deleteById(Long id);
}

