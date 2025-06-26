package com.example.application.taskmanagement.service;

import com.example.application.taskmanagement.domain.Examination;
import java.util.List;
import java.util.Optional;

public interface IExaminationService {
    List<Examination> findAll();
    Optional<Examination> findById(Long id);
    Examination save(Examination examination);
    void deleteById(Long id);
}

