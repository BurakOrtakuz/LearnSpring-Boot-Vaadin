package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.layout.AdminLayout;
import com.example.application.domain.Report;
import com.example.application.service.IReportService;
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
@PageTitle("Rapor Yönetimi")
@RolesAllowed("ADMIN")
public class AdminReportView extends VerticalLayout {

    private final IReportService reportService;
    private final Grid<Report> reportGrid = new Grid<>(Report.class, false);

    // Upload dialog components
    private Dialog uploadDialog;
    private MemoryBuffer buffer = new MemoryBuffer();
    private Upload upload = new Upload(buffer);
    private ComboBox<String> mainReportSelector = new ComboBox<>("Ana Rapor Seçin");
    private TextField reportNameField = new TextField("Rapor Adı");
    private TextArea reportDescriptionField = new TextArea("Açıklama");
    private Button confirmUploadButton = new Button("Yükle");

    public AdminReportView(IReportService reportService) {
        this.reportService = reportService;
        initializeComponents();
        setupLayout();
        refreshReportGrid();
    }

    private void initializeComponents() {
        // Grid setup
        setupReportGrid();

        // Upload dialog setup
        setupUploadDialog();
    }

    private void setupReportGrid() {
        reportGrid.addColumn(Report::getReportCode).setHeader("Rapor Kodu");
        reportGrid.addColumn(Report::getName).setHeader("Rapor Adı");
        reportGrid.addColumn(Report::getDescription).setHeader("Açıklama");
        reportGrid.addColumn(report -> report.getUploadDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .setHeader("Yükleme Tarihi");

        reportGrid.addComponentColumn(report -> {
            Button deleteButton = new Button("Sil");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(e -> deleteReport(report));
            return deleteButton;
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
