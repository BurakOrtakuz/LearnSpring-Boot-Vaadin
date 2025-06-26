package com.example.application.taskmanagement.ui.view;

import com.example.application.base.ui.component.DoctorSideBar;
import com.example.application.base.ui.component.FooterBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/doctor", layout = DoctorSideBar.class)
@RolesAllowed("DOCTOR")
public class DoctorView extends VerticalLayout {
    public DoctorView() {

        add(new FooterBar());
    }
}
