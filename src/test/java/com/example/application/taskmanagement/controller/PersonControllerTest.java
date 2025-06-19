package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Person;
import com.example.application.taskmanagement.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(personService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(personService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/persons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Person person = new Person();
        person.setId(1L);
        Mockito.when(personService.save(any(Person.class))).thenReturn(person);
        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"personId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(personService.findById(1L)).thenReturn(Optional.of(new Person()));
        mockMvc.perform(delete("/api/persons/1"))
                .andExpect(status().isNoContent());
    }
}

