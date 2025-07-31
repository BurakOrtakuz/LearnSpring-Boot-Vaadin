package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.domain.Patient;
import com.example.application.service.IPatientService;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PatientManagementView extends BaseManagementView<Patient> {

    private final IPatientService patientService;

    public PatientManagementView(IPatientService patientService) {
        super();
        this.patientService = patientService;
        super.init();
        filterBar.setReloadCallback(this::applyFilters);
    }

    @Override
    public List<ColumnConfig<Patient, ?>> getColumnConfigs() {
        return List.of(
                new ColumnConfig<>("Ad", e-> e.getPerson().getFirstName(), true),
                new ColumnConfig<>("Soyad", e -> e.getPerson().getLastName(), true),
                new ColumnConfig<>("TC No", e -> e.getPerson().getTcNo(), true),
                new ColumnConfig<>("Kan Grubu", Patient::getBloodType, true),
                new ColumnConfig<>("Telefon", e -> e.getPerson().getPhoneNumber(), true),
                new ColumnConfig<>("E-posta", e -> e.getPerson().getEmail(), true),
                new ColumnConfig<>("Doğum Tarihi", e -> e.getPerson().getBirthDate().toString(), true),
                new ColumnConfig<>("Cinsiyet", e -> e.getPerson().getGender(), true),
                new ColumnConfig<>("Adres", e -> e.getPerson().getAddress(), true)
                );
    }

    @Override
    public Page<Patient> loadData(int page, int size) {
        // Get current filter values
        Map<String, Object> filters = filterBar != null ? filterBar.getFilterValues() : Map.of();

        PatientFilterCriteria criteria = PatientFilterCriteria.builder()
                .firstName((String) filters.getOrDefault("firstName", ""))
                .lastName((String) filters.getOrDefault("lastName", ""))
                .tcNo((String) filters.getOrDefault("tcNo", ""))
                .bloodType((String) filters.getOrDefault("bloodType", ""))
                .phoneNumber((String) filters.getOrDefault("phoneNumber", ""))
                .email((String) filters.getOrDefault("email", ""))
                .birthDate(parseBirthDate(filters.get("birthDate")))
                .gender((String) filters.get("gender"))
                .address((String) filters.getOrDefault("address", ""))
                .build();

        Page<Patient> result = patientService.findWithFilters(criteria, PageRequest.of(page, size));

        // Update table and pagination
        updateTableData(result);

        return result;
    }

    @Override
    protected void createFilterFields() {
        TextField nameFilter = new TextField("Ad");
        TextField surnameFilter = new TextField("Soyad");
        TextField tcNoFilter = new TextField("TC No");
        TextField bloodTypeFilter = new TextField("Kan Grubu");
        TextField phoneFilter = new TextField("Telefon");
        TextField emailFilter = new TextField("E-posta");
        DatePicker birthDateFilter = new DatePicker("Doğum Tarihi");
        RadioButtonGroup<String> genderFilter = new RadioButtonGroup<>("Cinsiyet");
        genderFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        genderFilter.setItems("Erkek", "Kadın");
        TextField addressFilter = new TextField("Adres");

        filterBar.addFilterComponent(nameFilter, nameFilter::getValue, "firstName");
        filterBar.addFilterComponent(surnameFilter, surnameFilter::getValue, "lastName");
        filterBar.addFilterComponent(tcNoFilter, tcNoFilter::getValue, "tcNo");
        filterBar.addFilterComponent(bloodTypeFilter, bloodTypeFilter::getValue, "bloodType");
        filterBar.addFilterComponent(phoneFilter, phoneFilter::getValue, "phoneNumber");
        filterBar.addFilterComponent(emailFilter, emailFilter::getValue, "email");
        filterBar.addFilterComponent(birthDateFilter, birthDateFilter::getValue, "birthDate");
        filterBar.addFilterComponent(genderFilter, genderFilter::getValue, "gender");
        filterBar.addFilterComponent(addressFilter, addressFilter::getValue, "address");
    }

    @Override
    protected void applyFilters() {
        // Reset to first page when filters change
        resetToFirstPage();
    }

    private LocalDate parseBirthDate(Object birthDateValue) {
        if (birthDateValue == null) {
            return null;
        }
        if (birthDateValue instanceof LocalDate) {
            return (LocalDate) birthDateValue;
        }
        if (birthDateValue instanceof String && !((String) birthDateValue).isEmpty()) {
            try {
                return LocalDate.parse((String) birthDateValue);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    protected String getTitle() {
        return "Hasta Yönetimi";
    }

    @Override
    protected void onAddNew() {
        // TODO: Implement add new patient
    }

    @Override
    protected void onEdit(Patient item) {
        // TODO: Implement edit patient
    }

    @Override
    protected void onDelete(Patient item) {
        // TODO: Implement delete patient
    }

    @Override
    protected boolean matchesFilters(Patient item, Map<String, Object> filters) {
        // This method is not used anymore since we use Specification
        return true;
    }
}

