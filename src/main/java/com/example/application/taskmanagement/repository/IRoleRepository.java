package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String user);
}
