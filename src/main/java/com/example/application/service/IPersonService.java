package com.example.application.service;

import com.example.application.domain.Person;
import java.util.List;
import java.util.Optional;

public interface IPersonService {
    List<Person> findAll();
    Optional<Person> findById(long id);
    Person save(Person person);
    void deleteById(Long id);
}

