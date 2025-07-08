package com.example.application.repository;

import com.example.application.domain.Examination;
import com.example.application.dto.IDoctorExaminationSearchResult;
import com.example.application.dto.IPatientExaminationSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
    @Query(value =
        """
            Select
                e.date as date,
                concat(p.first_name,' ',p.last_name) as doctorName,
                e.complaint as complaint,
                CASE
                    WHEN pr.document IS NULL THEN -1
                    ELSE pr.prescription_id
                    END as prescriptionId
            from examination as e
                inner join doctor as d on e.doctor_id = d.doctor_id
                inner join patient as pa on e.patient_id = pa.patient_id
                inner join person as p on p.person_id = d.doctor_id
                left join prescription as pr on pr.examination_id = e.examination_id
            where  to_char(e.date, 'YYYY-MM-DD HH24:MI')ILIKE '%'|| :query || '%'
                or concat(p.first_name,' ',p.last_name) ILIKE '%' || :query || '%'
                or e.complaint ILIKE '%' || :query || '%'
        """
            ,nativeQuery = true)
    List<IPatientExaminationSearchResult> patientSearchResult(String query);
    @Query(value =
        """
            Select
                e.date as date,
                concat(p.first_name,' ',p.last_name) as patientName,
                e.complaint as complaint,
                CASE
                    WHEN pr.document IS NULL THEN -1
                    ELSE pr.prescription_id
                    END as prescriptionId,
                e.examination_id as examinationId
            from examination as e
                inner join patient as pa on e.patient_id = pa.patient_id
                inner join person as p on p.person_id = pa.patient_id
                left join prescription as pr on pr.examination_id = e.examination_id
            where  to_char(e.date, 'YYYY-MM-DD HH24:MI')ILIKE '%'|| :query || '%'
                or concat(p.first_name,' ',p.last_name) ILIKE '%' || :query || '%'
                or e.complaint ILIKE '%' || :query || '%'
        """
            ,nativeQuery = true)
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
