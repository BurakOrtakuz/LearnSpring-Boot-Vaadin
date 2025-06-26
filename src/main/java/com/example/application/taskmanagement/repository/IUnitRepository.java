package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUnitRepository extends JpaRepository<Unit, Long> {
}

