package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.domain.Doctor;
import com.example.application.domain.Examination;
import com.example.application.domain.Patient;
import com.example.application.domain.Person;
import com.example.application.service.DoctorService;
import com.example.application.service.ExaminationService;
import com.example.application.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@Route(value = "/patient/new-appointment", layout = PatientMainLayout.class)
@RolesAllowed("PATIENT")
public class NewAppointmentsView extends VerticalLayout {
    private final DoctorService doctorService;
    private final ExaminationService examinationService;
    private final PatientService patientService;

    public NewAppointmentsView(DoctorService doctorService, ExaminationService examinationService, PatientService patientService) {
        this.doctorService = doctorService;
        this.examinationService = examinationService;
        this.patientService = patientService;

        setSpacing(true);
        setPadding(true);
        setWidthFull();
        setMaxWidth("500px");

        ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doktor Seçiniz");
        List<Doctor> doctors = doctorService.findAll();
        doctorComboBox.setItems(doctors);
        doctorComboBox.setItemLabelGenerator(doc -> doc.getPerson().getFirstName() + " " + doc.getPerson().getLastName() + " (" + doc.getBranch() + ")");

        DatePicker datePicker = new DatePicker("Tarih Seçiniz");
        datePicker.setMin(LocalDate.now());

        TextArea complaintField = new TextArea("Şikayetiniz");
        complaintField.setWidthFull();
        complaintField.setMaxLength(255);
        Button submitButton = new Button("Randevu Al");
        submitButton.addClickListener(e -> {
            System.out.println("I remember years ago someone told me i should take caution when it comes to love, I did");
            Doctor selectedDoctor = doctorComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();
            String complaint = complaintField.getValue();

            if (selectedDoctor == null || selectedDate == null || complaint.isBlank()) {
                Notification.show("Lütfen tüm alanları doldurunuz.", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Giriş yapan kullanıcıdan Patient bul
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof Person person) {
                patientService.findById(person.getId()).ifPresent(patient -> {
                    Examination examination = new Examination();
                    examination.setDoctor(selectedDoctor);
                    examination.setPatient(patient);
                    examination.setComplaint(complaint);
                    // İstersen burada examination'a tarih alanı ekleyebilirsin (entity'de yoksa eklenmeli)
                    examinationService.save(examination);
                    Notification.show("Randevunuz başarıyla oluşturuldu!", 3000, Notification.Position.TOP_CENTER);
                    doctorComboBox.clear();
                    datePicker.clear();
                    complaintField.clear();
                });
            }
        });

        add(doctorComboBox, datePicker, complaintField, submitButton);
    }
}
