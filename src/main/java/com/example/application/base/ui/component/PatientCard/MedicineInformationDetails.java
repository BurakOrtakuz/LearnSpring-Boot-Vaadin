package com.example.application.base.ui.component.PatientCard;

import com.example.application.domain.Person;
import com.example.application.dto.IPrescriptionMedicineByPersonResult;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class MedicineInformationDetails extends Details {

    public MedicineInformationDetails(IPrescriptionMedicineService prescriptionMedicineService, Person person) {
        HorizontalLayout summary = new HorizontalLayout();
        summary.setWidthFull();
        summary.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        Span header = new Span("İlaç Bilgileri");
        summary.add(header);

        VerticalLayout content = new VerticalLayout();
        content.addClassName("medicine-details");
        content.setWidthFull();
        content.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        List<IPrescriptionMedicineByPersonResult> medicines =
                prescriptionMedicineService.findByPrescription_Person_PersonId(person.getId());


        for(IPrescriptionMedicineByPersonResult medicine : medicines)
        {
            HorizontalLayout row = new HorizontalLayout();
            VerticalLayout medicineRow = new DetailRowItem("İlaç İsmi", medicine.getMedicineName());
            VerticalLayout unitRow = new DetailRowItem("Birim", medicine.getUnitName());
            VerticalLayout descriptionRow = new DetailRowItem("Açıklama", medicine.getDescription());
            VerticalLayout timestampRow = new DetailRowItem("Reçete Tarihi", medicine.getTimestamp() != null ? medicine.getTimestamp().toString() : "-");
            VerticalLayout finishTimeRow = new DetailRowItem("Bitiş Tarihi", medicine.getFinishTime() != null ? medicine.getFinishTime().toString() : "-");

            row.add(medicineRow, unitRow, descriptionRow, timestampRow, finishTimeRow);
            content.add(row);
        }

        super.setSummary(header);
        super.add(content);
    }
}
