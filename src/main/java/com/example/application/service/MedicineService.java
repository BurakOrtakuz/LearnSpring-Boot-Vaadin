package com.example.application.service;

import com.example.application.domain.Unit;
import com.example.application.repository.IUnitRepository;
import com.example.application.domain.Medicine;
import com.example.application.repository.IMedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService implements IMedicineService {
    private final IMedicineRepository medicineRepository;
    private final IUnitRepository unitRepository;

    @Autowired
    public MedicineService(IMedicineRepository medicineRepository, IUnitRepository unitRepository) {
        this.medicineRepository = medicineRepository;
        this.unitRepository = unitRepository;
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
        if (medicine.getUnit() != null && medicine.getUnit().getUnitId() != null) {
            Unit unit = unitRepository.findById(medicine.getUnit().getUnitId())
                    .orElseThrow(() -> new IllegalArgumentException("Unit not found"));
            medicine.setUnit(unit);
        } else {
            throw new IllegalArgumentException("Unit is required for Medicine");
        }
        return medicineRepository.save(medicine);
    }

    @Override
    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }
}
