package com.example.application.dto;

import java.time.LocalDateTime;

public interface IExaminationSearchResult {
    LocalDateTime getDate();
    String getDoctorName();
    String getComplaint();
    int getPrescriptionId();
}
