package com.example.application.base.ui.view.Doctor;

import com.example.application.Jaster.CreatePdf;
import com.example.application.domain.Examination;
import com.example.application.domain.Medicine;
import com.example.application.domain.Prescription;
import com.example.application.domain.PrescriptionMedicine;
import com.example.application.service.ExaminationService;
import com.example.application.service.IPrescriptionMedicineService;
import com.example.application.service.MedicineService;
import com.example.application.service.PrescriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;

@Route(value = "/doctor/prescription/create", layout = com.example.application.base.ui.layout.DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class CreatePrescriptionView extends VerticalLayout implements HasUrlParameter<Long> {
    private final PrescriptionService prescriptionService;
    private final ExaminationService examinationService;
    private final CreatePdf createPdf;
    private final MedicineService medicineService;
    private Examination examination;
    private final IPrescriptionMedicineService prescriptionMedicineService;

    public CreatePrescriptionView(PrescriptionService prescriptionService, ExaminationService examinationService, CreatePdf createPdf, MedicineService medicineService, IPrescriptionMedicineService prescriptionMedicineService) {
        this.prescriptionService = prescriptionService;
        this.examinationService = examinationService;
        this.createPdf = createPdf;
        this.medicineService = medicineService;
        this.prescriptionMedicineService = prescriptionMedicineService;
        setSpacing(true);
        setPadding(true);
        setWidthFull();
    }

    @Override
    public void setParameter(BeforeEvent event, Long examinationId) {
        removeAll();
        examination = examinationService.findById(examinationId).orElse(null);
        if (examination == null) {
            add(new Div(new com.vaadin.flow.component.html.Span("Muayene bulunamadı.")));
            return;
        }
        TextArea prescriptionArea = new TextArea("Reçete Notu");
        prescriptionArea.setWidthFull();
        prescriptionArea.setHeight("200px");
        Button saveButton = new Button("Reçeteyi Kaydet");
        // İlaç ekleme alanı
        List<Medicine> allMedicines = medicineService.findAll();
        ComboBox<Medicine> medicineComboBox = new ComboBox<>("İlaç Seçiniz");
        medicineComboBox.setItems(allMedicines);
        medicineComboBox.setItemLabelGenerator(Medicine::getName);
        TextArea medDescArea = new TextArea("Açıklama (doz, kullanım vs.)");
        medDescArea.setWidthFull();
        Button addMedicineButton = new Button("Listeye Ekle");
        List<PrescriptionMedicine> selectedMedicines = new ArrayList<>();
        Grid<PrescriptionMedicine> medicineGrid = new Grid<>(PrescriptionMedicine.class, false);
        medicineGrid.addColumn(pm -> pm.getMedicine().getName()).setHeader("İlaç Adı");
        medicineGrid.addColumn(pm -> pm.getMedicine().getDescription()).setHeader("İlaç Açıklaması");
        medicineGrid.addColumn(pm -> {
            String medDesc = pm.getMedicine().getDescription();
            String docDesc = pm.getDescription();
            if (medDesc == null) medDesc = "";
            if (docDesc == null) docDesc = "";
            if (!medDesc.isBlank() && !docDesc.isBlank()) {
                return medDesc + " | " + docDesc;
            } else if (!medDesc.isBlank()) {
                return medDesc;
            } else {
                return docDesc;
            }
        }).setHeader("Tüm Açıklama");
        medicineGrid.setItems(selectedMedicines);
        addMedicineButton.addClickListener(ev -> {
            Medicine selected = medicineComboBox.getValue();
            String medDesc = medDescArea.getValue();
            if (selected != null && selectedMedicines.stream().noneMatch(pm -> pm.getMedicine().equals(selected))) {
                PrescriptionMedicine pm = new PrescriptionMedicine();
                pm.setMedicine(selected);
                pm.setDescription(medDesc);
                selectedMedicines.add(pm);
                medicineGrid.getDataProvider().refreshAll();
                medicineComboBox.clear();
                medDescArea.clear();
            }
        });
        saveButton.addClickListener(e -> {
            try {
                Prescription prescription = new Prescription();
                prescription.setExamination(examination);
                prescription.setDoctor(examination.getDoctor());
                prescription.setPatient(examination.getPatient());
                prescription.setMedicines(selectedMedicines);

                // Prescription'ı önce kaydet
                Prescription savedPrescription = prescriptionService.save(prescription);

                // PrescriptionMedicine nesnelerine prescription'ı set et ve kaydet
                for(PrescriptionMedicine pm : selectedMedicines) {
                    pm.setPrescription(savedPrescription);
                    prescriptionMedicineService.save(pm);
                }

                com.example.application.Jaster.Prescription jasperPrescription = new com.example.application.Jaster.Prescription(
                        examination.getPatient().getPerson().getFirstName() + " " + examination.getPatient().getPerson().getLastName(),
                        examination.getDate(),
                        examination.getPatient().getPerson().getUsername(),
                        examination.getDoctor().getPerson().getFirstName() + " " + examination.getDoctor().getPerson().getLastName(),
                        prescriptionArea.getValue()
                );

                byte[] pdfBytes = createPdf.generatePdf(jasperPrescription, selectedMedicines);

                savedPrescription.setDocument(pdfBytes);
                prescriptionService.save(savedPrescription);

                Notification.show("Reçete PDF ve ilaçlar ile kaydedildi!", 3000, Notification.Position.TOP_CENTER);
                prescriptionArea.clear();
                selectedMedicines.clear();
                medicineGrid.getDataProvider().refreshAll();
            } catch (Exception ex) {
                Notification.show("Hata: " + ex.getMessage(), 5000, Notification.Position.TOP_CENTER);
            }
        });
        add(prescriptionArea, medicineComboBox, medDescArea, addMedicineButton, medicineGrid, saveButton);
    }
}
