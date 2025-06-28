package com.example.application.repository;

import com.example.application.domain.Examination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
}

