package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.base.ui.view.PatientCard;
import com.example.application.domain.Person;
import com.example.application.service.IPatientService;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/doctor/patient-details", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorPatientDetails extends VerticalLayout implements HasUrlParameter<Long> {

    private final IPatientService patientService;
    private final IPrescriptionMedicineService prescriptionMedicineService;
    public DoctorPatientDetails(IPatientService patientService, IPrescriptionMedicineService prescriptionMedicineService) {
        this.patientService = patientService;
        this.prescriptionMedicineService = prescriptionMedicineService;

        setSpacing(true);
        setPadding(true);
        setWidthFull();
    }

    @Override
    public void setParameter(BeforeEvent event, Long patientId) {
        removeAll();

        if (patientId == null) {
            add(new Span("Hasta ID'si belirtilmemiş."));
            return;
        }

        // Hastayı veritabanından çek
        Person patient = patientService.findById(patientId).orElse(null).getPerson();

        if (patient == null) {
            add(new Span("Belirtilen ID'ye sahip hasta bulunamadı: " + patientId));
            return;
        }

        // Sayfa başlığı
        String pageTitle = patient.getFirstName() + " " + patient.getLastName() + " - Hasta Detayları";
        add(new H2(pageTitle));

        // PatientCard component'ini kullan
        PatientCard patientCard = new PatientCard(patient, prescriptionMedicineService);
        add(patientCard);
    }
}
