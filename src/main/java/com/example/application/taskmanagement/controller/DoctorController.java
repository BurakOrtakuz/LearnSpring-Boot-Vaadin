package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Doctor;
import com.example.application.taskmanagement.service.IDoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
public class DoctorController {
    private final IDoctorService doctorService;

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public List<Doctor> getAll() {
        return doctorService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    public ResponseEntity<Doctor> getById(@PathVariable Long id) {
        return doctorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<?> create(@RequestBody Doctor doctor) {
        // Null kontrolü eklendi
        if (doctor.getPerson() != null && doctor.getPerson().getUsername() != null) {
            if (doctorService.findPersonByUsername(doctor.getPerson().getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Bu kullanıcı adıyla bir kişi zaten mevcut.");
            }
        }
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<Doctor> update(@PathVariable Long id, @RequestBody Doctor doctor) {
        if (!doctorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctor.setDoctorId(id);
        return ResponseEntity.ok(doctorService.save(doctor));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<Doctor> patch(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.findById(id)
                .map(existing -> {
                    if (doctor.getBranch() != null) existing.setBranch(doctor.getBranch());
                    if (doctor.getPerson() != null) {
                        if (existing.getPerson() == null) {
                            existing.setPerson(doctor.getPerson());
                        } else {
                            if (doctor.getPerson().getFirstName() != null)
                                existing.getPerson().setFirstName(doctor.getPerson().getFirstName());
                            if (doctor.getPerson().getLastName() != null)
                                existing.getPerson().setLastName(doctor.getPerson().getLastName());
                            if (doctor.getPerson().getUsername() != null)
                                existing.getPerson().setUsername(doctor.getPerson().getUsername());
                            if (doctor.getPerson().getPassword() != null)
                                existing.getPerson().setPassword(doctor.getPerson().getPassword());
                            if (doctor.getPerson().getRole() != null)
                                existing.getPerson().setRole(doctor.getPerson().getRole());
                            if (doctor.getPerson().getBirthDate() != null)
                                existing.getPerson().setBirthDate(doctor.getPerson().getBirthDate());
                            if (doctor.getPerson().getGender() != null)
                                existing.getPerson().setGender(doctor.getPerson().getGender());
                            if (doctor.getPerson().getPhoneNumber() != null)
                                existing.getPerson().setPhoneNumber(doctor.getPerson().getPhoneNumber());
                            if (doctor.getPerson().getAddress() != null)
                                existing.getPerson().setAddress(doctor.getPerson().getAddress());
                        }
                    }
                    return ResponseEntity.ok(doctorService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!doctorService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        doctorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
