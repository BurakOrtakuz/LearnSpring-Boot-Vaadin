package com.example.application.repository;

import com.example.application.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUnitRepository extends JpaRepository<Unit, Long> {
}

