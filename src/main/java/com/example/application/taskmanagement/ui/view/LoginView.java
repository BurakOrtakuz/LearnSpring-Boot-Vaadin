package com.example.application.taskmanagement.ui.view;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.component.MainNavbar;
import com.example.application.taskmanagement.auth.AuthenticationRequest;
import com.example.application.taskmanagement.auth.AuthenticationResponse;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.Cookie;
import org.springframework.web.client.RestTemplate;

@Route(value = "/login/login", layout = MainNavbar.class)
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    private final LoginForm loginForm;
    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm = new LoginForm();
        loginForm.addLoginListener(this::onLogin);
        LoginI18n loginI18n = LoginI18n.createDefault();
        loginI18n.getForm().setUsername("Kullanıcı adı");
        loginI18n.getForm().setPassword("Şifre");
        loginI18n.getForm().setTitle("Giriş Yap");
        loginI18n.getForm().setSubmit("Giriş");
        loginI18n.getForm().setForgotPassword("Şifremi Unuttum");
        loginForm.getStyle().set("width", "420px").set("padding", "32px");
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle("Hatalı Giriş");
        errorMessage.setMessage("Kullanıcı adı veya şifre hatalı.");
        loginI18n.setErrorMessage(errorMessage);
        loginI18n.setHeader(new LoginI18n.Header());
        loginI18n.getHeader().setTitle("Giriş Yap");
        loginForm.setI18n(loginI18n);

        loginForm.getStyle().set("width", "350px");

        FooterBar footerBar = new FooterBar();
        add(loginForm, footerBar);
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
                setCookie(response.getToken());
                redirection(response);
            } else {
                loginForm.setError(true); // Hatalı girişte formu hata durumuna al
            }
        } catch (Exception e) {
            loginForm.setError(true); // Sunucu hatasında da hata durumuna al
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
