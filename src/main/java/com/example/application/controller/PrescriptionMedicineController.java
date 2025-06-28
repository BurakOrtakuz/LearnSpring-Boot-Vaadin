package com.example.application.controller;

import com.example.application.domain.PrescriptionMedicine;
import com.example.application.service.IPrescriptionMedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescription-medicines")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
public class PrescriptionMedicineController {
    private final IPrescriptionMedicineService prescriptionMedicineService;

    public PrescriptionMedicineController(IPrescriptionMedicineService prescriptionMedicineService) {
        this.prescriptionMedicineService = prescriptionMedicineService;
    }

    @GetMapping
    public List<PrescriptionMedicine> getAll() {
        return prescriptionMedicineService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionMedicine> getById(@PathVariable Long id) {
        return prescriptionMedicineService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PrescriptionMedicine create(@RequestBody PrescriptionMedicine prescriptionMedicine) {
        return prescriptionMedicineService.save(prescriptionMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionMedicine> update(@PathVariable Long id, @RequestBody PrescriptionMedicine prescriptionMedicine) {
        if (!prescriptionMedicineService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescriptionMedicine.setPrescriptionMedicineId(id);
        return ResponseEntity.ok(prescriptionMedicineService.save(prescriptionMedicine));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrescriptionMedicine> patch(@PathVariable Long id, @RequestBody PrescriptionMedicine prescriptionMedicine) {
        return prescriptionMedicineService.findById(id)
                .map(existing -> {
                    // Alan güncellemeleri eklenebilir
                    return ResponseEntity.ok(prescriptionMedicineService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!prescriptionMedicineService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescriptionMedicineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
