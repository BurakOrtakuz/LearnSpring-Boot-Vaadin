package com.example.application.base.ui.view.Doctor;

import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.base.ui.layout.DoctorAppLayout;
import com.example.application.domain.Doctor;
import com.example.application.domain.Examination;
import com.example.application.domain.Person;
import com.example.application.service.DoctorService;
import com.example.application.service.ExaminationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/doctor/appointments", layout = DoctorAppLayout.class)
@RolesAllowed("DOCTOR")
public class DoctorAppointmentsView extends VerticalLayout {
    public DoctorAppointmentsView(DoctorService doctorService, ExaminationService examinationService) {
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

        List<Examination> examinations = examinationService.findAll().stream()
                .filter(e -> e.getDoctor() != null && e.getDoctor().getDoctorId().equals(doctor.getDoctorId()))
                .collect(Collectors.toList());

        List<ColumnConfig<Examination, ?>> columns = List.of(
                new ColumnConfig<>("Muayene ID", Examination::getExaminationId),
                new ColumnConfig<>("Hasta", e -> e.getPatient() != null ? e.getPatient().getPerson().getFirstName() + " " + e.getPatient().getPerson().getLastName() : "-"),
                new ColumnConfig<>("Tarih", e -> e.getDate() != null ? e.getDate().toString() : "-"),
                new ColumnConfig<>("Şikayet", Examination::getComplaint),
                new ColumnConfig<>("Reçete Yaz", e -> {
                    RouterLink link = new RouterLink("Reçete Yaz", CreatePrescriptionView.class, e.getExaminationId());
                    return link;
                })
        );

        GenericTable<Examination> table = new GenericTable<>(columns, examinations);
        add(table);
    }
}
