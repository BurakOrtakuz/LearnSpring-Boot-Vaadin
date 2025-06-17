package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    private Long adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private Person person;

    private String rank;
}
