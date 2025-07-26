package com.example.application.repository;

import com.example.application.domain.PrescriptionMedicine;
import com.example.application.dto.IPrescriptionMedicineByPersonResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {

    @Query(value =
            """
                SELECT pm.medicine.name AS medicineName,
                       pm.medicine.unit AS unitName,
                       pm.description AS description,
                       pm.timestamp AS timestamp,
                       pm.finishTime AS finishTime
                from PrescriptionMedicine pm
                where pm.prescription.patient.patientId = :personId
            """)
    List<IPrescriptionMedicineByPersonResult> findByPrescription_Person_PersonId(Long personId);
}
