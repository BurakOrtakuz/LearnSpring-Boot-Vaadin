package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "name", nullable = false)
    private String name;
}

