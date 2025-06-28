package com.example.application.controller;

import com.example.application.domain.Person;
import com.example.application.service.IPersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@PreAuthorize("hasRole('ADMIN')")
public class PersonController {
    private final IPersonService personService;

    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Person create(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person person) {
        if (!personService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        person.setId(id);
        return ResponseEntity.ok(personService.save(person));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> patch(@PathVariable Long id, @RequestBody Person person) {
        return personService.findById(id)
                .map(existing -> {
                    if (person.getFirstName() != null) existing.setFirstName(person.getFirstName());
                    if (person.getLastName() != null) existing.setLastName(person.getLastName());
                    // Diğer alanlar için de ekleme yapılabilir
                    return ResponseEntity.ok(personService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!personService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
