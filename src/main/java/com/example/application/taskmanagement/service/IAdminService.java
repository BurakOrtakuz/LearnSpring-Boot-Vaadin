package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Admin;
import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Admin> findAll();
    Optional<Admin> findById(Long id);
    Admin save(Admin admin);
    void deleteById(Long id);
}

