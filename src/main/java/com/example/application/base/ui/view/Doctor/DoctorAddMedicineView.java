package com.example.application.base.ui.view.Doctor;

import com.example.application.domain.Medicine;
import com.example.application.domain.Unit;
import com.example.application.service.MedicineService;
import com.example.application.service.UnitService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import java.util.List;

@Route(value = "/doctor/medicine/add", layout = com.example.application.base.ui.layout.DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorAddMedicineView extends VerticalLayout {
    public DoctorAddMedicineView(MedicineService medicineService, UnitService unitService) {
        setSpacing(true);
        setPadding(true);
        setWidthFull();

        TextField nameField = new TextField("İlaç Adı");
        ComboBox<Unit> unitComboBox = new ComboBox<>("Birim");
        unitComboBox.setItems(unitService.findAll());
        unitComboBox.setItemLabelGenerator(Unit::getName);
        TextArea descriptionField = new TextArea("Açıklama");
        descriptionField.setWidthFull();
        Button saveButton = new Button("İlaç Ekle");
        saveButton.addClickListener(e -> {
            String name = nameField.getValue();
            Unit unit = unitComboBox.getValue();
            String desc = descriptionField.getValue();
            if (name == null || name.isBlank() || unit == null) {
                Notification.show("İlaç adı ve birim zorunludur!", 3000, Notification.Position.MIDDLE);
                return;
            }
            Medicine medicine = new Medicine();
            medicine.setName(name);
            medicine.setUnit(unit);
            medicine.setDescription(desc);
            medicineService.save(medicine);
            Notification.show("İlaç başarıyla eklendi!", 3000, Notification.Position.TOP_CENTER);
            nameField.clear();
            unitComboBox.clear();
            descriptionField.clear();
        });
        add(nameField, unitComboBox, descriptionField, saveButton);
    }
}

