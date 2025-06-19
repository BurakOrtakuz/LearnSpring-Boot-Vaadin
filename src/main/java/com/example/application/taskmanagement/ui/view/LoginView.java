package com.example.application.taskmanagement.ui.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/login/login")
public class LoginView extends VerticalLayout {
    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("/login");

        Div info = new Div();
        info.setText("Lütfen giriş yapınız.");
        info.getStyle().set("margin-bottom", "1em");

        add(info, loginForm);
    }
}
