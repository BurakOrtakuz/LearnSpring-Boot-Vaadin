package com.example.application.base.ui.component;

import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.taskmanagement.ui.view.AppointmentsView;
import com.example.application.taskmanagement.ui.view.LoginView;
import com.example.application.taskmanagement.ui.view.RegisterView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
public class MainNavbar extends AppLayout {

    public MainNavbar()
    {
        H1 logoText = new H1("ASCHENTE");
        logoText.addClassName("logo");
        RouterLink logo = new RouterLink((String) null, HomeView.class);
        logo.add(logoText);
        logo.setClassName("logo-link");

        RouterLink aboutLink = new RouterLink("Hakkında", AboutView.class);
        aboutLink.setClassName("about-link");

        RouterLink appointmentsLink = new RouterLink("Randevular", AppointmentsView.class);
        appointmentsLink.setClassName("appointments-link");

        RouterLink loginLink = new RouterLink("Giriş Yap", LoginView.class);
        loginLink.setClassName("login-link");

        RouterLink registerLink = new RouterLink("Kayıt Ol", RegisterView.class);
        registerLink.setClassName("register-link");

        Div spacer = new Div();
        spacer.getStyle().set("flex-grow", "1");

        HorizontalLayout navbar = new HorizontalLayout(
                logo,
                spacer,
                aboutLink,
                appointmentsLink,
                loginLink,
                registerLink
        );
        navbar.setClassName("navbar");
        navbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        navbar.setWidthFull();

        addToNavbar(navbar);
    }
}
