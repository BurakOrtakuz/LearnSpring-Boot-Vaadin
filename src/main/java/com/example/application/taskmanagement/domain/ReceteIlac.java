package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceteIlac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receteIlacId;

    @ManyToOne
    @JoinColumn(name = "ilac_id")
    private Ilac ilac;

    @ManyToOne
    @JoinColumn(name = "recete_id")
    private Recete recete;
}

