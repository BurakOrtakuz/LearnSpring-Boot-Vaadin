package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Medicine;
import com.example.application.taskmanagement.service.IMedicineService;
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

@WebMvcTest(MedicineController.class)
class MedicineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMedicineService medicineService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(medicineService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/medicines"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(medicineService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/medicines/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Medicine medicine = new Medicine();
        medicine.setMedicineId(1L);
        Mockito.when(medicineService.save(any(Medicine.class))).thenReturn(medicine);
        mockMvc.perform(post("/api/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medicineId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(medicineService.findById(1L)).thenReturn(Optional.of(new Medicine()));
        mockMvc.perform(delete("/api/medicines/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        Medicine medicine = new Medicine();
        medicine.setMedicineId(1L);
        Mockito.when(medicineService.findById(1L)).thenReturn(Optional.of(medicine));
        Mockito.when(medicineService.save(any(Medicine.class))).thenReturn(medicine);
        mockMvc.perform(put("/api/medicines/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"medicineId\":1,\"name\":\"test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void patch_shouldReturnOk() throws Exception {
        Medicine medicine = new Medicine();
        medicine.setMedicineId(1L);
        Mockito.when(medicineService.findById(1L)).thenReturn(Optional.of(medicine));
        Mockito.when(medicineService.save(any(Medicine.class))).thenReturn(medicine);
        mockMvc.perform(patch("/api/medicines/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"patched\"}"))
                .andExpect(status().isOk());
    }
}
