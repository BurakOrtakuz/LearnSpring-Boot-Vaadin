package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.layout.DoctorAppLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/doctor", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorView extends VerticalLayout {
    public DoctorView() {
        Text welcomeText = new Text("Doktor Paneli");
        add(welcomeText);
        // Buraya başka doktor paneli içeriği eklenebilir.
    }
}