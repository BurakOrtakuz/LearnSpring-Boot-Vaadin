package com.example.application.service;

import com.example.application.domain.Report;
import com.example.application.domain.ReportUsage;
import com.example.application.repository.IReportRepository;
import com.example.application.repository.IReportUsageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportUsageService implements IReportUsageService {

    private final IReportUsageRepository reportUsageRepository;
    private final IReportRepository reportRepository;

    public ReportUsageService(IReportUsageRepository reportUsageRepository, IReportRepository reportRepository) {
        this.reportUsageRepository = reportUsageRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public ReportUsage createReportUsage(String operationName, Long reportId) {
        if (reportUsageRepository.existsByOperationName(operationName)) {
            throw new IllegalArgumentException("Bu işlem adı zaten kullanılıyor: " + operationName);
        }

        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new IllegalArgumentException("Rapor bulunamadı: " + reportId);
        }

        ReportUsage reportUsage = ReportUsage.builder()
                .operationName(operationName)
                .report(report.get())
                .build();

        return reportUsageRepository.save(reportUsage);
    }

    @Override
    public ReportUsage updateReportUsage(Long usageId, String operationName, Long reportId) {
        Optional<ReportUsage> usageOpt = reportUsageRepository.findById(usageId);
        if (usageOpt.isEmpty()) {
            throw new IllegalArgumentException("Rapor kullanımı bulunamadı: " + usageId);
        }

        ReportUsage existingUsage = usageOpt.get();

        if (!existingUsage.getOperationName().equals(operationName)) {
            if (reportUsageRepository.existsByOperationName(operationName)) {
                throw new IllegalArgumentException("Bu işlem adı zaten kullanılıyor: " + operationName);
            }
        }

        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new IllegalArgumentException("Rapor bulunamadı: " + reportId);
        }

        existingUsage.setOperationName(operationName);
        existingUsage.setReport(report.get());

        return reportUsageRepository.save(existingUsage);
    }

    @Override
    public void deleteReportUsage(Long usageId) {
        reportUsageRepository.deleteById(usageId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportUsage> getReportUsageById(Long usageId) {
        return reportUsageRepository.findById(usageId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportUsage> getAllReportUsages() {
        return reportUsageRepository.findAllByOrderByOperationNameAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportUsage> searchByOperationName(String operationName) {
        return reportUsageRepository.findByOperationNameContainingIgnoreCase(operationName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportUsage> getUsagesByReportId(Long reportId) {
        return reportUsageRepository.findByReportReportId(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportUsage> getReportByOperationName(String operationName) {
        return reportUsageRepository.findByOperationName(operationName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isOperationNameExists(String operationName) {
        return reportUsageRepository.existsByOperationName(operationName);
    }
}
