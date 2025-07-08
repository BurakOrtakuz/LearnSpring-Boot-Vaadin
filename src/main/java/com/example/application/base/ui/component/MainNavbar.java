package com.example.application.base.ui.component;

import com.example.application.auth.AuthenticationRequest;
import com.example.application.auth.AuthenticationResponse;
import com.example.application.auth.AuthenticationService;
import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.example.application.base.ui.view.RegisterView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.Cookie;
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
        registerDialog = registerDialog();
        loginDialog = loginDialog();
        H1 logoText = new H1("ASCHENTE");
        logoText.addClassName("logo");
        RouterLink logo = new RouterLink((String) null, HomeView.class);
        logo.add(logoText);
        logo.setClassName("logo-link");

        RouterLink aboutLink = new RouterLink("Hakkında", AboutView.class);
        aboutLink.setClassName("about-link");

        RouterLink appointmentsLink = new RouterLink("Randevular", AppointmentsView.class);
        appointmentsLink.setClassName("appointments-link");

        Dialog loginDialog = loginDialog();
        Button loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        loginButton.addClassName("login-link");
        loginButton.addClickListener(e -> {
            loginDialog.open();
        });

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
                registerLink
            );
        }
        navbar.setClassName("navbar");
        navbar.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        navbar.setWidthFull();
        addToNavbar(navbar);
    }

    private Dialog loginDialog()
    {
        Dialog dialog = new Dialog();
        Text dialogTitle = new Text("Giriş Yap");

        VerticalLayout dialogContent = new VerticalLayout();
        dialogContent.setClassName("login-dialog-content");

        TextField usernameField = new TextField("Kullanıcı Adı");
        PasswordField passwordField = new PasswordField("Şifre");
        Button loginButton = new Button("Giriş Yap");
        Button closeButton = new Button("Kapat", e -> dialog.close());
        Anchor forgotPasswordLink = new Anchor();
        Span registerDiaglogOpen = new Span();

        usernameField.setClassName("login-dialog-username-field");
        usernameField.setRequired(true);
        usernameField.setAutoselect(true);
        usernameField.setTabIndex(1);
        usernameField.addKeyDownListener(Key.ENTER, event -> {
            if(!usernameField.isEmpty())
                passwordField.focus();
        });

        passwordField.setClassName("login-dialog-password-field");
        passwordField.setRequired(true);
        passwordField.setTabIndex(2);
        passwordField.addKeyDownListener(Key.ENTER, event -> {
            loginButton.click();
        });

        forgotPasswordLink.setHref("/forgot-password");
        forgotPasswordLink.setText("Şifremi Unuttum");
        forgotPasswordLink.setTabIndex(0);
        forgotPasswordLink.addClassName("forgot-password-link");

        registerDiaglogOpen.setText("Kayıt Ol");
        registerDiaglogOpen.setClassName("login-dialog-register-link");
        registerDiaglogOpen.getElement().addEventListener("click",e -> {
            dialog.setVisible(false);
            registerDialog.open();
        });

        loginButton.addClassName("login-dialog-login-button");
        loginButton.setTabIndex(3);

        loginButton.addClickListener(e -> {
            onLogin(usernameField.getValue(), passwordField.getValue());
        });

        closeButton.setTabIndex(4);
        closeButton.addClassName("login-dialog-close-button");
        closeButton.addClickListener(e ->  dialog.close());
        Shortcuts.addShortcutListener(closeButton, usernameField::focus,Key.TAB).listenOn(
                closeButton
        );

        dialog.getHeader().add(dialogTitle);
        dialog.getFooter().add(closeButton, loginButton);
        dialogContent.add(
                usernameField,
                passwordField,
                forgotPasswordLink,
                registerDiaglogOpen);
        dialog.add(dialogContent);
        return dialog;
    }

    private Dialog registerDialog()
    {
        Dialog dialog = new Dialog();

        VerticalLayout dialogContent = new VerticalLayout();
        Text dialogTitle = new Text("Kayıt Ol");

        dialogContent.setClassName("register-dialog-content");

        TextField firstNameField = new TextField();

        firstNameField.setClassName("register-dialog-firstname-field");
        firstNameField.setLabel("Ad");
        firstNameField.setRequired(true);





        dialogContent.add(dialogTitle);
        dialog.add(dialogContent);
        return dialog;
    }

    private void onLogin(String username, String password) {
        AuthenticationResponse response =
                authenticationService.authenticate(new AuthenticationRequest(username, password));

        if(response != null)
        {
            setCookie(response.getToken());
            redirection(response);
        }
    }
    private void redirection(AuthenticationResponse response)
    {
        switch (response.getRole()) {
            case "DOCTOR" -> getUI().ifPresent(ui -> ui.getPage().setLocation("doctor"));
            case "PATIENT" -> getUI().ifPresent(ui -> ui.getPage().setLocation("patient"));
            case "ADMIN" -> getUI().ifPresent(ui -> ui.getPage().setLocation("admin"));
            default -> getUI().ifPresent(ui -> ui.navigate("unauthorized"));
        }
    }

    private void setCookie(String token) {
        Cookie jwtCookie = new Cookie("jwtToken", token);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Sadece HTTPS için
        jwtCookie.setMaxAge(60 * 60 * 24); // 1 gün geçerli
        jwtCookie.setAttribute("SameSite", "Strict"); // CSRF için önemli

        VaadinSession.getCurrent().setAttribute("jwtToken", token);
        VaadinService.getCurrentResponse().addCookie(jwtCookie);
    }
}
