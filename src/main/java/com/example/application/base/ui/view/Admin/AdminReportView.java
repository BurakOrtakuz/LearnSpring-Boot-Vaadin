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
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value = "admin/reports", layout = AdminLayout.class)
@RolesAllowed("ADMIN")
public class AdminReportView extends VerticalLayout {

    private final IReportService reportService;
    private final IReportUsageService reportUsageService;
    private final Grid<Report> reportGrid = new Grid<>(Report.class, false);

    // Upload dialog components
    private Dialog uploadDialog;
    private MemoryBuffer buffer;
    private Upload upload;
    private ComboBox<String> mainReportSelector;
    private TextField reportNameField;
    private TextArea reportDescriptionField;
    private Button confirmUploadButton;

    // Update dialog components
    private Dialog updateDialog;
    private MemoryBuffer updateBuffer;
    private Upload updateUpload;
    private ComboBox<String> updateMainReportSelector;
    private TextField updateReportNameField;
    private TextArea updateReportDescriptionField;
    private Button confirmUpdateButton;
    private Button updateWithoutZipButton;
    private Report currentUpdatingReport;

    public AdminReportView(IReportService reportService, IReportUsageService reportUsageService) {
        this.reportService = reportService;
        this.reportUsageService = reportUsageService;
        this.buffer = new MemoryBuffer();
        this.upload = new Upload(buffer);
        this.mainReportSelector = new ComboBox<>("Ana Rapor Seçin");
        this.reportNameField = new TextField("Rapor Adı");
        this.reportDescriptionField = new TextArea("Açıklama");
        this.confirmUploadButton = new Button("Yükle");

        // Update dialog components initialization
        this.updateBuffer = new MemoryBuffer();
        this.updateUpload = new Upload(updateBuffer);
        this.updateMainReportSelector = new ComboBox<>("Ana Rapor Seçin");
        this.updateReportNameField = new TextField("Rapor Adı");
        this.updateReportDescriptionField = new TextArea("Açıklama");
        this.confirmUpdateButton = new Button("Güncelle");
        this.updateWithoutZipButton = new Button("Zip Olmadan Güncelle");

        initializeComponents();
        setupLayout();
        refreshReportGrid();
    }

    private void initializeComponents() {
        // Grid setup
        setupReportGrid();

        // Upload dialog setup
        setupUploadDialog();

        // Update dialog setup
        setupUpdateDialog();
    }

    private void setupReportGrid() {
        reportGrid.addColumn(Report::getReportCode).setHeader("Rapor Kodu");
        reportGrid.addColumn(Report::getName).setHeader("Rapor Adı");
        reportGrid.addColumn(Report::getDescription).setHeader("Açıklama");
        reportGrid.addColumn(report -> report.getUploadDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .setHeader("Yükleme Tarihi");

        reportGrid.addComponentColumn(report -> {
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            Button deleteButton = new Button("Sil");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> deleteReport(report));

            Button updateButton = new Button("Güncelle");
            updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            updateButton.addClickListener(e -> openUpdateDialog(report));
            horizontalLayout.add(deleteButton, updateButton);
            return horizontalLayout;
        }).setHeader("İşlemler");
        reportGrid.setSizeFull();
    }

