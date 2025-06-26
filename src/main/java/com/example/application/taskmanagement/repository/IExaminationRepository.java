package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
}

