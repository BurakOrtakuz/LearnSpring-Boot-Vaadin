package com.example.application.service;

import com.example.application.domain.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IReportService {
    Report uploadReportFromZip(MultipartFile zipFile, String selectedMainReport, String name, String description) throws Exception;
    List<String> getJasperFilesFromZip(MultipartFile zipFile) throws Exception;
    List<Report> getAllReports();
    Optional<Report> getReportByCode(String reportCode);
    void deleteReport(Long id);
    String generateUniqueReportCode();
}
