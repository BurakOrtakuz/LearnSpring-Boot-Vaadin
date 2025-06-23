package com.example.application.taskmanagement.ui.view;

import com.example.application.taskmanagement.auth.AuthenticationRequest;
import com.example.application.taskmanagement.auth.AuthenticationResponse;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import org.springframework.web.client.RestTemplate;

@Route(value = "/login/login", autoLayout = false)
@PermitAll
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

        Button registerButton = new Button("Kayıt Ol", event ->
                getUI().ifPresent(ui -> ui.navigate("/login/register"))
        );
        registerButton.getStyle().set("margin-top", "1em");

        add(info, loginForm, registerButton);
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
            System.out.println("Response: " + response);
            if (response != null && response.getToken() != null) {
                setCookie(response.getToken());
                // Role göre yönlendirme
                redirection(response);

            } else {
                Notification.show("Giriş başarısız.");
            }
        } catch (Exception e) {
            Notification.show("Sunucuya ulaşılamadı veya hatalı bilgi.");
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
