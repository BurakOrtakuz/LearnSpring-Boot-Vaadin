package com.example.application.specifications;

import com.example.application.domain.Patient;
import com.example.application.specifications.criteria.PatientFilterCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PatientSpecification {

    public static Specification<Patient> firstNameContains(String firstName) {
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

    public static Specification<Patient> lastNameContains(String lastName) {
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

    public static Specification<Patient> tcNoStartsWith(String tcNo) {
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

    public static Specification<Patient> bloodTypeEquals(String bloodType) {
        return (root, query, criteriaBuilder) -> {
            if (bloodType == null || bloodType.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // BloodType Patient entity'sinde direkt field!
            return criteriaBuilder.equal(root.get("bloodType"), bloodType);
        };
    }

    public static Specification<Patient> phoneContains(String phoneNumber) {
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

    public static Specification<Patient> emailContains(String email) {
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

    public static Specification<Patient> genderEquals(String gender) {
        return (root, query, criteriaBuilder) -> {
            if (gender == null || gender.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("person").get("gender"), gender);
        };
    }

    public static Specification<Patient> addressContains(String address) {
        return (root, query, criteriaBuilder) -> {
            if (address == null || address.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("person").get("address")),
                    "%" + address.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Patient> birthDateEquals(LocalDate birthDate) {
        return (root, query, criteriaBuilder) -> {
            if (birthDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("person").get("birthDate"), birthDate);
        };
    }

    public static Specification<Patient> searchWithCriteria(PatientFilterCriteria criteria) {
        return Specification
                .where(firstNameContains(criteria.getFirstName()))
                .and(lastNameContains(criteria.getLastName()))
                .and(tcNoStartsWith(criteria.getTcNo()))
                .and(bloodTypeEquals(criteria.getBloodType()))
                .and(phoneContains(criteria.getPhoneNumber()))
                .and(emailContains(criteria.getEmail()))
                .and(birthDateEquals(criteria.getBirthDate()))
                .and(genderEquals(criteria.getGender()))
                .and(addressContains(criteria.getAddress()));
    }
}