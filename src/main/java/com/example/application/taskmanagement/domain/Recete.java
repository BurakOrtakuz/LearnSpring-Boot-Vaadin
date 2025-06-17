package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receteId;

    @ManyToOne
    @JoinColumn(name = "doktor_id")
    private Doktor doktor;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    @OneToMany(mappedBy = "recete")
    private List<ReceteIlac> ilaclar;
}

