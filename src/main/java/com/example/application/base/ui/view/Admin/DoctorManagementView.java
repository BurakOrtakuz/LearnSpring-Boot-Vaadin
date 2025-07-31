package com.example.application.base.ui.view.Admin;

import com.example.application.base.ui.component.table.ColumnConfig;
import com.example.application.domain.Doctor;
import com.example.application.domain.Patient;
import com.example.application.service.IDoctorService;
import com.example.application.specifications.criteria.DoctorFilterCriteria;
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

public class DoctorManagementView extends BaseManagementView{

    private final IDoctorService doctorService;
    public DoctorManagementView(IDoctorService doctorService) {
        super();
        this.doctorService = doctorService;
        super.init();
        filterBar.setReloadCallback(this::applyFilters);
        this.table.addItemDoubleClickListener(event -> {
            // UI.getCurrent().navigate("admin/doctor-details/" + selectedDoctor.getId());
        });
    }
    @Override
    public List<ColumnConfig<Doctor,?>> getColumnConfigs() {
        return List.of(
                new ColumnConfig<>("Ad", e -> e.getPerson().getFirstName(), true),
                new ColumnConfig<>("Soyad", e -> e.getPerson().getLastName(), true),
                new ColumnConfig<>("TC No", e -> e.getPerson().getTcNo(), true),
                new ColumnConfig<>("Uzmanlık Alanı", Doctor::getBranch, true),
                new ColumnConfig<>("Telefon", e -> e.getPerson().getPhoneNumber(), true),
                new ColumnConfig<>("E-posta", e -> e.getPerson().getEmail(), true),
                new ColumnConfig<>("Doğum Tarihi", e -> e.getPerson().getBirthDate().toString(), true),
                new ColumnConfig<>("Cinsiyet", e -> e.getPerson().getGender(), true),
                new ColumnConfig<>("Adres", e -> e.getPerson().getAddress(), true)
        );
    }

    @Override
    public Page loadData(int page, int size) {
        // Get current filter values
        Map<String, Object> filters = filterBar != null ? filterBar.getFilterValues() : Map.of();

        DoctorFilterCriteria criteria = DoctorFilterCriteria.builder()
                .firstName((String) filters.getOrDefault("firstName", ""))
                .lastName((String) filters.getOrDefault("lastName", ""))
                .tcNo((String) filters.getOrDefault("tcNo", ""))
                .specialization((String) filters.getOrDefault("specialization", ""))
                .phoneNumber((String) filters.getOrDefault("phoneNumber", ""))
                .email((String) filters.getOrDefault("email", ""))
                .birthDate(super.parseBirthDate(filters.get("birthDate")))
                .gender((String) filters.get("gender"))
                .address((String) filters.getOrDefault("address", ""))
                .build();

        Page<Doctor> result = doctorService.findWithFilters(criteria, PageRequest.of(page, size));

        // Update table and pagination
        updateTableData(result);

        return result;

    }

    @Override
    protected void createFilterFields() {
        TextField nameFilter = new TextField("Ad");
        TextField surnameFilter = new TextField("Soyad");
        TextField tcNoFilter = new TextField("TC No");
        TextField bloodTypeFilter = new TextField("Alanı");
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
        filterBar.addFilterComponent(bloodTypeFilter, bloodTypeFilter::getValue, "specialization");
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

    @Override
    protected String getTitle() {
        return "";
    }

    @Override
    protected void onAddNew() {

    }

    @Override
    protected void onEdit(Object item) {

    }

    @Override
    protected void onDelete(Object item) {

    }

}
