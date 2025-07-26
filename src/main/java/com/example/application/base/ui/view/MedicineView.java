package com.example.application.base.ui.view;

import com.example.application.base.ui.component.FilterBar;
import com.example.application.base.ui.component.MedicineDrawer;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.dto.IMedicineResult;
import com.example.application.service.IMedicineService;
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

    public MedicineView(IMedicineService medicineService) {
        this.medicineService = medicineService;
        this.medicineDrawer = new MedicineDrawer("İlaç Ekle", medicineService);

        table = medicineTable();

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
            String name = ((String) filters.getOrDefault("name", ""));
            String unit = ((String) filters.getOrDefault("unit", ""));
            String description = (String) filters.getOrDefault("description", "");
            List<IMedicineResult> filtered = medicineService.filterMedicine(name, unit, description);
            table.setItems(filtered);
        });

        // Buton alanı
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);
        buttonLayout.add(addMedicineButton());

        add(buttonLayout, filterBar);
    }

    private GenericTable<IMedicineResult> medicineTable() {
        List<ColumnConfig<IMedicineResult, ?>> columns = List.of(
                new ColumnConfig<>("İsim", IMedicineResult::getMedicineName, true),
                new ColumnConfig<>("Birim", r -> r.getUnit().getDisplayName(), true),
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

        return new GenericTable<>(columns , medicineService.findAllMedicines());
    }

    private Button getDeleteButton(IMedicineResult r) {
        Button deleteButton = new Button("Sil");
        deleteButton.addClickListener(event -> {
            medicineService.deleteById(r.getMedicineId());
            table.setItems(medicineService.findAllMedicines());
            Notification.show("İlaç başarıyla silindi!", 3000, Notification.Position.TOP_CENTER);
        });
        return deleteButton;
    }

    private Button addMedicineButton() {
        Button addMedicineButton = new Button("İlaç Ekle");
        addMedicineButton.addClickListener(e -> medicineDrawer.open());
        return addMedicineButton;
    }
}
