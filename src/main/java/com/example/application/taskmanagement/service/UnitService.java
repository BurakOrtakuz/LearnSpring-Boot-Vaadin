package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Unit;
import com.example.application.taskmanagement.repository.IUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService implements IUnitService {
    private final IUnitRepository unitRepository;

    public UnitService(IUnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return unitRepository.findById(id);
    }

    @Override
    public Unit save(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public void deleteById(Long id) {
        unitRepository.deleteById(id);
    }
}

