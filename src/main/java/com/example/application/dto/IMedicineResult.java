package com.example.application.dto;

import com.example.application.domain.Unit;

public interface IMedicineResult {
    Long getMedicineId();
    String getMedicineName();
    Unit getUnit();
    String getDescription();
}
