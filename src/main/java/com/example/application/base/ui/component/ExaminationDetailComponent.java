package com.example.application.base.ui.component;

import com.example.application.domain.Examination;
import com.example.application.service.ExaminationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class ExaminationDetailComponent extends VerticalLayout {

    private final ExaminationService examinationService;
    private Long examinationId;
    private Examination examination;
    @Setter
    private String backNavigationTarget;

    public ExaminationDetailComponent(ExaminationService examinationService) {
        this.examinationService = examinationService;
        this.backNavigationTarget = "";

        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.STRETCH);
        getStyle().set("margin", "0 auto");
    }

    public void loadExaminationDetails(Long examinationId) {
        this.examinationId = examinationId;
        loadExaminationDetails();
    }

    private void loadExaminationDetails() {
        removeAll();

        if (examinationId == null) {
            showErrorMessage("Geçersiz randevu ID'si");
            return;
        }

        Optional<Examination> examinationOpt = examinationService.findById(examinationId);

        if (examinationOpt.isEmpty()) {
            showErrorMessage("Randevu bulunamadı");
            return;
        }

        this.examination = examinationOpt.get();
        buildExaminationDetailView();
    }

    private void buildExaminationDetailView() {
        // Başlık ve geri dönüş butonu
        HorizontalLayout headerLayout = createHeaderLayout();
        add(headerLayout);

        // Randevu genel bilgileri
        Component generalInfo = createGeneralInfoSection();
        add(generalInfo);

        // Hasta bilgileri
        Component patientInfo = createPatientInfoSection();
        add(patientInfo);

        // Doktor bilgileri
        Component doctorInfo = createDoctorInfoSection();
        add(doctorInfo);

        // Şikayet bilgileri
        Component complaintInfo = createComplaintSection();
        add(complaintInfo);
    }

    private HorizontalLayout createHeaderLayout() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        Button backButton = new Button("Geri Dön", new Icon(VaadinIcon.ARROW_LEFT));
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(backNavigationTarget)));

        H1 title = new H1("Randevu Detayları");
        title.getStyle().set("margin", "0");

        headerLayout.add(title, backButton);
        return headerLayout;
    }

    private Component createGeneralInfoSection() {
        VerticalLayout section = new VerticalLayout();
        section.setSpacing(false);
        section.setPadding(false);

        H2 sectionTitle = new H2("Genel Bilgiler");
        sectionTitle.getStyle().set("color", "var(--lumo-primary-color)");

        Div infoCard = new Div();
        infoCard.addClassName("examination-info-card");
        infoCard.getStyle()
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border-radius", "var(--lumo-border-radius-m)")
            .set("padding", "var(--lumo-space-m)")
            .set("margin-bottom", "var(--lumo-space-m)");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = examination.getDate() != null ?
            dateFormat.format(examination.getDate()) : "Belirtilmemiş";

        HorizontalLayout idLayout = createInfoRow("Randevu ID:", examination.getExaminationId().toString());
        HorizontalLayout dateLayout = createInfoRow("Tarih:", formattedDate);

        infoCard.add(idLayout, dateLayout);
        section.add(sectionTitle, infoCard);
        return section;
    }

    private Component createPatientInfoSection() {
        VerticalLayout section = new VerticalLayout();
        section.addClickListener(e -> getUI().ifPresent(ui -> {
            ui.navigate("doctor/patient-details/" + examination.getPatient().getPatientId());
        }));
        section.setSpacing(false);
        section.setPadding(false);

        H2 sectionTitle = new H2("Hasta Bilgileri");
        sectionTitle.getStyle().set("color", "var(--lumo-primary-color)");

        Div patientCard = new Div();
        patientCard.addClassName("patient-info-card");
        patientCard.getStyle()
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border-radius", "var(--lumo-border-radius-m)")
            .set("padding", "var(--lumo-space-m)")
            .set("margin-bottom", "var(--lumo-space-m)");

        if (examination.getPatient() != null && examination.getPatient().getPerson() != null) {
            String patientName = examination.getPatient().getPerson().getFirstName() + " " +
                               examination.getPatient().getPerson().getLastName();
            String patientEmail = examination.getPatient().getPerson().getEmail() != null ?
                                examination.getPatient().getPerson().getEmail() : "Belirtilmemiş";

            HorizontalLayout nameLayout = createInfoRow("Ad Soyad:", patientName);
            HorizontalLayout emailLayout = createInfoRow("E-posta:", patientEmail);
            HorizontalLayout idLayout = createInfoRow("Hasta ID:", examination.getPatient().getPatientId().toString());

            patientCard.add(nameLayout, emailLayout, idLayout);
        } else {
            patientCard.add(new Span("Hasta bilgileri bulunamadı"));
        }

        section.add(sectionTitle, patientCard);
        return section;
    }

    private Component createDoctorInfoSection() {
        VerticalLayout section = new VerticalLayout();
        section.setSpacing(false);
        section.setPadding(false);

        H2 sectionTitle = new H2("Doktor Bilgileri");
        sectionTitle.getStyle().set("color", "var(--lumo-primary-color)");

        Div doctorCard = new Div();
        doctorCard.addClassName("doctor-info-card");
        doctorCard.getStyle()
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border-radius", "var(--lumo-border-radius-m)")
            .set("padding", "var(--lumo-space-m)")
            .set("margin-bottom", "var(--lumo-space-m)");

        if (examination.getDoctor() != null && examination.getDoctor().getPerson() != null) {
            String doctorName = "Dr. " + examination.getDoctor().getPerson().getFirstName() + " " +
                              examination.getDoctor().getPerson().getLastName();
            String branch = examination.getDoctor().getBranch() != null ?
                          examination.getDoctor().getBranch() : "Belirtilmemiş";
            String doctorEmail = examination.getDoctor().getPerson().getEmail() != null ?
                               examination.getDoctor().getPerson().getEmail() : "Belirtilmemiş";

            HorizontalLayout nameLayout = createInfoRow("Doktor:", doctorName);
            HorizontalLayout branchLayout = createInfoRow("Branş:", branch);
            HorizontalLayout emailLayout = createInfoRow("E-posta:", doctorEmail);
            HorizontalLayout idLayout = createInfoRow("Doktor ID:", examination.getDoctor().getDoctorId().toString());

            doctorCard.add(nameLayout, branchLayout, emailLayout, idLayout);
        } else {
            doctorCard.add(new Span("Doktor bilgileri bulunamadı"));
        }

        section.add(sectionTitle, doctorCard);
        return section;
    }

    private Component createComplaintSection() {
        VerticalLayout section = new VerticalLayout();
        section.setSpacing(false);
        section.setPadding(false);

        H2 sectionTitle = new H2("Şikayet");
        sectionTitle.getStyle().set("color", "var(--lumo-primary-color)");

        Div complaintCard = new Div();
        complaintCard.addClassName("complaint-info-card");
        complaintCard.getStyle()
            .set("background", "var(--lumo-contrast-5pct)")
            .set("border-radius", "var(--lumo-border-radius-m)")
            .set("padding", "var(--lumo-space-m)")
            .set("margin-bottom", "var(--lumo-space-m)")
            .set("white-space", "pre-wrap");

        String complaint = examination.getComplaint() != null && !examination.getComplaint().trim().isEmpty()
            ? examination.getComplaint()
            : "Şikayet belirtilmemiş";

        Span complaintText = new Span(complaint);
        complaintText.getStyle()
            .set("font-size", "var(--lumo-font-size-m)")
            .set("line-height", "1.6");

        complaintCard.add(complaintText);
        section.add(sectionTitle, complaintCard);
        return section;
    }

    private HorizontalLayout createInfoRow(String label, String value) {
        HorizontalLayout row = new HorizontalLayout();
        row.setSpacing(true);
        row.setAlignItems(Alignment.CENTER);
        row.setWidthFull();

        Span labelSpan = new Span(label);
        labelSpan.getStyle()
            .set("font-weight", "600")
            .set("color", "var(--lumo-secondary-text-color)")
            .set("min-width", "120px");

        Span valueSpan = new Span(value);
        valueSpan.getStyle()
            .set("font-weight", "400");

        row.add(labelSpan, valueSpan);
        return row;
    }

    private void showErrorMessage(String message) {
        // Hata sayfası
        VerticalLayout errorLayout = new VerticalLayout();
        errorLayout.setAlignItems(Alignment.CENTER);
        errorLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        errorLayout.setSizeFull();

        Icon errorIcon = new Icon(VaadinIcon.EXCLAMATION_CIRCLE);
        errorIcon.setSize("64px");
        errorIcon.getStyle().set("color", "var(--lumo-error-color)");

        H2 errorTitle = new H2("Hata");
        errorTitle.getStyle().set("color", "var(--lumo-error-color)");

        Span errorMessage = new Span(message);
        errorMessage.getStyle().set("font-size", "var(--lumo-font-size-l)");

        Button homeButton = new Button("Ana Sayfaya Dön", new Icon(VaadinIcon.HOME));
        homeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        homeButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));

        errorLayout.add(errorIcon, errorTitle, errorMessage, homeButton);
        add(errorLayout);

        // Bildirim göster
        Notification notification = Notification.show(message);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
    }
}
