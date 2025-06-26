package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "admin_id")
    private Person person;

    @Column(name = "rank")
    private String rank;
}
