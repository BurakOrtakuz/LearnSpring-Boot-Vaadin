package com.example.application.taskmanagement.controller;

import com.example.application.controller.PrescriptionMedicineController;
import com.example.application.domain.PrescriptionMedicine;
import com.example.application.service.IPrescriptionMedicineService;
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

@WebMvcTest(PrescriptionMedicineController.class)
class PrescriptionMedicineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPrescriptionMedicineService prescriptionMedicineService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(prescriptionMedicineService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/prescription-medicines"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(prescriptionMedicineService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/prescription-medicines/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine();
        prescriptionMedicine.setPrescriptionMedicineId(1L);
        Mockito.when(prescriptionMedicineService.save(any(PrescriptionMedicine.class))).thenReturn(prescriptionMedicine);
        mockMvc.perform(post("/api/prescription-medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prescriptionMedicineId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(prescriptionMedicineService.findById(1L)).thenReturn(Optional.of(new PrescriptionMedicine()));
        mockMvc.perform(delete("/api/prescription-medicines/1"))
                .andExpect(status().isNoContent());
    }
}

