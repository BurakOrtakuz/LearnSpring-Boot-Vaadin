package com.example.application.Jaster;

import com.example.application.domain.Medicine;
import com.example.application.domain.PrescriptionMedicine;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CreatePdf {
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
}
