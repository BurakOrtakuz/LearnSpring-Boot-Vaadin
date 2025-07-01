package com.example.application.dto;

import java.time.LocalDateTime;

public interface IDoctorExaminationSearchResult {
    LocalDateTime getDate();
    String getPatientName();
    String getComplaint();
    int getPrescriptionId();
    int getExaminationId();
}
