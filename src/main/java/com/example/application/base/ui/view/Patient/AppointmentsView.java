package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.component.MainNavbar;
import com.example.application.base.ui.layout.PatientMainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "appointments", layout = PatientMainLayout.class)
@RolesAllowed({"PATIENT"})
public class AppointmentsView extends VerticalLayout {
    public AppointmentsView() {
        Text text = new Text("Appointments");
        add(text);
        FooterBar footerBar = new FooterBar();
        add(footerBar);
    }
}

