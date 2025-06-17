package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Admin;
import com.example.application.taskmanagement.repository.IAdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {
    private final IAdminRepository adminRepository;

    public AdminService(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }
}
