package com.example.application.repository;

import com.example.application.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
