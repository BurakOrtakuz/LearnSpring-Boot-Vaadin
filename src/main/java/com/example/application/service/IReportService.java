package com.example.application.service;

import com.example.application.domain.Report;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IReportService {

    /**
     * Zip dosyasından rapor yükler
     * @param zipFile Yüklenecek zip dosyası
     * @param selectedMainReport Zip içinden seçilen ana rapor dosya adı
     * @param name Rapor adı
     * @param description Rapor açıklaması
     * @return Oluşturulan Report entity
     */
    Report uploadReportFromZip(MultipartFile zipFile, String selectedMainReport, String name, String description) throws Exception;

    /**
     * Zip dosyasındaki jasper dosyalarını listeler
     * @param zipFile Zip dosyası
     * @return Jasper dosya adları listesi
     */
    List<String> getJasperFilesFromZip(MultipartFile zipFile) throws Exception;

    /**
     * Tüm raporları listeler
     * @return Report listesi
     */
    List<Report> getAllReports();

    /**
     * Report code ile rapor bulur
     * @param reportCode Rapor kodu
     * @return Report entity
     */
    Optional<Report> getReportByCode(String reportCode);

    /**
     * ID ile rapor bulur
     * @param id Report ID
     * @return Report entity
     */
    Optional<Report> getReportById(Long id);

    /**
     * Rapor siler
     * @param id Report ID
     */
    void deleteReport(Long id);

    /**
     * Rapor listesi (kod ve isim)
     * @return ReportCode ve Name çiftleri
     */
    List<Object[]> getReportCodesAndNames();

    /**
     * Benzersiz report code üretir
     * @return Yeni report code
     */
    String generateUniqueReportCode();
}
