package com.example.application.base.ui.layout;

import com.example.application.auth.*;
import com.example.application.base.ui.view.AboutView;
import com.example.application.base.ui.view.HomeView;
import com.example.application.base.ui.view.Patient.AppointmentsView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.Cookie;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.application.domain.Person;

import java.util.Locale;

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

    private Dialog loginDialog()
    {
        Dialog dialog = new Dialog();
        Span dialogTitle = new Span("Giriş Yap");

        VerticalLayout dialogContent = new VerticalLayout();

        TextField usernameField = new TextField("Kullanıcı Adı");
        PasswordField passwordField = new PasswordField("Şifre");
        Button loginButton = new Button("Giriş Yap");
        Button closeButton = new Button("Kapat", e -> dialog.close());
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
            dialog.close();
            registerDialog.open();
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
                dialog.close();
            }
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
        Span dialogTitle = new Span("Kayıt Ol");
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField usernameField = new TextField();
        TextField email = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        DatePicker birthDay = new DatePicker();
        RadioButtonGroup<String> genderGroup = new RadioButtonGroup<>();
        TextField addressField = new TextField();
        Button registerButton = new Button();
        Button closeButton = new Button();

        HorizontalLayout firstRow = new HorizontalLayout();
        HorizontalLayout secondRow = new HorizontalLayout();
        HorizontalLayout thirdRow = new HorizontalLayout();
        HorizontalLayout fourthRow = new HorizontalLayout();
        HorizontalLayout fifthRow = new HorizontalLayout();

        firstRow.setClassName("register-dialog-first-row");

        secondRow.setClassName("register-dialog-second-row");

        thirdRow.setClassName("register-dialog-third-row");

        fourthRow.setClassName("register-dialog-fourth-row");

        fifthRow.setClassName("register-dialog-fifth-row");

        dialogTitle.setClassName("register-dialog-title");

        dialogContent.setClassName("register-dialog-content");


        firstNameField.setClassName("register-dialog-firstname-field");
        firstNameField.setLabel("Ad");
        firstNameField.setRequired(true);
        firstNameField.setAutoselect(true);
        firstNameField.setTabIndex(1);
        firstNameField.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Ad alanı zorunludur"));

        lastNameField.setClassName("register-dialog-lastname-field");
        lastNameField.setLabel("Soyad");
        lastNameField.setRequired(true);
        lastNameField.setTabIndex(2);
        lastNameField.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Soyad alanı zorunludur"));

        usernameField.setClassName("register-dialog-username-field");
        usernameField.setLabel("Kullanıcı Adı");
        usernameField.setRequired(true);
        usernameField.setTabIndex(3);
        usernameField.setErrorMessage("Geçersiz Kullanıcı Adı");
        usernameField.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Kullanıcı adı zorunludur"));

        email.setClassName("register-dialog-email-field");
        email.setLabel("E-posta");
        email.setRequired(true);
        email.setTabIndex(4);
        email.setPattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        email.setErrorMessage("Geçersiz e-posta Adresi");
        email.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("E-posta adresi girin"));

        passwordField.setClassName("register-dialog-password-field");
        passwordField.setLabel("Şifre");
        passwordField.setRequired(true);
        passwordField.setTabIndex(5);
        passwordField.setI18n(new PasswordField.PasswordFieldI18n()
                .setRequiredErrorMessage("Şifre alanı zorunludur"));

        confirmPasswordField.setClassName("register-dialog-confirm-password-field");
        confirmPasswordField.setLabel("Şifreyi Doğrula");
        confirmPasswordField.setRequired(true);
        confirmPasswordField.setTabIndex(6);
        confirmPasswordField.setErrorMessage("Şifreler eşleşmiyor");
        confirmPasswordField.setI18n(new PasswordField.PasswordFieldI18n()
                .setRequiredErrorMessage("Şifreyi doğrulama alanı zorunludur"));

        birthDay.setClassName("register-dialog-birthdate-field");
        birthDay.setLabel("Doğum Tarihi");
        birthDay.setRequired(true);
        birthDay.setTabIndex(7);
        birthDay.setLocale(new Locale("tr", "TR"));
        birthDay.setInvalid(true);
        birthDay.getElement().executeJs(
            "if (this.inputElement) {\n" +
            "  this.inputElement.addEventListener('keypress', function(e) {\n" +
            "    let value = this.value;\n" +
            "    if (!/[0-9.]/.test(e.key)) { e.preventDefault(); return; }\n" +
            "    if (value.length > 9) { e.preventDefault(); return; }\n" +
            "    if ((value.length === 2 || value.length === 5) && e.key !== '.') {\n" +
            "      this.value = value + '.';\n" +
            "    }\n" +
            "    if ((value.length === 2 || value.length === 5) && e.key === '.') { e.preventDefault(); return; }\n" +
            "  });\n" +
            "  this.inputElement.addEventListener('input', function(e) {\n" +
            "    let v = this.value.replace(/[^0-9.]/g, '');\n" +
            "    if (v.length > 10) v = v.slice(0, 10);\n" +
            "    if (v.length === 2 && v[2] !== '.') v = v.slice(0,2) + '.' + v.slice(2);\n" +
            "    if (v.length > 5 && v[5] !== '.') v = v.slice(0,5) + '.' + v.slice(5);\n" +
            "    this.value = v;\n" +
            "  });\n" +
            "}"
        );

        genderGroup.setClassName("register-dialog-gender-group");
        genderGroup.setLabel("Cinsiyet");
        genderGroup.setRequired(true);
        genderGroup.setItems("Erkek", "Kadın");
        genderGroup.setErrorMessage("Cinsiyet seçimi zorunludur");
        genderGroup.getElement().getNode().runWhenAttached(ui -> {
            genderGroup.getElement().setAttribute("tabIndex", "8");
            genderGroup.getElement().callJsFunction("focus");
        });
        genderGroup.getElement().addEventListener("focus",event -> {
            genderGroup.getChildren().forEach(component ->component.getElement().setAttribute("tabIndex", "8"));
            genderGroup.getChildren().findFirst()
                    .ifPresent(component -> component.getElement().callJsFunction("focus"));
        });

        TextField phoneField = phoneNumberField();
        phoneField.setClassName("register-dialog-phone-field");
        phoneField.setLabel("Telefon");
        phoneField.setRequired(true);
        phoneField.setTabIndex(9);

        addressField.setClassName("register-dialog-address-field");
        addressField.setLabel("Adres");
        addressField.setRequired(true);
        addressField.setTabIndex(10);
        addressField.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Adres alanı zorunludur"));

        closeButton.setClassName("register-dialog-close-button");
        closeButton.setText("Kapat");
        closeButton.addClickListener(e -> {dialog.close();});
        closeButton.getElement().addEventListener("keydown", event -> {
            if ("Tab".equals(event.getEventData().getString("event.key"))) {
                firstNameField.focus();
            }
        }).addEventData("event.key");
        closeButton.setTabIndex(12);

        registerButton.addClassName("register-dialog-register-button");
        registerButton.setText("Kayıt Ol");
        registerButton.addClickListener(e -> {
            // Alan kontrolleri
            if (firstNameField.isEmpty() || lastNameField.isEmpty() || usernameField.isEmpty() || email.isEmpty() || passwordField.isEmpty() || confirmPasswordField.isEmpty() || birthDay.isEmpty() || genderGroup.isEmpty() || phoneField.isEmpty() || addressField.isEmpty()) {
                Notification.show("Lütfen tüm alanları doldurun.", 3000, Notification.Position.MIDDLE);
                return;
            }
            if (!passwordField.getValue().equals(confirmPasswordField.getValue())) {
                Notification.show("Şifreler eşleşmiyor.", 3000, Notification.Position.MIDDLE);
                return;
            }
            if (birthDay.getValue() == null) {
                Notification.show("Geçerli bir doğum tarihi girin.", 3000, Notification.Position.MIDDLE);
                return;
            }
            if(onRegister(
                    firstNameField.getValue(),
                    lastNameField.getValue(),
                    usernameField.getValue(),
                    email.getValue(),
                    passwordField.getValue(),
                    birthDay.getValue().toString(),
                    genderGroup.getValue(),
                    phoneField.getValue(),
                    addressField.getValue()))
            {
                registerDialog.close();
                firstNameField.clear();
                lastNameField.clear();
                usernameField.clear();
                email.clear();
                passwordField.clear();
                confirmPasswordField.clear();
                birthDay.clear();
                genderGroup.clear();
                phoneField.clear();
                addressField.clear();
                loginDialog.open();
            }
        });
        registerButton.setTabIndex(11);

        firstRow.add(firstNameField, lastNameField);
        secondRow.add(usernameField, passwordField);
        thirdRow.add(birthDay, genderGroup);
        fourthRow.add(phoneField, addressField);

        dialogContent.add(
                firstRow,
                secondRow,
                thirdRow,
                fourthRow
        );
        dialog.getHeader().add(dialogTitle);
        dialog.getFooter().add(
                closeButton,
                registerButton
        );
        dialog.add(dialogContent);
        return dialog;
    }

    private boolean onRegister(String firstName,
                               String lastName,
                               String username,
                               String email,
                               String password,
                               String birthDate,
                               String gender,
                               String phoneNumber,
                               String address)
    {
        try
        {
            RegisterRequest registerRequest = new RegisterRequest(firstName,
                    lastName,
                    username,
                    email,
                    password);
            RegisterResponse response = authenticationService.register(registerRequest);
            Notification.show("Kayıt başarılı! Giriş yapabilirsiniz.", 3000, Notification.Position.MIDDLE);
            return true;
        }catch (Exception e)
        {
            Notification.show("Kayıt başarısız oldu: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            return false;
        }

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
        jwtCookie.setMaxAge(60 * 60 * 24); // 1 gün geçerli
        jwtCookie.setAttribute("SameSite", "Strict"); // CSRF için önemli

        VaadinSession.getCurrent().setAttribute("jwtToken", token);
        VaadinService.getCurrentResponse().addCookie(jwtCookie);
    }

    public TextField phoneNumberField()
    {
        TextField field = new TextField("Phone number");
        field.setRequiredIndicatorVisible(true);
        field.setPattern(
                "^[+]?[\\(]?[0-9]{3}[\\)]?[\\-]?[0-9]{3}[\\-]?[0-9]{4,6}$");
        field.setAllowedCharPattern("[0-9()+-]");
        field.setMinLength(5);
        field.setMaxLength(18);

        field.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Bu alan zorunludur")
                .setMinLengthErrorMessage("En az 5 karakter olmalıdır")
                .setMaxLengthErrorMessage("En fazla 18 karakter olmalıdır")
                .setPatternErrorMessage("Hatalı telefon numarası formatı"));
        return  field;
    }
}
