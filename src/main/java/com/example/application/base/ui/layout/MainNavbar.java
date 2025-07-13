package com.example.application.base.ui.layout;

import com.example.application.auth.*;
import com.example.application.base.ui.component.LoginDialog;
import com.example.application.base.ui.component.RegisterDialog;
import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.domain.Person;

@AnonymousAllowed
public class MainNavbar extends AppLayout {

    Dialog registerDialog;
    Dialog loginDialog;

    AuthenticationService authenticationService;
    public MainNavbar(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
        registerDialog = new RegisterDialog(authenticationService);
        loginDialog = new LoginDialog(authenticationService, () -> {
            registerDialog.open();
        });
        H1 logoText = new H1("ASCHENTE");
        logoText.addClassName("logo");
        RouterLink logo = new RouterLink((String) null, HomeView.class);
        logo.add(logoText);
        logo.setClassName("logo-link");

        RouterLink aboutLink = new RouterLink("Hakkında", AboutView.class);
        aboutLink.setClassName("about-link");

        RouterLink appointmentsLink = new RouterLink("Randevular", AppointmentsView.class);
        appointmentsLink.setClassName("appointments-link");

        Button loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        loginButton.addClassName("login-link");
        loginButton.addClickListener(e -> {
            loginDialog.open();
        });

        Button registerButton = new Button("Kayıt Ol");
        registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        registerButton.addClassName("register-link");
        registerButton.addClickListener(e -> {
            registerDialog.open();
        });

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
            RouterLink logoutLink = new RouterLink("Çıkış Yap", HomeView.class);
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
                loginButton,
                registerButton
            );
        }
        navbar.setClassName("navbar");
        navbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        navbar.setWidthFull();
        addToNavbar(navbar);
    }
}
