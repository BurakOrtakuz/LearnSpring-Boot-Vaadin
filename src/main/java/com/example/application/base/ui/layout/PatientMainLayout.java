package com.example.application.base.ui.layout;

import com.example.application.base.ui.component.MainNavbar;
import com.example.application.base.ui.view.Doctor.CreatePrescriptionView;
import com.example.application.base.ui.view.Doctor.DoctorView;
import com.example.application.base.ui.view.LoginView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.Patient.NewAppointmentsView;
import com.example.application.base.ui.view.Patient.PatientView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class PatientMainLayout extends MainNavbar {
    public PatientMainLayout() {
        // Drawer (yan menü) ekle
        VerticalLayout drawerLayout = new VerticalLayout();
        drawerLayout.setClassName("doctor-drawer");
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.setWidthFull();
        drawerLayout.setHeightFull();

        RouterLink about = new RouterLink("Randevularım", PatientView.class);
        RouterLink appointments = new RouterLink("Randevu Al", NewAppointmentsView.class);
        RouterLink login = new RouterLink("Kayıtlı ilaçlarım", LoginView.class);
        about.setClassName("doctor-link");
        appointments.setClassName("doctor-link");
        login.setClassName("doctor-link");

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add( about, appointments, login);
        addToDrawer(drawerLayout);
    }
}
