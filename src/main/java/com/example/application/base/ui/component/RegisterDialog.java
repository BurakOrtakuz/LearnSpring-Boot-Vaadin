package com.example.application.base.ui.component;

import com.example.application.auth.AuthenticationService;
import com.example.application.auth.RegisterRequest;
import com.example.application.auth.RegisterResponse;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Locale;

public class RegisterDialog extends Dialog {

    AuthenticationService authenticationService;

    public RegisterDialog(AuthenticationService authenticationService) {
        super();
        this.authenticationService = authenticationService;

        VerticalLayout dialogContent = new VerticalLayout();
        Span dialogTitle = new Span("Kayıt Ol");
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField usernameField = new TextField();
        TextField identityNumberField = new TextField();
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
        HorizontalLayout sixthRow = new HorizontalLayout();

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

        identityNumberField.setClassName("register-dialog-username-field");
        identityNumberField.setLabel("Kullanıcı Adı");
        identityNumberField.setRequired(true);
        identityNumberField.setTabIndex(3);
        identityNumberField.setErrorMessage("Geçersiz Kullanıcı Adı");
        identityNumberField.setI18n(new TextField.TextFieldI18n()
                .setRequiredErrorMessage("Tc Kimlik zorunludur"));

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
        closeButton.addClickListener(e -> {this.close();});
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
            boolean check = false;
            if(firstNameField.isEmpty())
            {
                firstNameField.setHelperText("Lütfen adınızı girin.");
                check = true;
            }
            if(lastNameField.isEmpty())
            {
                lastNameField.setHelperText("Lütfen soyadınızı girin.");
                check = true;
            }
            if(usernameField.isEmpty())
            {
                usernameField.setHelperText("Lütfen kullanıcı adınızı girin.");
                check = true;
            }
            if(identityNumberField.isEmpty())
            {
                identityNumberField.setHelperText("Lütfen kimlik numaranızı girin.");
                check = true;
            }
            if(email.isEmpty())
            {
                email.setHelperText("Lütfen e-posta adresinizi girin.");
                check = true;
            }
            if(passwordField.isEmpty())
            {
                passwordField.setHelperText("Lütfen şifrenizi girin.");
                check = true;
            }
            if(confirmPasswordField.isEmpty()) {
                confirmPasswordField.setHelperText("Lütfen şifrenizi doğrulayın.");
                check = true;
            }
            if(genderGroup.isEmpty())
            {
                genderGroup.setErrorMessage("Lütfen cinsiyetinizi seçin.");
                check = true;
            }
            if(phoneField.isEmpty())
            {
                phoneField.setHelperText("Lütfen telefon numaranızı girin.");
                check = true;
            }
            if(addressField.isEmpty())
            {
                addressField.setHelperText("Lütfen adresinizi girin.");
                check = true;
            }
            if (!passwordField.getValue().equals(confirmPasswordField.getValue())) {
                passwordField.setHelperText("Şifreler eşleşmiyor.");
                confirmPasswordField.setHelperText("Şifreler eşleşmiyor.");
                check = true;
            }
            if (birthDay.getValue() == null) {
                birthDay.setHelperText("Lütfen doğum tarihinizi seçin.");
                check = true;
            }
            if(check)
            {
                return;
            }
            if(onRegister(
                    firstNameField.getValue(),
                    lastNameField.getValue(),
                    usernameField.getValue(),
                    identityNumberField.getValue(),
                    email.getValue(),
                    passwordField.getValue(),
                    birthDay.getValue().toString(),
                    genderGroup.getValue(),
                    phoneField.getValue(),
                    addressField.getValue()))
            {
                this.close();
                firstNameField.clear();
                lastNameField.clear();
                usernameField.clear();
                identityNumberField.clear();
                email.clear();
                passwordField.clear();
                confirmPasswordField.clear();
                birthDay.clear();
                genderGroup.clear();
                phoneField.clear();
                addressField.clear();
            }
        });
        registerButton.setTabIndex(11);

        firstRow.add(firstNameField, lastNameField);
        secondRow.add(usernameField, email);
        thirdRow.add(identityNumberField, phoneField);
        fourthRow.add(passwordField, confirmPasswordField);
        fifthRow.add(birthDay, genderGroup);
        sixthRow.add(addressField);

        dialogContent.add(
                firstRow,
                secondRow,
                thirdRow,
                fourthRow,
                fifthRow,
                sixthRow
        );
        this.getHeader().add(dialogTitle);
        this.getFooter().add(
                closeButton,
                registerButton
        );
        this.add(dialogContent);
    }

    private boolean onRegister(String firstName,
                               String lastName,
                               String username,
                               String identityNumber,
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
                    identityNumber,
                    email,
                    password,
                    birthDate,
                    gender,
                    phoneNumber,
                    address
                    );
            RegisterResponse response = authenticationService.register(registerRequest);
            Notification.show("Kayıt başarılı! Giriş yapabilirsiniz.", 3000, Notification.Position.MIDDLE);
            return true;
        }catch (Exception e)
        {
            Notification.show("Kayıt başarısız oldu: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            return false;
        }

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
