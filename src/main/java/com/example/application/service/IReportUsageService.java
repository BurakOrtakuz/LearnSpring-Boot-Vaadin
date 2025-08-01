package com.example.application.service;

import com.example.application.domain.ReportUsage;
import java.util.List;
import java.util.Optional;

public interface IReportUsageService {

    ReportUsage createReportUsage(String operationName, Long reportId);
    ReportUsage updateReportUsage(Long usageId, String operationName, Long reportId);
    void deleteReportUsage(Long usageId);
    Optional<ReportUsage> getReportUsageById(Long usageId);
    List<ReportUsage> getAllReportUsages();

    List<ReportUsage> searchByOperationName(String operationName);
    List<ReportUsage> getUsagesByReportId(Long reportId);

    Optional<ReportUsage> getReportByOperationName(String operationName);
    boolean isOperationNameExists(String operationName);
}
