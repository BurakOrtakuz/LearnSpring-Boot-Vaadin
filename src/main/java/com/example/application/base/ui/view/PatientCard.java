package com.example.application.base.ui.view;

import com.example.application.base.ui.component.PatientCard.CommunicationInformationDetails;
import com.example.application.base.ui.component.PatientCard.MedicineInformationDetails;
import com.example.application.base.ui.component.PatientCard.PatientInformationDetails;
import com.example.application.domain.Person;
import com.example.application.service.IPrescriptionMedicineService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PatientCard extends VerticalLayout {

    public PatientCard(Person person, IPrescriptionMedicineService prescriptionMedicineService)
    {
        add(new PatientInformationDetails(person),
            new CommunicationInformationDetails(person),
            new MedicineInformationDetails(prescriptionMedicineService, person)
        );
    }
}
