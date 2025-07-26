package com.example.application.base.ui.component;

import com.example.application.domain.Medicine;
import com.example.application.domain.Unit;
import com.example.application.dto.IMedicineResult;
import com.example.application.service.IMedicineService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class MedicineDrawer extends RightDrawer{
    private final IMedicineService medicineService;
    private TextField medicineNameField;
    private ComboBox<Unit> unitComboBox;
    private TextArea medicineDescriptionField;

    public MedicineDrawer(IMedicineService medicineService) {
        super();
        this.medicineService = medicineService;
        createMedicineDrawer();
    }

    public MedicineDrawer(String title, IMedicineService medicineService) {
        super(title);
        this.medicineService = medicineService;
        createMedicineDrawer();
    }

    public void setItemsValue(IMedicineResult medicineResault) {
        medicineNameField.setValue(medicineResault.getMedicineName());
        // IMedicineResult'ı da güncellemek gerekebilir
        medicineDescriptionField.setValue(medicineResault.getDescription() != null ? medicineResault.getDescription() : "");
    }

    public void lockMedicineNameField() {
        medicineNameField.setReadOnly(true);
    }


    private void createMedicineDrawer() {
        medicineNameField = new TextField("İlaç Adı");
        medicineNameField.setPlaceholder("İlaç adı giriniz");

        unitComboBox = new ComboBox<>();
        unitComboBox.setPlaceholder("Birim giriniz");
        unitComboBox.setItems(Unit.values());
        unitComboBox.setItemLabelGenerator(Unit::getDisplayName);

        medicineDescriptionField = new TextArea();
        medicineDescriptionField.setPlaceholder("İlaç açıklaması giriniz!");
        medicineDescriptionField.setLabel("Açıklama");
        medicineDescriptionField.setWidthFull();

        Button saveButton = getSaveButton(medicineNameField, unitComboBox);

        this.setCloseOnEsc(true);
        this.setCloseOnOutsideClick(true);
        this.addDialogCloseActionListener(event ->
        {
            medicineNameField.clear();
            unitComboBox.clear();
            medicineDescriptionField.clear();
            medicineNameField.setReadOnly(false);
        });
        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("doctor-medicine-drawer-content");
        layout.add(medicineNameField, unitComboBox,medicineDescriptionField, saveButton);

        this.add(layout);
    }

    private Button getSaveButton(TextField medicineNameField, ComboBox<Unit> unitComboBox) {
        Button saveButton = new Button("İlaç Ekle");
        saveButton.addClickListener(e -> {
            String medicineName = medicineNameField.getValue();
            Unit unit = unitComboBox.getValue();
            if (medicineName == null || medicineName.isBlank()) {
                medicineNameField.setHelperText("İlaç adı zorunludur!");
                return;
            }
            Medicine medicine = new Medicine();
            medicine.setName(medicineName);
            medicine.setUnit(unit);
            medicine.setDescription(medicineDescriptionField.getValue());
            medicineService.saveIfNotExists(medicine);
            Notification.show("İlaç başarıyla eklendi!", 3000, Notification.Position.TOP_CENTER);
            medicineNameField.clear();
            unitComboBox.clear();
        });
        return saveButton;
    }
}
