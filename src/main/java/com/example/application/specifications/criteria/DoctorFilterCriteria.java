package com.example.application.specifications.criteria;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DoctorFilterCriteria {
    private String firstName;
    private String lastName;
    private String tcNo;
    private String specialization;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private String gender;
    private String address;
}
