package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hasta {
    @Id
    private Long hastaId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "hasta_id")
    private Kisi kisi;
}

