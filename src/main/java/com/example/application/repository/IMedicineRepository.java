package com.example.application.repository;

import com.example.application.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {
}
