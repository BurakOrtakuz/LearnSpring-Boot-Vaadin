package com.example.application.Jaster;

import com.example.application.domain.Medicine;
import com.example.application.domain.PrescriptionMedicine;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
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
        // Jasper dosyasını resources içinden yükle
        InputStream jasperStream = getClass().getResourceAsStream("/JasperTemplates/Test.jasper");
        if (jasperStream == null) {
            throw new IllegalArgumentException("Jasper dosyası bulunamadı: /JasperTemplates/Test.jasper");
        }
        List<com.example.application.Jaster.Medicine> jasperMedicines = prescriptionMedicines.stream()
                .map(pm -> new com.example.application.Jaster.Medicine(
                        pm.getMedicine().getName(),
                        pm.getMedicine().getDescription(),
                        pm.getDescription()
                ))
                .collect(Collectors.toList());
        List<Prescription> prescriptionList = List.of(prescription);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("LIST_DATA_SOURCE", new JRBeanCollectionDataSource(jasperMedicines));
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperStream,
                parameters,
                new JRBeanCollectionDataSource(prescriptionList)
        );
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
