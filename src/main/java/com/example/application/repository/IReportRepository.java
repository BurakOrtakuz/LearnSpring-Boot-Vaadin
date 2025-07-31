package com.example.application.repository;

import com.example.application.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByReportCode(String reportCode);

    List<Report> findByNameContainingIgnoreCase(String name);

    @Query("SELECT r FROM Report r ORDER BY r.uploadDate DESC")
    List<Report> findAllOrderByUploadDateDesc();

    @Query("SELECT r.reportCode, r.name FROM Report r ORDER BY r.name")
    List<Object[]> findReportCodesAndNames();

    boolean existsByReportCode(String reportCode);
}
