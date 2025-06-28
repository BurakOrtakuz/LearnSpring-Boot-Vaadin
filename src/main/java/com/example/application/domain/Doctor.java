package com.example.application.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @Column(name = "doctor_id")
    private Long doctorId; // Same as Person ID

    @OneToOne
    @MapsId
    @JoinColumn(name = "doctor_id")
    private Person person;

    @Column(name = "branch")
    private String branch;
}
