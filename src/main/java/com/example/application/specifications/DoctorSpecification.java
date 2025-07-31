package com.example.application.specifications;

import com.example.application.domain.Doctor;
import com.example.application.specifications.criteria.DoctorFilterCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class DoctorSpecification {

    public static Specification<Doctor> firstNameContains(String firstName) {
        return (root, query, criteriaBuilder) -> {
            if (firstName == null || firstName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("person").get("firstName")),
                    "%" + firstName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> lastNameContains(String lastName) {
        return (root, query, criteriaBuilder) -> {
            if (lastName == null || lastName.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("person").get("lastName")),
                    "%" + lastName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> tcNoStartsWith(String tcNo) {
        return (root, query, criteriaBuilder) -> {
            if (tcNo == null || tcNo.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("person").get("tcNo"),
                    tcNo + "%"  // TC başlangıç match
            );
        };
    }

    public static Specification<Doctor> specializationStartsWith(String specialization) {
        return (root, query, criteriaBuilder) -> {
            if (specialization == null || specialization.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("specialization")),
                    specialization.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> phoneContains(String phoneNumber) {
        return (root, query, criteriaBuilder) -> {
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    root.get("person").get("phoneNumber"),
                    "%" + phoneNumber + "%"
            );
        };
    }
    public static Specification<Doctor> emailContains(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("person").get("email")),
                    "%" + email.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> birthDateEquals(LocalDate birthDate) {
        return (root, query, criteriaBuilder) -> {
            if (birthDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("person").get("birthDate"),
                    birthDate
            );
        };
    }

    public static Specification<Doctor> genderEquals(String gender) {
        return (root, query, criteriaBuilder) -> {
            if(gender == null || gender.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("person").get("gender"),
                    gender
            );
        };
    }

    public static Specification<Doctor> addressContains(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null || address.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("person").get("email")),
                    "%" + address.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Doctor> searchWithCriteria(DoctorFilterCriteria criteria) {
        return Specification.where(firstNameContains(criteria.getFirstName()))
                .and(lastNameContains(criteria.getLastName()))
                .and(tcNoStartsWith(criteria.getTcNo()))
                .and(specializationStartsWith(criteria.getSpecialization()))
                .and(phoneContains(criteria.getPhoneNumber()))
                .and(emailContains(criteria.getEmail()))
                .and(birthDateEquals(criteria.getBirthDate()))
                .and(genderEquals(criteria.getGender()))
                .and(addressContains(criteria.getAddress()));
    }
}
