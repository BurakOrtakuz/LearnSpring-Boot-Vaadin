package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Admin;
import com.example.application.taskmanagement.service.IAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<Admin> getAll() {
        return adminService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getById(@PathVariable Long id) {
        return adminService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Admin create(@RequestBody Admin admin) {
        return adminService.save(admin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin admin) {
        if (!adminService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        admin.setAdminId(id);
        return ResponseEntity.ok(adminService.save(admin));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Admin> patch(@PathVariable Long id, @RequestBody Admin admin) {
        return adminService.findById(id)
                .map(existing -> {
                    if (admin.getRank() != null) existing.setRank(admin.getRank());
                    // Diğer alanlar için de ekleme yapılabilir
                    return ResponseEntity.ok(adminService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!adminService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

