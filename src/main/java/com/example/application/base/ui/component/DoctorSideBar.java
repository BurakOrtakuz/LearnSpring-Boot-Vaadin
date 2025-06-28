package com.example.application.base.ui.component;

import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.Doctor.CreatePrescriptionView;
import com.example.application.base.ui.view.Doctor.DoctorView;
import com.example.application.base.ui.view.LoginView;
import com.example.application.base.ui.view.RegisterView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class DoctorSideBar extends VerticalLayout implements RouterLayout {

    public DoctorSideBar() {
        setWidth("220px");
        setHeightFull();
        setSpacing(false);
        setPadding(false);
        getStyle()
            .set("background", "#4635B1")
            .set("padding", "24px 0");

        H1 logoText = new H1("ASCHENTE");
        logoText.getStyle()
            .set("color", "#fff")
            .set("font-size", "1.8em")
            .set("text-align", "center");

        RouterLink logoLink = new RouterLink((String) null, DoctorView.class);
        logoLink.add(logoText);
        logoLink.getStyle()
            .set("display", "block")
            .set("text-decoration", "none");

        RouterLink home = new RouterLink("Ana Sayfa", HomeView.class);
        RouterLink about = new RouterLink("Reçete", CreatePrescriptionView.class);
        RouterLink appointments = new RouterLink("Randevular", AppointmentsView.class);
        RouterLink login = new RouterLink("Giriş Yap", LoginView.class);
        RouterLink register = new RouterLink("Kayıt Ol", RegisterView.class);
        logoText.addClassName("doctor-logo");
        logoLink.addClassName("doctor-logo-link");

        home.addClassName("doctor-link");
        about.addClassName("doctor-link");
        appointments.addClassName("doctor-link");
        login.addClassName("doctor-link");
        register.addClassName("doctor-link");

        setAlignItems(Alignment.CENTER);
        add(logoLink, home, about, appointments, login, register);
    }
}