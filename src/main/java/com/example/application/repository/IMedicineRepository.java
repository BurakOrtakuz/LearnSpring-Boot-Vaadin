package com.example.application.repository;

import com.example.application.domain.Medicine;
import com.example.application.dto.IMedicineResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IMedicineRepository extends JpaRepository<Medicine, Long> {

    @Query(value =
            """
                Select m.medicineId as medicineId ,m.name as medicineName, u as unit, m.description as description
                from Medicine as m
                join m.unit as u
            """)
    List<IMedicineResult> findAllMedicines();

    @Query(value =
            """
                Select m.medicineId as medicineId ,m.name as medicineName, u as unit, m.description as description
                from Medicine as m
                join m.unit as u
                where m.name like %:name%
                and u.name like %:unit%
                and m.description like %:description%
            """)
    List<IMedicineResult> filterMedicine(@Param("name") String name,@Param("unit") String unit, @Param("description") String description);
    Optional<Medicine> findByName(String name);
}
