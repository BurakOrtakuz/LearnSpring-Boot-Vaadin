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
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

        ComboBox<LocalTime> timeComboBox = new ComboBox<>("Saat Seçiniz");
        timeComboBox.setWidthFull();
        timeComboBox.setAllowCustomValue(false);

        // Saat aralıklarını oluştur
        List<LocalTime> allTimes = new ArrayList<>();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (!start.isAfter(end.minusMinutes(15))) {
            allTimes.add(start);
            start = start.plusMinutes(15);
        }

        // Doktor veya tarih değiştiğinde uygun saatleri güncelle
        doctorComboBox.addValueChangeListener(event -> updateAvailableTimes(doctorComboBox, datePicker, allTimes, timeComboBox));
        datePicker.addValueChangeListener(event -> updateAvailableTimes(doctorComboBox, datePicker, allTimes, timeComboBox));

        TextArea complaintField = new TextArea("Şikayetiniz");
        complaintField.setWidthFull();
        complaintField.setMaxLength(255);

        Button submitButton = new Button("Randevu Al");
        submitButton.addClickListener(e -> {
            try {
                Doctor selectedDoctor = doctorComboBox.getValue();
                LocalDate selectedDate = datePicker.getValue();
                LocalTime selectedTime = timeComboBox.getValue();
                String complaint = complaintField.getValue();

                if (selectedDoctor == null || selectedDate == null || selectedTime == null || complaint.isBlank()) {
                    Notification.show("Lütfen tüm alanları doldurunuz.", 3000, Notification.Position.MIDDLE);
                    return;
                }

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof Person person) {
                    patientService.findById(person.getId()).ifPresent(patient -> {
                        Examination examination = new Examination();
                        examination.setDoctor(selectedDoctor);
                        examination.setPatient(patient);
                        examination.setComplaint(complaint);
                        examination.setDate(java.sql.Timestamp.valueOf(selectedDate.atTime(selectedTime)));
                        examinationService.save(examination);
                        Notification.show("Randevunuz başarıyla oluşturuldu!", 3000, Notification.Position.TOP_CENTER);
                        doctorComboBox.clear();
                        datePicker.clear();
                        timeComboBox.clear();
                        complaintField.clear();
                    });
                }
            } catch (Exception ex) {
                Notification.show("Hata: " + ex.getMessage(), 5000, Notification.Position.TOP_CENTER);
                ex.printStackTrace();
            }
        });

        add(doctorComboBox, datePicker, timeComboBox, complaintField, submitButton);

    }

    private void updateAvailableTimes(ComboBox<Doctor> doctorComboBox, DatePicker datePicker, List<LocalTime> allTimes, ComboBox<LocalTime> timeComboBox) {
        Doctor selectedDoctor = doctorComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDoctor == null || selectedDate == null) {
            timeComboBox.setItems(new ArrayList<>());
            return;
        }
        List<Examination> taken = examinationService.findAll().stream()
                .filter(e -> e.getDoctor() != null && e.getDoctor().getDoctorId().equals(selectedDoctor.getDoctorId()))
                .filter(e -> e.getDate() != null && e.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate().equals(selectedDate))
                .toList();
        List<LocalTime> takenTimes = taken.stream()
                .map(e -> e.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0))
                .toList();
        List<LocalTime> available = allTimes.stream().filter(t -> !takenTimes.contains(t)).toList();
        timeComboBox.setItems(available);
    }
}
