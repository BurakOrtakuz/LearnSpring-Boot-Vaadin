package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.layout.AdminLayout;
import com.example.application.domain.Report;
import com.example.application.domain.ReportUsage;
import com.example.application.service.IReportService;
import com.example.application.service.IReportUsageService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "admin/report-usages", layout = AdminLayout.class)
@RolesAllowed("ADMIN")
public class AdminReportUsageView extends VerticalLayout {

    private final IReportUsageService reportUsageService;
    private final IReportService reportService;
    private final Grid<ReportUsage> usageGrid = new Grid<>(ReportUsage.class, false);

    // Dialog components
    private Dialog usageDialog;
    private TextField operationNameField;
    private ComboBox<Report> reportSelector;
    private Button confirmButton;
    private ReportUsage currentEditingUsage;

    public AdminReportUsageView(IReportUsageService reportUsageService, IReportService reportService) {
        this.reportUsageService = reportUsageService;
        this.reportService = reportService;

        initializeComponents();
        setupLayout();
        refreshUsageGrid();
    }

    private void initializeComponents() {
        setupUsageGrid();
        setupUsageDialog();
    }

    private void setupUsageGrid() {
        usageGrid.addColumn(usage -> usage.getOperationName()).setHeader("İşlem Adı");
        usageGrid.addColumn(usage -> usage.getReport().getName()).setHeader("Rapor Adı");
        usageGrid.addColumn(usage -> usage.getReport().getReportCode()).setHeader("Rapor Kodu");
        usageGrid.addColumn(usage -> usage.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .setHeader("Oluşturma Tarihi");

        usageGrid.addComponentColumn(usage -> {
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            Button editButton = new Button("Düzenle");
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(e -> openEditDialog(usage));

            Button deleteButton = new Button("Sil");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> deleteUsage(usage));

            horizontalLayout.add(editButton, deleteButton);
            return horizontalLayout;
        }).setHeader("İşlemler");

        usageGrid.setSizeFull();
    }

    private void setupUsageDialog() {
        usageDialog = new Dialog();
        usageDialog.setWidth("400px");

        operationNameField = new TextField("İşlem Adı");
        operationNameField.setRequired(true);
        operationNameField.setWidth("100%");

        reportSelector = new ComboBox<>("Rapor Seçin");
        reportSelector.setRequired(true);
        reportSelector.setWidth("100%");
        reportSelector.setItemLabelGenerator(report -> report.getName() + " (" + report.getReportCode() + ")");

        confirmButton = new Button();
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmButton.addClickListener(e -> saveUsage());

        Button cancelButton = new Button("İptal", e -> closeDialog());

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmButton, cancelButton);

        VerticalLayout dialogContent = new VerticalLayout(
                operationNameField,
                reportSelector,
                buttonLayout
        );

        usageDialog.add(dialogContent);
    }

    private void setupLayout() {
        H2 title = new H2("Rapor Kullanım Yönetimi");

        Button addUsageButton = new Button("Yeni İşlem Ekle");
        addUsageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addUsageButton.addClickListener(e -> openAddDialog());

        HorizontalLayout headerLayout = new HorizontalLayout(title, addUsageButton);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        add(headerLayout, usageGrid);
        setSizeFull();
        setPadding(true);
    }

    private void openAddDialog() {
        currentEditingUsage = null;
        usageDialog.setHeaderTitle("Yeni İşlem Ekle");
        confirmButton.setText("Ekle");

        // Form temizle
        operationNameField.clear();
        reportSelector.clear();

        // Raporları yükle
        reportSelector.setItems(reportService.getAllReports());

        usageDialog.open();
    }

    private void openEditDialog(ReportUsage usage) {
        currentEditingUsage = usage;
        usageDialog.setHeaderTitle("İşlem Düzenle");
        confirmButton.setText("Güncelle");

        // Form doldur
        operationNameField.setValue(usage.getOperationName());
        reportSelector.setItems(reportService.getAllReports());
        reportSelector.setValue(usage.getReport());

        usageDialog.open();
    }

    private void closeDialog() {
        usageDialog.close();
    }

    private void saveUsage() {
        String operationName = operationNameField.getValue().trim();
        Report selectedReport = reportSelector.getValue();

        if (operationName.isEmpty()) {
            Notification.show("İşlem adı boş olamaz!", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        if (selectedReport == null) {
            Notification.show("Rapor seçilmelidir!", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        try {
            if (currentEditingUsage == null) {
                // Yeni kayıt
                reportUsageService.createReportUsage(operationName, selectedReport.getReportId());
                Notification.show("İşlem başarıyla eklendi!", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } else {
                // Güncelleme
                reportUsageService.updateReportUsage(currentEditingUsage.getUsageId(), operationName, selectedReport.getReportId());
                Notification.show("İşlem başarıyla güncellendi!", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }

            refreshUsageGrid();
            closeDialog();

        } catch (Exception e) {
            Notification.show("Hata: " + e.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void deleteUsage(ReportUsage usage) {
        try {
            reportUsageService.deleteReportUsage(usage.getUsageId());
            Notification.show("İşlem silindi: " + usage.getOperationName(), 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            refreshUsageGrid();
        } catch (Exception e) {
            Notification.show("Silme hatası: " + e.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void refreshUsageGrid() {
        usageGrid.setItems(reportUsageService.getAllReportUsages());
    }
}
