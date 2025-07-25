package com.example.application.service;

import com.example.application.domain.Admin;
import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Admin> findAll();
    Optional<Admin> findById(Long id);
    Admin save(Admin admin);
    void deleteById(Long id);
}

