package com.example.application.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "patient")
@Data
@SuperBuilder
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
