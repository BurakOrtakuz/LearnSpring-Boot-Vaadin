package com.example.application.base.ui.view.Doctor;

import com.example.application.domain.Unit;
import com.example.application.service.UnitService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "/doctor/unit/add", layout = com.example.application.base.ui.layout.DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorAddUnitView extends VerticalLayout {
    public DoctorAddUnitView(UnitService unitService) {
        setSpacing(true);
        setPadding(true);
        setWidthFull();

        TextField nameField = new TextField("Birim Adı");
        Button saveButton = new Button("Birim Ekle");
        saveButton.addClickListener(e -> {
            String name = nameField.getValue();
            if (name == null || name.isBlank()) {
                Notification.show("Birim adı zorunludur!", 3000, Notification.Position.MIDDLE);
                return;
            }
            Unit unit = new Unit();
            unit.setName(name);
            unitService.save(unit);
            Notification.show("Birim başarıyla eklendi!", 3000, Notification.Position.TOP_CENTER);
            nameField.clear();
        });
        add(nameField, saveButton);
    }
}