    private void setupUploadDialog() {
        uploadDialog = new Dialog();
        uploadDialog.setHeaderTitle("Rapor Yükle");
        uploadDialog.setWidth("500px");

        // Upload component setup
        upload.setAcceptedFileTypes(".zip");
        upload.setMaxFileSize(10 * 1024 * 1024); // 10MB
        upload.addSucceededListener(event -> {
            try {
                List<String> jasperFiles = reportService.getJasperFilesFromZip(
                    new SpringMultipartFile(buffer.getFileName(), buffer.getInputStream())
                );

                if (jasperFiles.isEmpty()) {
                    Notification.show("Zip dosyasında .jasper dosyası bulunamadı!", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                mainReportSelector.setItems(jasperFiles);
                mainReportSelector.setVisible(true);
                confirmUploadButton.setEnabled(false);

                Notification.show("Dosya başarıyla yüklendi. Ana raporu seçin.", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (Exception e) {
                Notification.show("Dosya işlenirken hata: " + e.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Main report selector setup
        mainReportSelector.setVisible(false);
        mainReportSelector.addValueChangeListener(event -> {
            confirmUploadButton.setEnabled(event.getValue() != null &&
                !reportNameField.getValue().trim().isEmpty());
        });

        // Report name field setup
        reportNameField.setRequired(true);
        reportNameField.addValueChangeListener(event -> {
            confirmUploadButton.setEnabled(mainReportSelector.getValue() != null &&
                !event.getValue().trim().isEmpty());
        });

        // Description field setup
        reportDescriptionField.setHeight("100px");

        // Confirm button setup
        confirmUploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmUploadButton.setEnabled(false);
        confirmUploadButton.addClickListener(event -> performUpload());

        // Cancel button
        Button cancelButton = new Button("İptal", event -> closeUploadDialog());

        // Dialog layout
        VerticalLayout dialogContent = new VerticalLayout(
            new Paragraph("Jasper raporlarını içeren zip dosyasını seçin:"),
            upload,
            mainReportSelector,
            reportNameField,
            reportDescriptionField
        );

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmUploadButton, cancelButton);

        uploadDialog.add(dialogContent);
        uploadDialog.getFooter().add(buttonLayout);
    }

    private void setupUpdateDialog() {
        updateDialog = new Dialog();
        updateDialog.setHeaderTitle("Rapor Güncelle");
        updateDialog.setWidth("500px");

        // Update component setup
        updateUpload.setAcceptedFileTypes(".zip");
        updateUpload.setMaxFileSize(10 * 1024 * 1024); // 10MB
        updateUpload.addSucceededListener(event -> {
            try {
                List<String> jasperFiles = reportService.getJasperFilesFromZip(
                    new SpringMultipartFile(updateBuffer.getFileName(), updateBuffer.getInputStream())
                );

                if (jasperFiles.isEmpty()) {
                    Notification.show("Zip dosyasında .jasper dosyası bulunamadı!", 3000, Notification.Position.MIDDLE)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }

                updateMainReportSelector.setItems(jasperFiles);
                updateMainReportSelector.setVisible(true);
                confirmUpdateButton.setEnabled(false);

                Notification.show("Dosya başarıyla yüklendi. Ana raporu seçin.", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            } catch (Exception e) {
                Notification.show("Dosya işlenirken hata: " + e.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });

        // Main report selector setup
        updateMainReportSelector.setVisible(false);
        updateMainReportSelector.addValueChangeListener(event -> {
            confirmUpdateButton.setEnabled(event.getValue() != null &&
                !updateReportNameField.getValue().trim().isEmpty());
        });

        // Report name field setup
        updateReportNameField.setRequired(true);
        updateReportNameField.addValueChangeListener(event -> {
            confirmUpdateButton.setEnabled(updateMainReportSelector.getValue() != null &&
                !event.getValue().trim().isEmpty());
        });

        // Description field setup
        updateReportDescriptionField.setHeight("100px");

        // Confirm button setup
        confirmUpdateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmUpdateButton.setEnabled(false);
        confirmUpdateButton.addClickListener(event -> performUpdate());

        // Update without zip button
        updateWithoutZipButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateWithoutZipButton.addClickListener(event -> performUpdateWithoutZip());

        // Cancel button
        Button cancelButton = new Button("İptal", event -> closeUpdateDialog());

        // Dialog layout
        VerticalLayout dialogContent = new VerticalLayout(
            new Paragraph("Güncellenmiş jasper raporlarını içeren zip dosyasını seçin veya sadece rapor adını değiştirin:"),
            updateUpload,
            updateMainReportSelector,
            updateReportNameField,
            updateReportDescriptionField
        );

        HorizontalLayout buttonLayout = new HorizontalLayout(confirmUpdateButton, updateWithoutZipButton, cancelButton);

        updateDialog.add(dialogContent);
        updateDialog.getFooter().add(buttonLayout);
    }

    private void setupLayout() {
        H2 title = new H2("Rapor Yönetimi");

        Button addReportButton = new Button("Yeni Rapor Yükle");
        addReportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addReportButton.addClickListener(event -> openUploadDialog());

        HorizontalLayout headerLayout = new HorizontalLayout(title, addReportButton);
        headerLayout.setWidthFull();
        headerLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        headerLayout.setAlignItems(Alignment.CENTER);

        add(headerLayout, reportGrid);
        setSizeFull();
        setPadding(true);
    }

    private void openUploadDialog() {
        // Reset dialog state
        buffer = new MemoryBuffer();
        upload.setReceiver(buffer);
        mainReportSelector.clear();
        mainReportSelector.setVisible(false);
        reportNameField.clear();
        reportDescriptionField.clear();
        confirmUploadButton.setEnabled(false);

        uploadDialog.open();
    }

    private void closeUploadDialog() {
        uploadDialog.close();
    }

    private void performUpload() {
        try {
            String selectedMainReport = mainReportSelector.getValue();
            String reportName = reportNameField.getValue().trim();
            String reportDescription = reportDescriptionField.getValue().trim();

            MultipartFile zipFile = new SpringMultipartFile(buffer.getFileName(), buffer.getInputStream());

            Report savedReport = reportService.uploadReportFromZip(
                zipFile, selectedMainReport, reportName, reportDescription
            );

            Notification.show("Rapor başarıyla yüklendi! Kod: " + savedReport.getReportCode(),
                5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            refreshReportGrid();
            closeUploadDialog();

        } catch (Exception e) {
            Notification.show("Rapor yüklenirken hata: " + e.getMessage(),
                5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void openUpdateDialog(Report report) {
        currentUpdatingReport = report;

        // Reset dialog state
        updateBuffer = new MemoryBuffer();
        updateUpload.setReceiver(updateBuffer);
        updateMainReportSelector.clear();
        updateMainReportSelector.setVisible(false);
        updateReportNameField.clear();
        updateReportDescriptionField.clear();
        confirmUpdateButton.setEnabled(false);

        // Set current report data
        updateReportNameField.setValue(report.getName());
        updateReportDescriptionField.setValue(report.getDescription());

        updateDialog.open();
    }

    private void closeUpdateDialog() {
        updateDialog.close();
    }

    private void performUpdate() {
        try {
            String selectedMainReport = updateMainReportSelector.getValue();
            String reportName = updateReportNameField.getValue().trim();
            String reportDescription = updateReportDescriptionField.getValue().trim();

            // Validasyon kontrolü
            if (selectedMainReport == null || selectedMainReport.trim().isEmpty()) {
                updateMainReportSelector.setHelperText("Ana rapor seçilmelidir!");
                return;
            }

            if (reportName.isEmpty()) {
                updateReportNameField.setHelperText("Rapor adı boş olamaz!");
                return;
            }

            if (currentUpdatingReport == null) {
                Notification.show("Güncellenecek rapor bulunamadı!", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            MultipartFile zipFile = new SpringMultipartFile(updateBuffer.getFileName(), updateBuffer.getInputStream());

            Report updatedReport = reportService.updateReportWithZip(
                currentUpdatingReport.getReportId(), zipFile, selectedMainReport, reportName, reportDescription
            );

            Notification.show("Rapor başarıyla güncellendi! Kod: " + updatedReport.getReportCode(),
                5000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            refreshReportGrid();
            closeUpdateDialog();

        } catch (Exception e) {
            String errorMessage = "Rapor güncellenirken hata: ";
            if (e.getMessage() != null && !e.getMessage().trim().isEmpty()) {
                errorMessage += e.getMessage();
            } else {
                errorMessage += "Bilinmeyen hata (" + e.getClass().getSimpleName() + ")";
            }

            Notification.show(errorMessage, 5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void performUpdateWithoutZip() {
        try {
            String reportName = updateReportNameField.getValue().trim();
            String reportDescription = updateReportDescriptionField.getValue().trim();

            if (reportName.isEmpty()) {
                Notification.show("Rapor adı boş olamaz!", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            Report updatedReport = reportService.updateReport(
                currentUpdatingReport.getReportId(), reportName, reportDescription
            );

            Notification.show("Rapor başarıyla güncellendi! Kod: " + updatedReport.getReportCode(),
                5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

            refreshReportGrid();
            closeUpdateDialog();

        } catch (Exception e) {
            Notification.show("Rapor güncellenirken hata: " + e.getMessage(),
                5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void deleteReport(Report report) {
        try {
            reportService.deleteReport(report.getReportId());
            Notification.show("Rapor silindi: " + report.getName(), 3000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            refreshReportGrid();
        } catch (Exception e) {
            Notification.show("Rapor silinirken hata: " + e.getMessage(), 5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void refreshReportGrid() {
        reportGrid.setItems(reportService.getAllReports());
    }

    // Helper class for MultipartFile implementation
    private static class SpringMultipartFile implements MultipartFile {
        private final String fileName;
        private final java.io.InputStream inputStream;

        public SpringMultipartFile(String fileName, java.io.InputStream inputStream) {
            this.fileName = fileName;
            this.inputStream = inputStream;
        }

        @Override
        public String getName() { return "file"; }

        @Override
        public String getOriginalFilename() { return fileName; }

        @Override
        public String getContentType() { return "application/zip"; }

        @Override
        public boolean isEmpty() {
            try { return inputStream.available() == 0; }
            catch (IOException e) { return true; }
        }

        @Override
        public long getSize() {
            try { return inputStream.available(); }
            catch (IOException e) { return 0; }
        }

        @Override
        public byte[] getBytes() throws IOException {
            return inputStream.readAllBytes();
        }

        @Override
        public java.io.InputStream getInputStream() { return inputStream; }

        @Override
        public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
            // Not implemented for this use case
        }
    }
}
