package com.example.application.taskmanagement.ui.view;

import com.example.application.base.ui.view.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.Cookie;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/admin", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

    public AdminView() {
        TextField firstName = new TextField("Ad");
        TextField lastName = new TextField("Soyad");
        TextField username = new TextField("Kullanıcı Adı");
        TextField email = new TextField("E-posta");
        PasswordField password = new PasswordField("Şifre");
        ComboBox<String> roleComboBox = new ComboBox<>("Rol");

        // Rolleri backend'den çek
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/api/v1/roles";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String token = null;
            Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwtToken".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }
            if (token != null) {
                headers.setBearerAuth(token);
            }
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    List.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Her rol nesnesi bir Map olarak gelir, adı al
                List<?> roles = response.getBody();
                List<String> roleNames = roles.stream()
                        .map(obj -> ((java.util.Map<?, ?>) obj).get("name").toString())
                        .collect(Collectors.toList());
                roleComboBox.setItems(roleNames);
            }
        } catch (Exception e) {
            Notification.show("Roller yüklenemedi: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
        }

        Button addButton = new Button("Kullanıcı Ekle", event -> {
            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "/api/v1/auth/adminRegister";

                String body = String.format(
                        "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"roleName\":\"%s\"}",
                        firstName.getValue(), lastName.getValue(), username.getValue(), email.getValue(), password.getValue(), roleComboBox.getValue()
                );

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                String token = (String) VaadinSession.getCurrent().getAttribute("token");
                if (token != null) {
                    headers.setBearerAuth(token);
                }

                HttpEntity<String> entity = new HttpEntity<>(body, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    Notification.show("Kullanıcı başarıyla eklendi!", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Kullanıcı eklenemedi: " + response.getStatusCode(), 3000, Notification.Position.MIDDLE);
                }
            } catch (Exception e) {
                Notification.show("Hata: " + e.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });

        // Alanları alt alta ekle
        add(
                firstName,
                lastName,
                username,
                email,
                password,
                roleComboBox,
                addButton
        );
    }
}