package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Role;
import com.example.application.taskmanagement.service.IRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id) {
        return roleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody Role role) {
        if (!roleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        role.setRoleId(id);
        return ResponseEntity.ok(roleService.save(role));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> patch(@PathVariable Long id, @RequestBody Role role) {
        return roleService.findById(id)
                .map(existing -> {
                    if (role.getName() != null) existing.setName(role.getName());
                    return ResponseEntity.ok(roleService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!roleService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

