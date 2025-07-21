package com.example.application.Jaster;

import java.util.Date;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Prescription {
    private String name;
    private Date date;
    private String tcNo;
    private String drName;
    private String diagnosis;
    private JRBeanCollectionDataSource subreportDataSource;
    private Date prescriptionDate;
    private boolean doctorApproved;

    public Prescription() {
    }

    public Prescription(String name, Date date, String tcNo, String drName, String diagnosis) {
        this.name = name;
        this.date = date;
        this.tcNo = tcNo;
        this.drName = drName;
        this.diagnosis = diagnosis;
        this.prescriptionDate = date; // date ile aynı değeri kullan
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public JRBeanCollectionDataSource getSubreportDataSource() {
        return subreportDataSource;
    }

    public void setSubreportDataSource(JRBeanCollectionDataSource subreportDataSource) {
        this.subreportDataSource = subreportDataSource;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public boolean isDoctorApproved() {
        return doctorApproved;
    }

    public void setDoctorApproved(boolean doctorApproved) {
        this.doctorApproved = doctorApproved;
    }

}
