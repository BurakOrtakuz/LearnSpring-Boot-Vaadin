package com.example.application.base.ui.component.PatientCard;

import com.example.application.domain.Person;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CommunicationInformationDetails extends Details {
    public CommunicationInformationDetails(Person person) {
        super();
        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span header = new Span("İletişim Bilgileri");
        summary.add(header);

        VerticalLayout content = getVerticalLayout(person);

        super.setSummary(header);
        super.add(content);
    }

    private static VerticalLayout getVerticalLayout(Person person) {
        VerticalLayout content = new VerticalLayout();
        content.addClassName("communication-details");
        content.setWidthFull();
        content.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout row1 = new HorizontalLayout();

        VerticalLayout emailField = new DetailRowItem("E-posta", person.getEmail());
        VerticalLayout phoneField = new DetailRowItem("Telefon", person.getPhoneNumber());

        row1.add(emailField, phoneField);
        content.add(row1);
        return content;
    }
}
