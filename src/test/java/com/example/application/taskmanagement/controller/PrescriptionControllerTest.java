package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Prescription;
import com.example.application.taskmanagement.service.IPrescriptionService;
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

@WebMvcTest(PrescriptionController.class)
class PrescriptionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPrescriptionService prescriptionService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(prescriptionService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/prescriptions"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(prescriptionService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/prescriptions/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId(1L);
        Mockito.when(prescriptionService.save(any(Prescription.class))).thenReturn(prescription);
        mockMvc.perform(post("/api/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prescriptionId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(prescriptionService.findById(1L)).thenReturn(Optional.of(new Prescription()));
        mockMvc.perform(delete("/api/prescriptions/1"))
                .andExpect(status().isNoContent());
    }
}

