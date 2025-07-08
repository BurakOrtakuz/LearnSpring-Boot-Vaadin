package com.example.application.base.ui.layout;

import com.example.application.base.ui.view.Doctor.CreatePrescriptionView;
import com.example.application.base.ui.view.Doctor.DoctorView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class PatientLayout extends AppLayout {
    public PatientLayout() {
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setClassName("doctor-drawer");
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.setWidthFull();
        drawerLayout.setHeightFull();

        H1 logoText = new H1("ASCHENTE");
        logoText.setClassName("doctor-logo");
        RouterLink logoLink = new RouterLink((String) null, DoctorView.class);
        logoLink.add(logoText);
        logoLink.setClassName("doctor-logo-link");

        RouterLink about = new RouterLink("Randevularım", CreatePrescriptionView.class);
        RouterLink appointments = new RouterLink("Randevu Al", AppointmentsView.class);
        about.setClassName("doctor-link");
        appointments.setClassName("doctor-link");

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add(logoLink, about, appointments);
        addToDrawer(drawerLayout);
    }
}
