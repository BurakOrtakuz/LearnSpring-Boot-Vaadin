package com.example.application.base.ui.view;

import com.example.application.domain.Person;
import com.example.application.dto.IPrescriptionMedicineByPersonResult;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.util.List;

public class PatientCard extends VerticalLayout {

    private final Person person;
    private final IPrescriptionMedicineService prescriptionMedicineService;

    public PatientCard(Person person, IPrescriptionMedicineService prescriptionMedicineService)
    {
        this.person = person;
        this.prescriptionMedicineService = prescriptionMedicineService;
        add(patientInformation(),
            communicationInformation(),
            medicineInformation()
        );
    }
    private Details communicationInformation() {
        Details details = new Details();

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

        VerticalLayout emailField = newRowItem("E-posta", person.getEmail());
        VerticalLayout phoneField = newRowItem("Telefon", person.getPhoneNumber());

        row1.add(emailField, phoneField);
        content.add(row1);

        details.setSummary(header);
        details.add(content);
        return details;
    }

    private Details patientInformation() {
        Details details = new Details();

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

        VerticalLayout name = newRowItem("Adı Soyadı", person.getFirstName() + " " + person.getLastName());
        VerticalLayout gender = newRowItem("Cinsiyet", person.getGender());
        VerticalLayout birthDate = newRowItem("Doğum Tarihi", formatBirthDate(person.getBirthDate()));

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout address = newRowItem("Adres", person.getAddress());
        VerticalLayout phone = newRowItem("Telefon", person.getPhoneNumber());
        VerticalLayout email = newRowItem("E-posta", person.getEmail());

        row1.add(name, gender, birthDate);
        row2.add(address, phone, email);

        content.add(row1, row2);

        details.setSummary(header);
        details.add(content);
        return details;
    }

    private Details medicineInformation() {
        Details details = new Details();

        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(JustifyContentMode.BETWEEN);

        Span header = new Span("İlaç Bilgileri");
        summary.add(header);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("medicine-details");
        content.setWidthFull();
        content.setJustifyContentMode(JustifyContentMode.CENTER);

        List<IPrescriptionMedicineByPersonResult> medicines =
                prescriptionMedicineService.findByPrescription_Person_PersonId(person.getId());


        for(IPrescriptionMedicineByPersonResult medicine : medicines)
        {
            HorizontalLayout row = new HorizontalLayout();
            VerticalLayout medicineRow = newRowItem("İlaç İsmi", medicine.getMedicineName());
            VerticalLayout unitRow = newRowItem("Birim", medicine.getUnitName());
            VerticalLayout descriptionRow = newRowItem("Açıklama", medicine.getDescription());
            VerticalLayout timestampRow = newRowItem("Reçete Tarihi", medicine.getTimestamp() != null ? medicine.getTimestamp().toString() : "-");
            VerticalLayout finishTimeRow = newRowItem("Bitiş Tarihi", medicine.getFinishTime() != null ? medicine.getFinishTime().toString() : "-");

            row.add(medicineRow, unitRow, descriptionRow, timestampRow, finishTimeRow);
            content.add(row);
        }

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
}
