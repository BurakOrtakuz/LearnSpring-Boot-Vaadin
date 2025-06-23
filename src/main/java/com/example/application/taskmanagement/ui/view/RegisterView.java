package com.example.application.taskmanagement.ui.view;

import com.example.application.taskmanagement.auth.AuthenticationService;
import com.example.application.taskmanagement.auth.RegisterRequest;
import com.example.application.taskmanagement.auth.RegisterResponse;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value ="/login/register", autoLayout = false)
@PermitAll
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final AuthenticationService authenticationService;

    @Autowired
    public RegisterView(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;

        TextField firstName = new TextField("Ad");
        TextField lastName = new TextField("Soyad");
        TextField username = new TextField("Kullanıcı Adı");
        EmailField email = new EmailField("E-posta");
        PasswordField password = new PasswordField("Şifre");

        Button registerButton = new Button("Kayıt Ol", event -> {
            RegisterRequest request = RegisterRequest.builder()
                    .firstName(firstName.getValue())
                    .lastName(lastName.getValue())
                    .username(username.getValue())
                    .email(email.getValue())
                    .password(password.getValue())
                    .build();

            try {
                RegisterResponse response = authenticationService.register(request);
                UI.getCurrent().navigate("/login/login");
            } catch (Exception e) {
                Notification.show("Kayıt başarısız: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, username, email, password, registerButton);

        add(formLayout);
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}