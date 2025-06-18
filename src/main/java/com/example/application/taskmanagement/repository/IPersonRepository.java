package com.example.application.taskmanagement.repository;

import com.example.application.taskmanagement.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
