package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.component.ConfirmationDialog;
import com.example.application.base.ui.layout.AdminLayout;
import com.example.application.base.ui.view.PatientCard;
import com.example.application.domain.Patient;
import com.example.application.service.IPatientService;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = "/admin/patient-details", layout = AdminLayout.class)
@RolesAllowed("ADMIN")
public class AdminPatientDetails extends VerticalLayout implements HasUrlParameter<Long> {

    private final IPatientService patientService;
    private final IPrescriptionMedicineService prescriptionMedicineService;

    private PatientCard patientCard;
    private Long currentPatientId;

    public AdminPatientDetails(IPatientService patientService,
                              IPrescriptionMedicineService prescriptionMedicineService) {
        this.patientService = patientService;
        this.prescriptionMedicineService = prescriptionMedicineService;

        setSizeFull();
        setPadding(true);
        setSpacing(true);

        createHeader();
    }

    private void createHeader() {
        // Back button
        Button backButton = new Button("Geri Dön", VaadinIcon.ARROW_LEFT.create());
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("admin/user-management")));

        // Edit button
        Button editButton = new Button("Düzenle", VaadinIcon.EDIT.create());
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(e -> onEditPatient());

        Button deleteButton = new Button("Sil", VaadinIcon.TRASH.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(e -> {
            if (currentPatientId != null) {
                // Get patient name for confirmation
                patientService.findById(currentPatientId).ifPresent(patient -> {
                    String patientName = patient.getPerson().getFirstName() + " " + patient.getPerson().getLastName();

                    ConfirmationDialog.showDelete(
                        "\"" + patientName + "\" isimli hastayı",
                        () -> {
                            // Confirmed - delete the patient
                            patientService.deleteById(currentPatientId);
                            Notification.show("Hasta başarıyla silindi", 3000, Notification.Position.MIDDLE)
                                       .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            UI.getCurrent().navigate("admin/user-management");
                        }
                    );
                });
            }
        });
        // Header layout
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout rightSection = new HorizontalLayout();
        rightSection.setAlignItems(Alignment.CENTER);
        rightSection.add(editButton, deleteButton);

        headerLayout.add(backButton, rightSection);

        add(headerLayout);
    }

    @Override
    public void setParameter(BeforeEvent event, Long patientId) {
        this.currentPatientId = patientId;
        loadPatientDetails(patientId);
    }

    private void loadPatientDetails(Long patientId) {
        if (patientId == null) {
            showErrorNotification("Geçersiz hasta ID");
            return;
        }

        Optional<Patient> patientOpt = patientService.findById(patientId);

        if (patientOpt.isEmpty()) {
            showErrorNotification("Hasta bulunamadı");
            return;
        }

        Patient patient = patientOpt.get();

        if (patientCard != null) {
            remove(patientCard);
        }

        // Create new patient card
        patientCard = new PatientCard(patient.getPerson(), prescriptionMedicineService);
        patientCard.setSizeFull();

        add(patientCard);
    }

    private void onEditPatient() {
        if (currentPatientId != null) {
            // TODO: Navigate to patient edit page
            Notification.show("Hasta düzenleme sayfası henüz hazır değil", 3000, Notification.Position.MIDDLE)
                       .addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        }
    }

    private void showErrorNotification(String message) {
        Notification notification = Notification.show(message, 5000, Notification.Position.MIDDLE);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        getUI().ifPresent(ui -> ui.navigate("admin/user-management"));
    }
}
