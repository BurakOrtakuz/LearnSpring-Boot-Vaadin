package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
}
