package com.example.application.base.ui.view;

import com.example.application.base.ui.component.MedicineDrawer;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.dto.IMedicineResult;
import com.example.application.service.IMedicineService;
import com.example.application.service.IUnitService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = "/medicine", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class MedicineView extends VerticalLayout {
    private final IMedicineService medicineService;
    private final MedicineDrawer medicineDrawer;
    private GenericTable<IMedicineResult> table;

    public MedicineView(IMedicineService medicineService, IUnitService unitService) {
        this.medicineService = medicineService;
        this.medicineDrawer = new MedicineDrawer("İlaç Ekle", medicineService, unitService);

        table = medicineTable();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setPadding(true);

        Button addMedicineButton = addMedicineButton();

        horizontalLayout.add(addMedicineButton);
        add(horizontalLayout,table);
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

}
