package com.example.application.taskmanagement.controller;

import com.example.application.controller.PatientController;
import com.example.application.domain.Patient;
import com.example.application.service.IPatientService;
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

@WebMvcTest(PatientController.class)
class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService patientService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(patientService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(patientService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(1L);
        Mockito.when(patientService.save(any(Patient.class))).thenReturn(patient);
        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"patientId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(patientService.findById(1L)).thenReturn(Optional.of(new Patient()));
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }
}

