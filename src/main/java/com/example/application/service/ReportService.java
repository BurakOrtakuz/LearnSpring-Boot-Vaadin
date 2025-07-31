package com.example.application.service;

import com.example.application.domain.Report;
import com.example.application.repository.IReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Transactional
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;

    public ReportService(IReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report uploadReportFromZip(MultipartFile zipFile, String selectedMainReport, String name, String description) throws Exception {
        if (zipFile.isEmpty()) {
            throw new IllegalArgumentException("Zip dosyası boş olamaz");
        }

        // Zip dosyasından jasper dosyalarını çıkar
        Map<String, byte[]> jasperFiles = extractJasperFilesFromZip(zipFile);

        if (jasperFiles.isEmpty()) {
            throw new IllegalArgumentException("Zip dosyasında .jasper dosyası bulunamadı");
        }

        if (!jasperFiles.containsKey(selectedMainReport)) {
            throw new IllegalArgumentException("Seçilen ana rapor dosyası bulunamadı: " + selectedMainReport);
        }

        // Ana rapor ve subreport'u ayır
        byte[] mainReportData = jasperFiles.get(selectedMainReport);
        byte[] subreportData = null;

        // Ana rapor dışındaki ilk jasper dosyasını subreport olarak kabul et
        for (Map.Entry<String, byte[]> entry : jasperFiles.entrySet()) {
            if (!entry.getKey().equals(selectedMainReport)) {
                subreportData = entry.getValue();
                break;
            }
        }

        // Benzersiz report code oluştur
        String reportCode = generateUniqueReportCode();

        // Report entity oluştur
        Report report = Report.builder()
                .reportCode(reportCode)
                .name(name)
                .description(description)
                .mainReportData(mainReportData)
                .subreportData(subreportData)
                .build();

        return reportRepository.save(report);
    }

    @Override
    public List<String> getJasperFilesFromZip(MultipartFile zipFile) throws Exception {
        List<String> jasperFiles = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".jasper")) {
                    jasperFiles.add(entry.getName());
                }
                zipInputStream.closeEntry();
            }
        }

        return jasperFiles;
    }

    private Map<String, byte[]> extractJasperFilesFromZip(MultipartFile zipFile) throws IOException {
        Map<String, byte[]> jasperFiles = new HashMap<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".jasper")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipInputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    jasperFiles.put(entry.getName(), baos.toByteArray());
                }
                zipInputStream.closeEntry();
            }
        }

        return jasperFiles;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> getAllReports() {
        return reportRepository.findAllOrderByUploadDateDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> getReportByCode(String reportCode) {
        return reportRepository.findByReportCode(reportCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> getReportCodesAndNames() {
        return reportRepository.findReportCodesAndNames();
    }

    @Override
    public String generateUniqueReportCode() {
        String code;
        do {
            // RPT_ + 8 karakterlik UUID
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
            code = "RPT_" + uuid;
        } while (reportRepository.existsByReportCode(code));

        return code;
    }
}
