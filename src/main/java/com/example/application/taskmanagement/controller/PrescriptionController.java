package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Prescription;
import com.example.application.taskmanagement.service.IPrescriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {
    private final IPrescriptionService prescriptionService;

    public PrescriptionController(IPrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping
    public List<Prescription> getAll() {
        return prescriptionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {
        return prescriptionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prescription create(@RequestBody Prescription prescription) {
        return prescriptionService.save(prescription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> update(@PathVariable Long id, @RequestBody Prescription prescription) {
        if (!prescriptionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescription.setPrescriptionId(id);
        return ResponseEntity.ok(prescriptionService.save(prescription));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Prescription> patch(@PathVariable Long id, @RequestBody Prescription prescription) {
        return prescriptionService.findById(id)
                .map(existing -> {
                    // Alan güncellemeleri eklenebilir
                    return ResponseEntity.ok(prescriptionService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!prescriptionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        prescriptionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

