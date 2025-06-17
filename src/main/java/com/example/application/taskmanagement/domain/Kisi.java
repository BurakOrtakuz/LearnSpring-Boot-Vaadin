package com.example.application.taskmanagement.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kisiId;

    private String isim;
    private String soyisim;
    private String kullaniciAdi;
    private String sifre;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    private LocalDate dogumTarihi;
    private String cinsiyet;
    private String telefonNumarasi;
    private String adres;
}

