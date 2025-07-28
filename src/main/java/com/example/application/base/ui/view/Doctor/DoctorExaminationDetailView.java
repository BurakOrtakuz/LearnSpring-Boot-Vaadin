package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.component.ExaminationDetailComponent;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.service.ExaminationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "doctor/examination-detail", layout = DoctorAppLayout.class)
@RolesAllowed({"DOCTOR"})
public class DoctorExaminationDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private final ExaminationDetailComponent examinationDetailComponent;

    public DoctorExaminationDetailView(ExaminationService examinationService) {
        this.examinationDetailComponent = new ExaminationDetailComponent(examinationService);
        this.examinationDetailComponent.setBackNavigationTarget("doctor/appointments");

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
