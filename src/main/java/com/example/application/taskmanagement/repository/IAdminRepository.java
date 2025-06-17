package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
}
