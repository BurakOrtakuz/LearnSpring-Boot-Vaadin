package com.example.application.base.ui.component;

import com.example.application.auth.AuthenticationRequest;
import com.example.application.auth.AuthenticationResponse;
import com.example.application.auth.AuthenticationService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.Cookie;

public class LoginDialog extends Dialog {
    AuthenticationService authenticationService;
    public LoginDialog(AuthenticationService authenticationService, Runnable registerDialogOpenExecute) {
        super();
        this.authenticationService = authenticationService;
        Span dialogTitle = new Span("Giriş Yap");

        VerticalLayout dialogContent = new VerticalLayout();

        TextField usernameField = new TextField("Kullanıcı Adı");
        PasswordField passwordField = new PasswordField("Şifre");
        Button loginButton = new Button("Giriş Yap");
        Button closeButton = new Button("Kapat", e -> this.close());
        Anchor forgotPasswordLink = new Anchor();
        Span registerDiaglogOpen = new Span();

        dialogTitle.setClassName("login-dialog-title");

        dialogContent.setClassName("login-dialog-content");

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
            this.close();
            registerDialogOpenExecute.run();
        });

        loginButton.addClassName("login-dialog-login-button");
        loginButton.setTabIndex(3);

        loginButton.addClickListener(e -> {
            if(!onLogin(usernameField.getValue(), passwordField.getValue()))
            {
                usernameField.setInvalid(true);
                passwordField.setInvalid(true);
                usernameField.setErrorMessage("Kullanıcı adı veya şifre hatalı");
                passwordField.clear();
            }
            else
            {
                this.close();
            }
        });

        closeButton.setTabIndex(4);
        closeButton.addClassName("login-dialog-close-button");
        closeButton.addClickListener(e ->  this.close());
        Shortcuts.addShortcutListener(closeButton, usernameField::focus,Key.TAB).listenOn(
                closeButton
        );

        this.getHeader().add(dialogTitle);
        this.getFooter().add(closeButton, loginButton);
        dialogContent.add(
                usernameField,
                passwordField,
                forgotPasswordLink,
                registerDiaglogOpen);
        this.add(dialogContent);
    }
    private boolean onLogin(String username, String password) {
        try
        {
            AuthenticationResponse response =
                    authenticationService.authenticate(new AuthenticationRequest(username, password));
            setCookie(response.getToken());
            redirection(response);
            return true;
        }catch (Exception e)
        {
            return false;
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
        jwtCookie.setMaxAge(60 * 60*60 * 24); // 1 gün geçerli
        jwtCookie.setAttribute("SameSite", "Strict"); // CSRF için önemli

        VaadinSession.getCurrent().setAttribute("jwtToken", token);
        VaadinService.getCurrentResponse().addCookie(jwtCookie);
    }
}
