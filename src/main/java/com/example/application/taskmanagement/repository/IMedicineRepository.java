package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {
}
