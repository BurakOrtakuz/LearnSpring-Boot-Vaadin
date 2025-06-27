package com.example.application.Jaster;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class CreatePdf {
    /**
     * PDF oluşturur. Jasper dosyasını derlemeden, hazır .jasper dosyasını kullanır.
     * @param prescription Reçete bilgileri
     * @param medicines İlaç listesi
     * @return PDF dosyasının byte dizisi
     * @throws Exception Hata durumunda fırlatılır
     */
    public byte[] generatePdf(Prescription prescription, List<Medicine> medicines) throws Exception {
        // Jasper dosyasını resources içinden yükle
        InputStream jasperStream = getClass().getResourceAsStream("/JasperTemplates/Test.jasper");
        if (jasperStream == null) {
            throw new IllegalArgumentException("Jasper dosyası bulunamadı: /JasperTemplates/Test.jasper");
        }
        // Prescription'ı ana datasource olarak veriyoruz
        List<Prescription> prescriptionList = List.of(prescription);
        Map<String, Object> parameters = new HashMap<>();
        // İlaçlar için parametre olarak JRBeanCollectionDataSource veriyoruz
        parameters.put("LIST_DATA_SOURCE", new JRBeanCollectionDataSource(medicines));
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperStream,
                parameters,
                new JRBeanCollectionDataSource(prescriptionList)
        );
        // PDF olarak export et
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
