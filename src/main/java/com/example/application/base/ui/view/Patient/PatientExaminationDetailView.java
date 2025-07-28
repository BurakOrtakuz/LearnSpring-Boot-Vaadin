package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.component.ExaminationDetailComponent;
import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.service.ExaminationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "patient/examination-detail", layout = PatientMainLayout.class)
@RolesAllowed({"PATIENT"})
public class PatientExaminationDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final ExaminationDetailComponent examinationDetailComponent;

    public PatientExaminationDetailView(ExaminationService examinationService) {
        this.examinationDetailComponent = new ExaminationDetailComponent(examinationService);
        this.examinationDetailComponent.setBackNavigationTarget("appointments");

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        add(examinationDetailComponent);
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        examinationDetailComponent.loadExaminationDetails(parameter);
    }
}
