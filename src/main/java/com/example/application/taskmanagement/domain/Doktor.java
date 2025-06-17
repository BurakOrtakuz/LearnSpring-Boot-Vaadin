package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doktor {
    @Id
    private Long doktorId; // Kisi ID ile aynı

    @OneToOne
    @MapsId
    @JoinColumn(name = "doktor_id")
    private Kisi kisi;

    private String brans;
}

