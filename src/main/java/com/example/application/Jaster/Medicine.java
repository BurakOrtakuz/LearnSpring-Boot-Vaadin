package com.example.application.Jaster;

public class Medicine {
    private String medicineName;
    private String medicineDescription;

    public Medicine(String medicineName, String medicineDescription) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDescription() {
        return medicineDescription;
    }

    public void setMedicineDescription(String medicineDescription) {
        this.medicineDescription = medicineDescription;
    }
}