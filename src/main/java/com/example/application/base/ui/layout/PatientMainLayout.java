package com.example.application.base.ui.layout;

import com.example.application.auth.AuthenticationService;
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

        RouterLink about = new RouterLink("Randevularım", PatientView.class);
        RouterLink appointments = new RouterLink("Randevu Al", NewAppointmentsView.class);
        about.setClassName("doctor-link");
        appointments.setClassName("doctor-link");

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add( about, appointments);
        addToDrawer(drawerLayout);
    }
}
