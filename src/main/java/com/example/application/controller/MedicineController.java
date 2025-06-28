package com.example.application.controller;

import com.example.application.domain.Medicine;
import com.example.application.service.IMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
public class MedicineController {
    private final IMedicineService medicineService;

    public MedicineController(IMedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public List<Medicine> getAll() {
        return medicineService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getById(@PathVariable Long id) {
        return medicineService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medicine create(@RequestBody Medicine medicine) {
        return medicineService.save(medicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> update(@PathVariable Long id, @RequestBody Medicine medicine) {
        if (!medicineService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicine.setMedicineId(id);
        return ResponseEntity.ok(medicineService.save(medicine));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Medicine> patch(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.findById(id)
                .map(existing -> {
                    if (medicine.getName() != null) existing.setName(medicine.getName());
                    if (medicine.getUnit() != null) existing.setUnit(medicine.getUnit());
                    if (medicine.getDescription() != null) existing.setDescription(medicine.getDescription());
                    return ResponseEntity.ok(medicineService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!medicineService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
