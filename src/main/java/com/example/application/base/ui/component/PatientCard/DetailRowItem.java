package com.example.application.base.ui.component.PatientCard;

import com.vaadin.flow.component.charts.model.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;

public class DetailRowItem extends VerticalLayout {
    public DetailRowItem(String label, String value) {
        super();
        if (value == null)
            value = "-";
        if (label == null)
            label = "-";

        super.setPadding(false);
        super.setSpacing(false);

        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setReadOnly(true);
        textField.setWidthFull();
        super.add(textField);
    }
}
