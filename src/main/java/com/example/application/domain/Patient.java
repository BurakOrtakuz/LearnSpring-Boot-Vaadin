package com.example.application.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @Column(name = "patient_id")
    private Long patientId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Person person;
}
