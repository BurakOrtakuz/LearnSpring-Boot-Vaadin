package com.example.application.taskmanagement.ui.view.Doctor;

import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.base.ui.component.FooterBar;
import com.example.application.Jaster.CreatePdf;
import com.example.application.Jaster.Prescription;
import com.example.application.Jaster.Medicine;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Route(value = "/create-prescription", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class CreatePrescriptionView extends VerticalLayout {
    @Autowired
    private CreatePdf createPdf;

    public CreatePrescriptionView() {
        Text welcomeText = new Text("Reçete Oluştur");
        add(new FooterBar());
        add(welcomeText);

        // Form alanları
        TextField nameField = new TextField("Hasta Adı Soyadı");
        DatePicker dateField = new DatePicker("Tarih");
        TextField tcNoField = new TextField("T.C. Kimlik No");
        TextField drNameField = new TextField("Doktor Adı Soyadı");
        TextArea diagnosisField = new TextArea("Tanı");
        TextArea medicinesField = new TextArea("İlaçlar (Her satıra bir ilaç: ad - açıklama)");
        medicinesField.setPlaceholder("Parol - Ağrı kesici\nAugmentin - Antibiyotik");

        FormLayout form = new FormLayout();
        form.add(nameField, dateField, tcNoField, drNameField, diagnosisField, medicinesField);
        add(form);

        Button pdfButton = new Button("PDF İndir");
        Anchor downloadLink = new Anchor();
        downloadLink.add(pdfButton);
        downloadLink.getElement().setAttribute("download", true);
        add(downloadLink);

        pdfButton.addClickListener(e -> {
            try {
                String name = nameField.getValue();
                Date date = dateField.getValue() != null ? Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
                String tcNo = tcNoField.getValue();
                String drName = drNameField.getValue();
                String diagnosis = diagnosisField.getValue();
                String medicinesRaw = medicinesField.getValue();
                List<Medicine> medicines = new ArrayList<>();
                if (medicinesRaw != null && !medicinesRaw.isBlank()) {
                    for (String line : medicinesRaw.split("\\n")) {
                        String[] parts = line.split("-");
                        if (parts.length >= 2) {
                            medicines.add(new Medicine(parts[0].trim(), parts[1].trim()));
                        } else if (parts.length == 1) {
                            medicines.add(new Medicine(parts[0].trim(), ""));
                        }
                    }
                }
                Prescription prescription = new Prescription(name, date, tcNo, drName, diagnosis);
                byte[] pdfBytes = createPdf.generatePdf(prescription, medicines);
                StreamResource resource = new StreamResource("recete.pdf", () -> new ByteArrayInputStream(pdfBytes));
                downloadLink.setHref(resource);
                // Otomatik indirme için anchor'ı tıkla
                downloadLink.getElement().executeJs("this.click()");
            } catch (Exception ex) {
                Notification.show("PDF oluşturulamadı: " + ex.getMessage(), 5000, Notification.Position.MIDDLE);
            }
        });
    }
}
