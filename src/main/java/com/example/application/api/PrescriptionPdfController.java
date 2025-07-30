package com.example.application.api;

import com.example.application.Jaster.CreatePdf;
import com.example.application.domain.Examination;
import com.example.application.domain.Medicine;
import com.example.application.domain.Prescription;
import com.example.application.domain.PrescriptionMedicine;
import com.example.application.service.IPrescriptionMedicineService;
import com.example.application.service.IPrescriptionService;
import com.example.application.service.PrescriptionService;
import jakarta.annotation.security.PermitAll;
import net.sf.jasperreports.repo.JasperDesignReportResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/prescription")
@PermitAll
public class PrescriptionPdfController {
    private final IPrescriptionService prescriptionService;
    private final IPrescriptionMedicineService prescriptionMedicineService;
    private final CreatePdf createPdf;

    public PrescriptionPdfController(PrescriptionService prescriptionService, IPrescriptionMedicineService prescriptionMedicineService, CreatePdf createPdf) {
        this.prescriptionService = prescriptionService;
        this.prescriptionMedicineService = prescriptionMedicineService;
        this.createPdf = createPdf;
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> getPrescriptionPdf(@PathVariable Long id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if(prescription.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Prescription prescriptionObj = prescription.get();
        Examination examination = prescriptionObj.getExamination();
        try{

        com.example.application.Jaster.Prescription jasperPrescription = new com.example.application.Jaster.Prescription(
                examination.getPatient().getPerson().getFirstName() + " " + examination.getPatient().getPerson().getLastName(),
                examination.getDate(),
                examination.getPatient().getPerson().getUsername(),
                examination.getDoctor().getPerson().getFirstName() + " " + examination.getDoctor().getPerson().getLastName(),
                prescriptionObj.getDoctorNote()
        );
        List<PrescriptionMedicine> medicines = prescriptionMedicineService.getMedicinesByPrescriptionId(id);
        byte[] pdf = createPdf.generatePdf(jasperPrescription,medicines);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=prescription-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(("Error generating PDF: " + e.getMessage()).getBytes());
        }
    }
}

