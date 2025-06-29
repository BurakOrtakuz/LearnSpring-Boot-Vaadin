package com.example.application.api;

import com.example.application.service.PrescriptionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionPdfController {
    private final PrescriptionService prescriptionService;

    public PrescriptionPdfController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> getPrescriptionPdf(@PathVariable Long id) {
        byte[] pdf = prescriptionService.getDocument(id);
        if (pdf == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=prescription-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

