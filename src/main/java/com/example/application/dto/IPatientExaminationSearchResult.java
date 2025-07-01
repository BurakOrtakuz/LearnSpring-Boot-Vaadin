package com.example.application.dto;

import java.time.LocalDateTime;

public interface IPatientExaminationSearchResult {
    LocalDateTime getDate();
    String getDoctorName();
    String getComplaint();
    int getPrescriptionId();
}
