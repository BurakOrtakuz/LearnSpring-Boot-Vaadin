package com.example.application.base.ui.layout;

import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.Doctor.CreatePrescriptionView;
import com.example.application.base.ui.view.Doctor.DoctorView;
import com.example.application.base.ui.view.LoginView;
import com.example.application.base.ui.view.RegisterView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class DoctorAppLayout extends AppLayout {
    public DoctorAppLayout() {
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

        RouterLink home = new RouterLink("Ana Sayfa", DoctorView.class);
        RouterLink about = new RouterLink("Reçete", CreatePrescriptionView.class);
        RouterLink appointments = new RouterLink("Randevular", AppointmentsView.class);
        RouterLink login = new RouterLink("Giriş Yap", LoginView.class);
        RouterLink register = new RouterLink("Kayıt Ol", RegisterView.class);
        home.setClassName("doctor-link");
        about.setClassName("doctor-link");
        appointments.setClassName("doctor-link");
        login.setClassName("doctor-link");
        register.setClassName("doctor-link");

        drawerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        drawerLayout.add(logoLink, home, about, appointments, login, register);
        addToDrawer(drawerLayout);
    }
}
