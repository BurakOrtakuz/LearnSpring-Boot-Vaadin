package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.domain.Doctor;
import com.example.application.domain.Person;
import com.example.application.dto.IDoctorExaminationSearchResult;
import com.example.application.service.DoctorService;
import com.example.application.service.ExaminationService;
import com.example.application.service.PatientService;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = "/doctor/appointments", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorAppointmentsView extends VerticalLayout {
    private final PatientService patientService;

    public DoctorAppointmentsView(DoctorService doctorService, ExaminationService examinationService, PatientService patientService) {
        this.patientService = patientService;
        
        setSpacing(true);
        setPadding(true);
        setWidthFull();

        // Giriş yapan doktorun PersonId'sini al
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Person person)) {
            add("Kullanıcı bilgileri alınamadı.");
            return;
        }
        Doctor doctor = doctorService.findById(person.getId()).orElse(null);
        if (doctor == null) {
            add("Doktor bulunamadı.");
            return;
        }
        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);

        List<ColumnConfig<IDoctorExaminationSearchResult, ?>> columns = List.of(
                new ColumnConfig<>("Hasta",IDoctorExaminationSearchResult::getPatientName, true),
                new ColumnConfig<>("Tarih", e -> e.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), true),
                new ColumnConfig<>("Şikayet", IDoctorExaminationSearchResult::getComplaint, true),
                new ColumnConfig<>("Reçete Yaz", e -> {
                    if( e.getPrescriptionId() != -1) {
                        HorizontalLayout layout = new HorizontalLayout();
                        layout.setSpacing(true);
                        layout.add(new RouterLink("Reçeteyi Güncelle", CreatePrescriptionView.class, (long)e.getExaminationId()));
                        layout.add(new Anchor("/api/prescription/pdf/" + e.getPrescriptionId(), "Reçete Görüntüle"));
                        return layout;
                    }
                    return new RouterLink("Reçete Yaz", CreatePrescriptionView.class, (long)e.getExaminationId());
                }, false,true)
        );

        GenericTable<IDoctorExaminationSearchResult> table = new GenericTable<>(columns, new ArrayList<>());
        table.addItemDoubleClickListener(event -> {
            IDoctorExaminationSearchResult examination = event.getItem();
            if (examination != null) {
                // Hasta detay sayfasına git
                getUI().ifPresent(ui -> ui.navigate("doctor/patient-details/" + examination.getPatientId()));
            }
        });
        add(searchField,table);

        doctorService.findById(person.getId()).ifPresent(d -> {
            List<IDoctorExaminationSearchResult> examinations = examinationService.doctorSearch("");
            table.setItems(examinations);
        });

        searchField.addValueChangeListener(event -> {
           String query = searchField.getValue();
           List<IDoctorExaminationSearchResult> filteredExamination = examinationService.doctorSearch(query);
           table.setItems(filteredExamination);
        });
    }
}
