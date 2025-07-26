package com.example.application.base.ui.layout;

import com.example.application.auth.AuthenticationService;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.Patient.NewAppointmentsView;
import com.example.application.base.ui.view.Patient.PatientView;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class PatientMainLayout extends MainNavbar {
    public PatientMainLayout(AuthenticationService authenticationService) {
        super(authenticationService);
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setClassName("doctor-drawer");
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.setWidthFull();
        drawerLayout.setHeightFull();

        RouterLink details = new RouterLink("Bilgilerim", PatientView.class);
        RouterLink about = new RouterLink("Randevularım", AppointmentsView.class);
        RouterLink appointments = new RouterLink("Randevu Al", NewAppointmentsView.class);
        details.setClassName("doctor-link");
        about.setClassName("doctor-link");
        appointments.setClassName("doctor-link");

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add(details, about, appointments);
        addToDrawer(drawerLayout);
    }
}
