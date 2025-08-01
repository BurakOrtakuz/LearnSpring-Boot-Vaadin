package com.example.application.domain;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_usage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long usageId;

    @Column(name = "operation_name", nullable = false, length = 100)
    @Unique
    private String operationName;

    @ManyToOne()
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
