package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.component.MainNavbar;
import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.domain.Person;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.domain.Examination;
import com.example.application.domain.Patient;
import com.example.application.service.ExaminationService;
import com.example.application.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/patient", layout = PatientMainLayout.class)
@RolesAllowed("PATIENT")
public class PatientView extends VerticalLayout {

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private PatientService patientService;

    public PatientView(ExaminationService examinationService, PatientService patientService) {
        this.examinationService = examinationService;
        this.patientService = patientService;
        System.out.println("PatientView initialized");
        setSpacing(true);
        setPadding(true);

        // Giriş yapan kullanıcıyı al
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal: " + principal);
        if (principal instanceof Person person) {
            add(new Text("Ad: " + person.getFirstName()));
            add(new Text("Soyad: " + person.getLastName()));
            add(new Text("E-posta: " + person.getEmail()));
            add(new Text("Doğum Tarihi: " + (person.getBirthDate() != null ? person.getBirthDate().toString() : "-")));
            add(new Text("Cinsiyet: " + (person.getGender() != null ? person.getGender() : "-")));
            add(new Text("Telefon: " + (person.getPhoneNumber() != null ? person.getPhoneNumber() : "-")));
            add(new Text("Adres: " + (person.getAddress() != null ? person.getAddress() : "-")));

            // PersonId ile Patient bul
            patientService.findById(person.getId()).ifPresent(patient -> {
                List<Examination> examinations = examinationService.findAll().stream()
                        .filter(e -> e.getPatient() != null && e.getPatient().getPatientId().equals(patient.getPatientId()))
                        .collect(Collectors.toList());
                List<ColumnConfig<Examination, ?>> columns = List.of(
                        new ColumnConfig<>("Muayene ID", Examination::getExaminationId),
                        new ColumnConfig<>("Doktor", e -> e.getDoctor() != null ? e.getDoctor().getPerson().getFirstName() + " " + e.getDoctor().getPerson().getLastName() : "-"),
                        new ColumnConfig<>("Şikayet", Examination::getComplaint)
                );
                System.out.println("Examinations: " + examinations);
                GenericTable<Examination> table = new GenericTable<>(columns, examinations);
                add(table);
            });
        } else {
            add(new Text("Kullanıcı bilgileri alınamadı."));
        }
    }
}