package com.example.application.service;

import com.example.application.domain.Examination;
import com.example.application.repository.IExaminationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExaminationService implements IExaminationService {
    private final IExaminationRepository examinationRepository;

    public ExaminationService(IExaminationRepository examinationRepository) {
        this.examinationRepository = examinationRepository;
    }

    @Override
    public List<Examination> findAll() {
        return examinationRepository.findAll();
    }

    @Override
    public Optional<Examination> findById(Long id) {
        return examinationRepository.findById(id);
    }

    @Override
    public Examination save(Examination examination) {
        return examinationRepository.save(examination);
    }

    @Override
    public void deleteById(Long id) {
        examinationRepository.deleteById(id);
    }
}

