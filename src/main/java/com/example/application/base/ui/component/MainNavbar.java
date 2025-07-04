package com.example.application.base.ui.component;

import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.LoginView;
import com.example.application.base.ui.view.RegisterView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.domain.Person;

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

        // Kullanıcı giriş kontrolü
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HorizontalLayout navbar;
        if (principal instanceof Person person) {
            String fullName = person.getFirstName() + " " + person.getLastName();
            Div userDiv = new Div();
            userDiv.setText(fullName);
            userDiv.getStyle().set("color", "#fff").set("margin-right", "10px");
            RouterLink logoutLink = new RouterLink("Çıkış Yap", LoginView.class);
            logoutLink.addClassName("logout-link");
            logoutLink.getElement().addEventListener("click", e -> {
                getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
            });
            navbar = new HorizontalLayout(
                logo,
                spacer,
                aboutLink,
                appointmentsLink,
                userDiv,
                logoutLink
            );
        } else {
            navbar = new HorizontalLayout(
                logo,
                spacer,
                aboutLink,
                appointmentsLink,
                loginLink,
                registerLink
            );
        }
        navbar.setClassName("navbar");
        navbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        navbar.setWidthFull();
        addToNavbar(navbar);
    }
}
