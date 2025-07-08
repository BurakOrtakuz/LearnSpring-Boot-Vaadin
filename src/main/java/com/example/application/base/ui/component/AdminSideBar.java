package com.example.application.base.ui.component;

import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.AdminView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.RegisterView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;

public class AdminSideBar extends VerticalLayout implements RouterLayout {

    public AdminSideBar() {
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

        RouterLink logoLink = new RouterLink((String) null, AdminView.class);
        logoLink.add(logoText);
        logoLink.getStyle()
            .set("display", "block")
            .set("text-decoration", "none");

        RouterLink home = new RouterLink("Ana Sayfa", HomeView.class);
        RouterLink about = new RouterLink("Hakkında", AboutView.class);
        RouterLink appointments = new RouterLink("Randevular", AppointmentsView.class);
        logoText.addClassName("admin-logo");
        logoLink.addClassName("admin-logo-link");

        home.addClassName("admin-link");
        about.addClassName("admin-link");
        appointments.addClassName("admin-link");

        setAlignItems(Alignment.CENTER);
        add(logoLink, home, about, appointments);
    }
}
