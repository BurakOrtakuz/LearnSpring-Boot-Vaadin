package com.example.application.base.ui.component.PatientCard;

import com.example.application.domain.Person;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;

public class PatientInformationDetails extends Details {
    public PatientInformationDetails(Person person) {
        super();
        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        super.setOpened(true);
        Span header = new Span("Hasta Bilgileri");
        summary.add(header);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("patient-details");
        content.setWidthFull();
        content.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout name = new DetailRowItem("Adı Soyadı", person.getFirstName() + " " + person.getLastName());
        VerticalLayout gender = new DetailRowItem("Cinsiyet", person.getGender());
        VerticalLayout birthDate = new DetailRowItem("Doğum Tarihi", formatBirthDate(person.getBirthDate()));

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        VerticalLayout address = new DetailRowItem("Adres", person.getAddress());
        VerticalLayout phone = new DetailRowItem("Telefon", person.getPhoneNumber());
        VerticalLayout email = new DetailRowItem("E-posta", person.getEmail());

        row1.add(name, gender, birthDate);
        row2.add(address, phone, email);

        content.add(row1, row2);

        super.setSummary(header);
        super.add(content);
    }
    private String formatBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            return "-";
        }
        return birthDate.toString();
    }
}
