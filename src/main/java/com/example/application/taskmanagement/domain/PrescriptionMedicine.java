package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_medicine_id")
    private Long prescriptionMedicineId;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;
}
