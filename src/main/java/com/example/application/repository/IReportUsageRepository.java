package com.example.application.repository;

import com.example.application.domain.ReportUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IReportUsageRepository extends JpaRepository<ReportUsage, Long> {

    List<ReportUsage> findByOperationNameContainingIgnoreCase(String operationName);

    List<ReportUsage> findByReportReportId(Long reportId);

    List<ReportUsage> findAllByOrderByOperationNameAsc();

    Optional<ReportUsage> findByOperationName(String operationName);

    boolean existsByOperationName(String operationName);
}
