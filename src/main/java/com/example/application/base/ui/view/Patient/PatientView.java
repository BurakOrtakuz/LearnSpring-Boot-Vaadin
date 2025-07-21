package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.domain.Person;
import com.example.application.service.IPatientService;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;

@Route(value = "/patient", layout = PatientMainLayout.class)
@RolesAllowed("PATIENT")
public class PatientView extends VerticalLayout {

    private final IPatientService patientService;

    public PatientView(IPatientService patientService) {
        this.patientService = patientService;

        setSpacing(true);
        setPadding(true);
        setWidthFull();

        // Sayfa başlığı
        add(new com.vaadin.flow.component.html.H2("Kişisel Bilgilerim"));

        // Bilgi panelleri
        add(patientInformation(), communicationInformation());
    }

    private Details communicationInformation() {
        Details details = new Details();
        Person currentPatient = getCurrentUser();

        if (currentPatient == null) {
            details.setSummary(new Span("İletişim Bilgileri"));
            details.add(new Span("Kullanıcı bilgileri yüklenemedi."));
            return details;
        }

        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span header = new Span("İletişim Bilgileri");
        summary.add(header);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("communication-details");
        content.setWidthFull();
        content.setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout row1 = new HorizontalLayout();

        VerticalLayout emailField = newRowItem("E-posta", currentPatient.getEmail());
        VerticalLayout phoneField = newRowItem("Telefon", currentPatient.getPhoneNumber());

        row1.add(emailField, phoneField);
        content.add(row1);

        details.setSummary(header);
        details.add(content);
        return details;
    }

    private Details patientInformation() {
        Details details = new Details();
        Person currentPatient = getCurrentUser();

        if (currentPatient == null) {
            details.setSummary(new Span("Hasta Bilgileri"));
            details.add(new Span("Kullanıcı bilgileri yüklenemedi."));
            return details;
        }

        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(JustifyContentMode.BETWEEN);

        details.setOpened(true);
        Span header = new Span("Hasta Bilgileri");
        summary.add(header);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("patient-details");
        content.setWidthFull();
        content.setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout name = newRowItem("Adı Soyadı", currentPatient.getFirstName() + " " + currentPatient.getLastName());
        VerticalLayout gender = newRowItem("Cinsiyet", currentPatient.getGender());
        VerticalLayout birthDate = newRowItem("Doğum Tarihi", formatBirthDate(currentPatient.getBirthDate()));

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout address = newRowItem("Adres", currentPatient.getAddress());
        VerticalLayout phone = newRowItem("Telefon", currentPatient.getPhoneNumber());
        VerticalLayout email = newRowItem("E-posta", currentPatient.getEmail());

        row1.add(name, gender, birthDate);
        row2.add(address, phone, email);

        content.add(row1, row2);

        details.setSummary(header);
        details.add(content);
        return details;
    }

    private String formatBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return "-";
        }
        return birthDate.toString();
    }

    private VerticalLayout newRowItem(String label, String value) {
        if (value == null)
            value = "-";
        if (label == null)
            label = "-";

        VerticalLayout row = new VerticalLayout();
        row.setPadding(false);
        row.setSpacing(false);

        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setReadOnly(true);
        textField.setWidthFull();

        row.add(textField);
        return row;
    }

    private Person getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Person person) {
            return person;
        }
        return null;
    }
}
