package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    private Long doctorId; // Same as Person ID

    @OneToOne
    @MapsId
    @JoinColumn(name = "doctor_id")
    private Person person;

    private String branch;
}
