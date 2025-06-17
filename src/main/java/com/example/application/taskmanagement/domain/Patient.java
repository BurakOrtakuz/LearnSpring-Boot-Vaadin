package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    private Long patientId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "patient_id")
    private Person person;
}
