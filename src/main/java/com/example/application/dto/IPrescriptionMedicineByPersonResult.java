package com.example.application.dto;

import java.util.Date;

public interface IPrescriptionMedicineByPersonResult {
    String getMedicineName();
    String getUnitName();
    String getDescription();
    Date getTimestamp();
    Date getFinishTime();
}
