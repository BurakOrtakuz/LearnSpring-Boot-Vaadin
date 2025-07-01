package com.example.application.base.ui.view.Patient;

import com.example.application.base.ui.layout.PatientMainLayout;
import com.example.application.domain.Person;
import com.example.application.dto.IPatientExaminationSearchResult;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.base.ui.component.table.GenericTable;
import com.example.application.service.ExaminationService;
import com.example.application.service.PatientService;
import com.vaadin.flow.component.html.Anchor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Route(value = "/patient", layout = PatientMainLayout.class)
@RolesAllowed("PATIENT")
public class PatientView extends VerticalLayout {

    private ExaminationService examinationService;
    private PatientService patientService;

    private List<IPatientExaminationSearchResult> examinations;
    public PatientView(ExaminationService examinationService, PatientService patientService) {
        this.examinationService = examinationService;
        this.patientService = patientService;
        examinations = null;
        System.out.println("PatientView initialized");
        setSpacing(true);
        setPadding(true);

        // Giriş yapan kullanıcıyı al
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Person person) {
            TextField searchField = new TextField();
            searchField.setWidth("50%");
            searchField.setPlaceholder("Search");
            searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
            searchField.setValueChangeMode(ValueChangeMode.EAGER);

            // Tabloyu dışarıda tanımla ki listener içinde erişebilelim
            List<ColumnConfig<IPatientExaminationSearchResult, ?>> columns = List.of(
                    new ColumnConfig<>("Tarih", e -> e.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")), true),
                    new ColumnConfig<>("Doktor", IPatientExaminationSearchResult::getDoctorName,true),
                    new ColumnConfig<>("Şikayet", IPatientExaminationSearchResult::getComplaint,true),
                    new ColumnConfig<>("Reçete", e -> {
                        if (e.getPrescriptionId() != -1) {
                            Anchor pdfLink = new Anchor("/api/prescription/pdf/" + e.getPrescriptionId(), "Reçete Görüntüle");
                            pdfLink.setTarget("_blank");
                            return pdfLink;
                        } else {
                            return new Span("-");
                        }
                    },false,true)
            );
            GenericTable<IPatientExaminationSearchResult> table = new GenericTable<>(columns, new ArrayList<>());
            add(searchField, table);

            patientService.findById(person.getId()).ifPresent(patient -> {
                // İlk yüklemede boş arama ile doldur
                List<IPatientExaminationSearchResult> examinations = examinationService.patientSearch("");
                System.out.println(examinations);
                table.setItems(examinations);
            });

            searchField.addValueChangeListener(e -> {
                String query = searchField.getValue();
                List<IPatientExaminationSearchResult> filtered = examinationService.patientSearch(query);
                table.setItems(filtered);
            });
        } else {
            add(new Text("Kullanıcı bilgileri alınamadı."));
        }
    }
}
