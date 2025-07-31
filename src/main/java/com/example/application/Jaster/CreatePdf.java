package com.example.application.Jaster;

import com.example.application.domain.Medicine;
import com.example.application.domain.PrescriptionMedicine;
import com.example.application.domain.Report;
import com.example.application.service.IReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CreatePdf {

    private final IReportService reportService;

    public CreatePdf(IReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * PDF oluşturur. Jasper dosyasını derlemeden, hazır .jasper dosyasını kullanır.
     * @param prescription Reçete bilgileri (Jaster.Prescription)
     * @param prescriptionMedicines domain.Medicine listesidir
     * @return PDF dosyasının byte dizisi
     * @throws Exception Hata durumunda fırlatılır
     */
    public byte[] generatePdf(Prescription prescription, List<PrescriptionMedicine> prescriptionMedicines) throws Exception {
        // Ana rapor dosyasını resources içinden yükle
        InputStream jasperStream = getClass().getResourceAsStream("/JasperTemplates/Test.jasper");
        if (jasperStream == null) {
            throw new IllegalArgumentException("Ana jasper dosyası bulunamadı: /JasperTemplates/Test.jasper");
        }

        // Medicine subreport dosyasını yükle
        InputStream medicineJasperStream = getClass().getResourceAsStream("/JasperTemplates/Medicine.jasper");
        if (medicineJasperStream == null) {
            throw new IllegalArgumentException("Medicine subreport dosyası bulunamadı: /JasperTemplates/Medicine.jasper");
        }

        // PrescriptionMedicine'lerden Jaster.Medicine'lere dönüştür
        List<com.example.application.Jaster.Medicine> jasperMedicines = prescriptionMedicines.stream()
                .map(pm -> new com.example.application.Jaster.Medicine(
                        pm.getMedicine().getName(),
                        pm.getMedicine().getDescription(),
                        pm.getDescription()
                ))
                .collect(Collectors.toList());

        // Ana rapor dosyasını yükle
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        // Medicine subreport'unu yükle
        JasperReport medicineReport = (JasperReport) JRLoader.loadObject(medicineJasperStream);

        // Prescription listesi oluştur
        List<Prescription> prescriptionList = List.of(prescription);

        // Parametreleri demo koduna uygun şekilde ayarla
        Map<String, Object> parameters = new HashMap<>();

        // MedicineReport parametresi - subreport'un kendisi (JasperReport tipi)
        parameters.put("MedicineReport", medicineReport);

        // MedicineData parametresi - Medicine.jrxml'de Map olarak bekleniyor
        Map<String, Object> medicineDataMap = new HashMap<>();
        medicineDataMap.put("MedicineData", new JRBeanCollectionDataSource(jasperMedicines));
        // Medicine.jrxml'deki tarih alanı için prescriptionDate parametresini de geçir
        medicineDataMap.put("prescriptionDate", prescription.getPrescriptionDate());

        parameters.put("MedicineData", medicineDataMap);

        // DataSource oluştur - Prescription için
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(prescriptionList);

        // Raporu doldur
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // PDF olarak byte dizisi halinde dön
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Database'den jasper template kullanarak PDF oluşturur
     * @param reportCode Database'deki rapor kodu
     * @param dataSource Ana rapor için veri kaynağı
     * @param parameters Rapor parametreleri
     * @return PDF dosyasının byte dizisi
     * @throws Exception Hata durumunda fırlatılır
     */
    public byte[] generatePdfFromDatabase(String reportCode, JRBeanCollectionDataSource dataSource, Map<String, Object> parameters) throws Exception {
        // Database'den raporu al
        Report report = reportService.getReportByCode(reportCode)
                .orElseThrow(() -> new IllegalArgumentException("Rapor bulunamadı: " + reportCode));

        // Ana rapor dosyasını yükle
        InputStream mainReportStream = new ByteArrayInputStream(report.getMainReportData());
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(mainReportStream);

        // Subreport varsa yükle ve parametrelere ekle
        if (report.getSubreportData() != null) {
            InputStream subreportStream = new ByteArrayInputStream(report.getSubreportData());
            JasperReport subreport = (JasperReport) JRLoader.loadObject(subreportStream);

            // Subreport'u parametrelere ekle (generic olarak)
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            // Generic subreport parametreleri
            parameters.put("SubReport", subreport);
            parameters.put("MedicineReport", subreport); // Backward compatibility için
        }

        // PDF oluştur
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    /**
     * Database'den prescription raporu oluşturur (özel method)
     * @param reportCode Database'deki rapor kodu
     * @param prescription Prescription verisi
     * @param prescriptionMedicines Medicine listesi
     * @return PDF byte dizisi
     * @throws Exception
     */
    public byte[] generatePrescriptionPdfFromDatabase(String reportCode, Prescription prescription, List<PrescriptionMedicine> prescriptionMedicines) throws Exception {
        // PrescriptionMedicine'lerden Jaster.Medicine'lere dönüştür
        List<com.example.application.Jaster.Medicine> jasperMedicines = prescriptionMedicines.stream()
                .map(pm -> new com.example.application.Jaster.Medicine(
                        pm.getMedicine().getName(),
                        pm.getMedicine().getDescription(),
                        pm.getDescription()
                ))
                .collect(Collectors.toList());

        // Prescription listesi oluştur
        List<Prescription> prescriptionList = List.of(prescription);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(prescriptionList);

        // Parametreleri hazırla (mevcut sistem ile uyumlu)
        Map<String, Object> parameters = new HashMap<>();

        // MedicineData parametresi - Medicine.jrxml'de Map olarak bekleniyor
        Map<String, Object> medicineDataMap = new HashMap<>();
        medicineDataMap.put("MedicineData", new JRBeanCollectionDataSource(jasperMedicines));
        medicineDataMap.put("prescriptionDate", prescription.getPrescriptionDate());

        parameters.put("MedicineData", medicineDataMap);

        return generatePdfFromDatabase(reportCode, dataSource, parameters);
    }

    /**
     * Generic subreport data oluşturma helper methodu
     * @param subreportDataList Subreport için veri listesi
     * @param additionalParams Ek parametreler
     * @return Subreport için hazırlanmış parameters Map'i
     */
    public Map<String, Object> createSubreportParameters(List<?> subreportDataList, Map<String, Object> additionalParams) {
        Map<String, Object> subreportParams = new HashMap<>();
        subreportParams.put("SubreportData", new JRBeanCollectionDataSource(subreportDataList));

        if (additionalParams != null) {
            subreportParams.putAll(additionalParams);
        }

        return subreportParams;
    }
}
