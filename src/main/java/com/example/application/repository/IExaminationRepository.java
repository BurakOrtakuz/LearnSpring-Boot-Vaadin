package com.example.application.repository;

import com.example.application.domain.Examination;
import com.example.application.dto.IExaminationSearchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IExaminationRepository extends JpaRepository<Examination, Long> {
    @Query(value =
            "Select e.date as date,\n" +
            "       concat(p.first_name,' ',p.last_name) as doctorName,\n" +
            "       e.complaint as complaint,\n" +
            "       CASE WHEN pr.document IS NULL THEN -1 ELSE pr.prescription_id END as prescriptionId\n" +
            "from examination as e\n" +
            " inner join doctor as d on e.doctor_id = d.doctor_id\n" +
            " inner join patient as pa on e.patient_id = pa.patient_id\n" +
            " inner join person as p on p.person_id = d.doctor_id\n" +
            " inner join prescription as pr on pr.examination_id = e.examination_id\n" +
            "where  to_char(e.date, 'YYYY-MM-DD HH24:MI')ILIKE '%'|| :query || '%'\n" +
            "or concat(p.first_name,' ',p.last_name) ILIKE '%' || :query || '%'\n" +
            "or e.complaint ILIKE '%' || :query || '%'",nativeQuery = true)
    List<IExaminationSearchResult> searchResult(String query);
}
