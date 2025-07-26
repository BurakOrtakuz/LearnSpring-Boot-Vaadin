package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.base.ui.view.PatientCard;
import com.example.application.domain.Person;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "/patient", layout = PatientMainLayout.class)
@RolesAllowed("PATIENT")
public class PatientView extends VerticalLayout {

    private final IPrescriptionMedicineService prescriptionMedicineService;
    public PatientView(IPrescriptionMedicineService prescriptionMedicineService) {
        this.prescriptionMedicineService = prescriptionMedicineService;
        setSpacing(true);
        setPadding(true);
        setWidthFull();

        // Sayfa başlığı
        add(new H2("Kişisel Bilgilerim"));

        // Bilgi panelleri
        Person person = getCurrentUser();
        if (person != null) {
            add(new PatientCard(getCurrentUser(), prescriptionMedicineService));
        }
    }

    private Person getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Person person) {
            return person;
        }
        return null;
    }
}
