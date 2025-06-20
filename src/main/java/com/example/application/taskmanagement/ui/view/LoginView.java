package com.example.application.taskmanagement.ui.view;

import com.example.application.taskmanagement.auth.AuthenticationRequest;
import com.example.application.taskmanagement.auth.AuthenticationResponse;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.web.client.RestTemplate;

@Route(value = "/login/login", autoLayout = false)
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();

        loginForm.addLoginListener(this::onLogin);

        Div info = new Div();
        info.setText("Lütfen giriş yapınız.");
        info.getStyle().set("margin-bottom", "1em");

        add(info, loginForm);
    }

    private void onLogin(LoginEvent event) {
        RestTemplate restTemplate = new RestTemplate();
        AuthenticationRequest request = new AuthenticationRequest(event.getUsername(), event.getPassword());

        try {
            AuthenticationResponse response = restTemplate.postForObject(
                    "http://localhost:8080/api/v1/auth/authenticate",
                    request,
                    AuthenticationResponse.class
            );

            if (response != null && response.getToken() != null) {
                // Token'ı oturumda sakla
                VaadinSession.getCurrent().setAttribute("jwtToken", response.getToken());

                // Role göre yönlendirme
                switch (response.getRole()) {
                    case "DOCTOR" -> getUI().ifPresent(ui -> ui.navigate("doctor-panel"));
                    case "PATIENT" -> getUI().ifPresent(ui -> ui.navigate("patient-panel"));
                    case "ADMIN" -> getUI().ifPresent(ui -> ui.navigate("admin-panel"));
                    default -> getUI().ifPresent(ui -> ui.navigate("unauthorized"));
                }
            } else {
                Notification.show("Giriş başarısız.");
            }
        } catch (Exception e) {
            Notification.show("Sunucuya ulaşılamadı veya hatalı bilgi.");
        }
    }

}
