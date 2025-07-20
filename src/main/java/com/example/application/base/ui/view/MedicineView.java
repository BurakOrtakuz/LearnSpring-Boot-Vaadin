package com.example.application.base.ui.view;

import com.example.application.base.ui.component.FilterBar;
import com.example.application.base.ui.component.MedicineDrawer;
import com.example.application.base.ui.component.RightDrawer;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.domain.Unit;
import com.example.application.dto.IMedicineResult;
import com.example.application.service.IMedicineService;
import com.example.application.service.IUnitService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;
import java.util.Map;

@Route(value = "/medicine", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class MedicineView extends VerticalLayout {
    private final IMedicineService medicineService;
    private final MedicineDrawer medicineDrawer;
    private final GenericTable<IMedicineResult> table;
    private final RightDrawer unitDrawer;
    private final IUnitService unitService;

    public MedicineView(IMedicineService medicineService, IUnitService unitService1) {
        this.medicineService = medicineService;
        this.unitService = unitService1;
        this.medicineDrawer = new MedicineDrawer("İlaç Ekle", medicineService, unitService);

        table = medicineTable();
        unitDrawer = createUnitDrawer();
        // 🔹 Filter Bar ve filtre bileşenleri
        TextField nameFilter = new TextField("İsim");
        TextField unitFilter = new TextField("Birim");
        TextField descriptionFilter = new TextField("Açıklama");

        FilterBar filterBar = new FilterBar(table);
        filterBar.addFilterComponent(nameFilter, nameFilter::getValue, "name");
        filterBar.addFilterComponent(unitFilter, unitFilter::getValue, "unit");
        filterBar.addFilterComponent(descriptionFilter, descriptionFilter::getValue, "description");
        filterBar.setHeightFull();

        filterBar.setReloadCallback(() -> {
            Map<String, Object> filters = filterBar.getFilterValues();
            String name = ((String) filters.getOrDefault("name", "")).toLowerCase();
            String unit = ((String) filters.getOrDefault("unit", "")).toLowerCase();
            String description = (String) filters.getOrDefault("description", "");
            List<IMedicineResult> filtered = medicineService.filterMedicine(name, unit, description);
            table.setItems(filtered);
        });

        // Buton alanı
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);
        buttonLayout.add(addMedicineButton(), addUnitButton());

        add(buttonLayout, filterBar);
    }

    private RightDrawer createUnitDrawer() {
        RightDrawer drawer = new RightDrawer();

        drawer.setTitle("Birim Ekle");

        TextField unitNameField = new TextField("Birim Adı");
        unitNameField.setPlaceholder("Birim adı giriniz");

        Button saveButton = new Button("Birim Ekle");
        saveButton.addClickListener(e -> {
            String unitName = unitNameField.getValue();
            if (unitName == null || unitName.isBlank()) {
                unitNameField.setHelperText("Bu alan zorunludur!");
                return;
            }
            Unit unit = new Unit();
            unit.setName(unitName);
            unitService.save(unit);
            Notification.show("Birim başarıyla eklendi!", 3000, Notification.Position.TOP_CENTER);
            unitNameField.clear();
        });

        VerticalLayout layout = new VerticalLayout();
        layout.setClassName("doctor-unit-drawer-content");
        layout.add(unitNameField, saveButton);

        drawer.setContent(layout);
        return drawer;
    }

    private GenericTable<IMedicineResult> medicineTable() {
        List<ColumnConfig<IMedicineResult, ?>> columns = List.of(
                new ColumnConfig<>("İsim", IMedicineResult::getMedicineName, true),
                new ColumnConfig<>("Birim", r -> r.getUnit().getName(), true),
                new ColumnConfig<>("Açıklama", IMedicineResult::getDescription, true),
                new ColumnConfig<>("İşlemler", r -> {
                    HorizontalLayout layout = new HorizontalLayout();
                    layout.setWidth(null);
                    Button updateButton = new Button("Güncelle");
                    updateButton.addClickListener(event -> {
                        medicineDrawer.setItemsValue(r);
                        medicineDrawer.lockMedicineNameField();
                        medicineDrawer.open();
                    });

                    Button deleteButton = getDeleteButton(r);
                    layout.add(updateButton, deleteButton);
                    return layout;
                }, false,true)
        );

        GenericTable<IMedicineResult> table = new GenericTable<>(columns , medicineService.findAllMedicines());
        table.setAutoWidth(true);
        table.addItemDoubleClickListener(event -> {
            medicineDrawer.setItemsValue(event.getItem());
            medicineDrawer.lockMedicineNameField();
            medicineDrawer.open();
        });

        return table;
    }

    private Button getDeleteButton(IMedicineResult r) {
        Button deleteButton = new Button("Sil");
        deleteButton.addClickListener(event -> {
            medicineService.deleteById(r.getMedicineId());
            table.setItems(medicineService.findAllMedicines());
            Notification.show("İlaç silindi", 3000, Notification.Position.MIDDLE);
            medicineDrawer.close();
            medicineTable().setItems(medicineService.findAllMedicines());
        });
        return deleteButton;
    }

    private Button addMedicineButton() {
        Button addMedicineButton = new Button();
        addMedicineButton.setText("İlaç Ekle");
        addMedicineButton.addClickListener(event -> {
            medicineDrawer.open();
        });
        return addMedicineButton;
    }

    private Button addUnitButton() {
        Button addUnitButton = new Button("Birim Ekle");
        addUnitButton.addClickListener(event -> {
            unitDrawer.open();
        });
        return addUnitButton;
    }
}
