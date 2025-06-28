package com.example.application.controller;

import com.example.application.domain.Patient;
import com.example.application.service.IPatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
public class PatientController {
    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAll() {
        return patientService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        return patientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody Patient patient) {
        if (!patientService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        patient.setPatientId(id);
        return ResponseEntity.ok(patientService.save(patient));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Patient> patch(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.findById(id)
                .map(existing -> {
                    // Alan güncellemeleri eklenebilir
                    return ResponseEntity.ok(patientService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!patientService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
