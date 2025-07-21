package com.example.application.Jaster;

public class Medicine {
    private String medicineName;
    private String medicineDescription;
    private String doctorDescription;

    public Medicine(String medicineName, String medicineDescription) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
    }

    public Medicine(String medicineName, String medicineDescription, String doctorDescription) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
        this.doctorDescription = doctorDescription;
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

    public String getDoctorDescription() {
        return doctorDescription;
    }

    public void setDoctorDescription(String doctorDescription) {
        this.doctorDescription = doctorDescription;
    }
}