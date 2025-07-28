package com.example.application.base.ui.view;

import com.example.application.base.ui.layout.AdminLayout;
import com.example.application.auth.TokenNotFoundException;
import com.example.application.service.AdminService;
import com.example.application.service.DoctorService;
import com.example.application.service.PatientService;
import com.example.application.service.RoleService;
import com.example.application.domain.Person;
import com.example.application.domain.Doctor;
import com.example.application.domain.Patient;
import com.example.application.domain.Admin;
import com.example.application.domain.Role;
import java.util.Optional;
import java.time.LocalDate;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value = "/admin", layout = AdminLayout.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

    private final DoctorService doctorService;
    private final AdminService adminService;
    private final PatientService patientService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminView(DoctorService doctorService, AdminService adminService, PatientService patientService, RoleService roleService, PasswordEncoder passwordEncoder) throws TokenNotFoundException {
        this.doctorService = doctorService;
        this.adminService = adminService;
        this.patientService = patientService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;

        setSpacing(true);
        setPadding(true);
        setWidthFull();

        // Admin için kullanıcı kayıt formu
        FormLayout formLayout = new FormLayout();
        TextField firstNameField = new TextField("Ad");
        TextField lastNameField = new TextField("Soyad");
        TextField usernameField = new TextField("Kullanıcı Adı");
        PasswordField passwordField = new PasswordField("Şifre");
        TextField emailField = new TextField("E-posta");
        ComboBox<String> roleCombo = new ComboBox<>("Rol");
        roleCombo.setItems("Admin", "Doctor", "Patient");

        // Role özel alanlar
        TextField branchField = new TextField("Branş"); // Doctor
        DatePicker birthDateField = new DatePicker("Doğum Tarihi"); // Patient
        ComboBox<String> genderField = new ComboBox<>("Cinsiyet"); // Patient
        genderField.setItems("Erkek", "Kadın", "Diğer");
        TextField rankField = new TextField("Rütbe"); // Admin

        // Dinamik alan yönetimi
        roleCombo.addValueChangeListener(event -> {
            formLayout.removeAll();
            formLayout.add(firstNameField, lastNameField, usernameField, passwordField, emailField, roleCombo);
            String role = event.getValue();
            if ("Doctor".equals(role)) {
                formLayout.add(branchField);
            } else if ("Patient".equals(role)) {
                formLayout.add(birthDateField, genderField);
            } else if ("Admin".equals(role)) {
                formLayout.add(rankField);
            }
        });

        // Varsayılan olarak sadece ortak alanlar
        formLayout.add(firstNameField, lastNameField, usernameField, passwordField, emailField, roleCombo);

        Button saveButton = new Button("Kaydet", e -> {
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            String email = emailField.getValue();
            String roleName = roleCombo.getValue();
            String branch = branchField.getValue();
            LocalDate birthDate = birthDateField.getValue();
            String gender = genderField.getValue();
            String rank = rankField.getValue();

            if (roleName == null) {
                Notification.show("Lütfen bir rol seçin.");
                return;
            }
            Optional<Role> roleOpt = roleService.findAll().stream().filter(r -> r.getName().equalsIgnoreCase(roleName)).findFirst();
            if (roleOpt.isEmpty()) {
                Notification.show("Rol bulunamadı!");
                return;
            }
            Role role = roleOpt.get();
            Person person = new Person();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setUsername(username);
            person.setPassword(passwordEncoder.encode(password));
            person.setEmail(email);
            person.setRole(role);
            if ("Patient".equals(roleName)) {
                person.setBirthDate(birthDate);
                person.setGender(gender);
            }
            // Kayıt işlemi
            // Username benzersiz mi kontrolü
            if (doctorService.findPersonByUsername(username).isPresent()) {
                Notification.show("Bu kullanıcı adı zaten mevcut!");
                return;
            }
            if ("Doctor".equals(roleName)) {
                person = doctorService.save(new Doctor(null, person, branch)).getPerson();
                Notification.show("Doktor kaydı başarıyla oluşturuldu!");
            } else if ("Patient".equals(roleName)) {
                person = patientService.save(new Patient(null, person)).getPerson();
                Notification.show("Hasta kaydı başarıyla oluşturuldu!");
            } else if ("Admin".equals(roleName)) {
                person = adminService.save(new Admin(null, person, rank)).getPerson();
                Notification.show("Admin kaydı başarıyla oluşturuldu!");
            }
            // Alanları temizle
            firstNameField.clear();
            lastNameField.clear();
            usernameField.clear();
            passwordField.clear();
            emailField.clear();
            branchField.clear();
            birthDateField.clear();
            genderField.clear();
            rankField.clear();
            roleCombo.clear();
        });

        add(formLayout, saveButton);
    }
}