package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ilac {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ilacId;

    private String isim;
    private String unit;
    private String aciklama;
}

