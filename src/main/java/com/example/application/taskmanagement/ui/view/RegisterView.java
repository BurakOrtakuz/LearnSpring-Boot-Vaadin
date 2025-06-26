package com.example.application.taskmanagement.ui.view;

import com.example.application.base.ui.component.FooterBar;
import com.example.application.base.ui.component.MainNavbar;
import com.example.application.taskmanagement.auth.AuthenticationService;
import com.example.application.taskmanagement.auth.RegisterRequest;
import com.example.application.taskmanagement.auth.RegisterResponse;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value ="/login/register", layout = MainNavbar.class)
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final AuthenticationService authenticationService;
    Button registerButton;

    @Autowired
    public RegisterView(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div messageDiv = new Div();
        messageDiv.getStyle()
                .set("width", "420px")
                .set("padding", "12px 24px")
                .set("border-radius", "8px")
                .set("margin-bottom", "16px")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("display", "none")
                .set("box-shadow", "0 4px 32px rgba(70,53,177,0.10)")
                .set("background", "#fff");

        Div formCard = new Div();
        formCard.getStyle()
                .set("width", "420px")
                .set("padding", "32px")
                .set("background", "#fff")
                .set("border-radius", "12px")
                .set("box-shadow", "0 4px 32px rgba(70,53,177,0.10)");

        H2 title = new H2("Kayıt Ol");
        title.getStyle().set("text-align", "center").set("margin-bottom", "24px");

        TextField firstName = new TextField("Ad");
        TextField lastName = new TextField("Soyad");
        TextField username = new TextField("Kullanıcı Adı");
        EmailField email = new EmailField("E-posta");
        PasswordField password = new PasswordField("Şifre");

        registerButton = new Button("Kayıt Ol", event -> {
            RegisterRequest request = RegisterRequest.builder()
                    .firstName(firstName.getValue())
                    .lastName(lastName.getValue())
                    .username(username.getValue())
                    .email(email.getValue())
                    .password(password.getValue())
                    .build();

            try {
                RegisterResponse response = authenticationService.register(request);
                messageDiv.setText("Kayıt başarılı!");
                messageDiv.getStyle()
                        .set("background", "#43a047")
                        .set("color", "#fff")
                        .set("display", "block");
                UI.getCurrent().navigate("/login/login");
            } catch (Exception e) {
                messageDiv.setText("Kayıt başarısız oldu");
                messageDiv.getStyle()
                        .set("background", "#e53935")
                        .set("color", "#fff")
                        .set("display", "block");
            }
        });
        registerButton.getStyle()
                .set("width", "100%")
                .set("background", "#4635B1")
                .set("color", "#fff")
                .set("font-weight", "bold")
                .set("border-radius", "6px");

        FormLayout formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.add(firstName, lastName, username, email, password, registerButton);

        formCard.add(title, formLayout);

        FooterBar footerBar = new FooterBar();
        add(messageDiv, formCard, footerBar);
    }
}