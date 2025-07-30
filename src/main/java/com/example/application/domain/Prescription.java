package com.example.application.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "prescription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long prescriptionId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "prescription")
    private List<PrescriptionMedicine> medicines;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Column(name = "dr_note")
    private String doctorNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "prescription_status", nullable = false)
    private PrescriptionStatus prescriptionStatus = PrescriptionStatus.NOT_CREATED;
}
