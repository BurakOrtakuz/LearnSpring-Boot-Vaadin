package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Role;
import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    void deleteById(Long id);
}

