package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Doctor;
import com.example.application.taskmanagement.service.IDoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final IDoctorService doctorService;

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAll() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getById(@PathVariable Long id) {
        return doctorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Doctor create(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @RequestBody Doctor doctor) {
        if (!doctorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctor.setDoctorId(id);
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Doctor> patch(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.findById(id)
                .map(existing -> {
                    if (doctor.getBranch() != null) existing.setBranch(doctor.getBranch());
                    // Diğer alanlar için de ekleme yapılabilir
                    return ResponseEntity.ok(doctorService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!doctorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

