package com.example.application.repository;

import com.example.application.domain.Examination;
import com.example.application.dto.IDoctorExaminationSearchResult;
import com.example.application.dto.IPatientExaminationSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
    @Query("""
        SELECT e.date as date,
               CONCAT(e.doctor.person.firstName, ' ', e.doctor.person.lastName) as doctorName,
               e.complaint as complaint,
               CASE
                   WHEN p IS NULL THEN -1
                   WHEN p.prescriptionStatus = 'NOT_CREATED' THEN -1
                   ELSE p.prescriptionId
               END as prescriptionId
        FROM Examination e
        LEFT JOIN Prescription p ON p.examination.examinationId = e.examinationId
        WHERE CAST(e.date AS string) LIKE CONCAT('%', :query, '%')
           OR CONCAT(e.doctor.person.firstName, ' ', e.doctor.person.lastName) LIKE CONCAT('%', :query, '%')
           OR e.complaint LIKE CONCAT('%', :query, '%')
        """)
    List<IPatientExaminationSearchResult> patientSearchResult(String query);

    @Query("""
        SELECT e.date as date,
               CONCAT(e.patient.person.firstName, ' ', e.patient.person.lastName) as patientName,
               e.complaint as complaint,
               e.patient.patientId as patientId,
               CASE
                   WHEN p IS NULL THEN -1
                   WHEN p.prescriptionStatus = 'NOT_CREATED' THEN -1
                   ELSE p.prescriptionId
               END as prescriptionId,
               e.examinationId as examinationId
        FROM Examination e
        LEFT JOIN Prescription p ON p.examination.examinationId = e.examinationId
        WHERE CAST(e.date AS string) LIKE CONCAT('%', :query, '%')
           OR CONCAT(e.patient.person.firstName, ' ', e.patient.person.lastName) LIKE CONCAT('%', :query, '%')
           OR e.complaint LIKE CONCAT('%', :query, '%')
        """)
    List<IDoctorExaminationSearchResult> doctorSearchResult(String query);

    @Query(value=
        """
            SELECT e.date
            FROM examination e
            WHERE e.doctor_id = :doctorId
              AND CAST(e.date AS DATE) = :date
            ORDER BY e.date
        """
            , nativeQuery = true)
    List<Date> getDoctorHour(Long doctorId, Date date);
}
