package com.example.application.service;

import com.example.application.domain.Unit;
import com.example.application.dto.IMedicineResult;
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
        Unit unit = unitRepository.findById(medicine.getUnit().getUnitId())
                .orElseThrow(() -> new IllegalArgumentException("Unit not found"));
        medicine.setUnit(unit);
        return medicineRepository.save(medicine);
    }

    @Override
    public Medicine saveIfNotExists(Medicine medicine) {
        Optional<Medicine> existingMedicine = medicineRepository.findByName(medicine.getName());
        existingMedicine.ifPresent(value -> medicine.setMedicineId(value.getMedicineId()));
        return save(medicine);
    }

    @Override
    public List<IMedicineResult> filterMedicine(String name, String unit, String description) {
        return medicineRepository.filterMedicine(name, unit, description);
    }

    @Override
    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }

    @Override
    public List<IMedicineResult> findAllMedicines() {
        return medicineRepository.findAllMedicines();
    }
}
