package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Doctor;
import com.example.application.taskmanagement.service.IDoctorService;
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

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDoctorService doctorService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(doctorService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(doctorService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        Mockito.when(doctorService.save(any(Doctor.class))).thenReturn(doctor);
        mockMvc.perform(post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"doctorId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(doctorService.findById(1L)).thenReturn(Optional.of(new Doctor()));
        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        Mockito.when(doctorService.findById(1L)).thenReturn(Optional.of(doctor));
        Mockito.when(doctorService.save(any(Doctor.class))).thenReturn(doctor);
        mockMvc.perform(put("/api/doctors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"doctorId\":1,\"branch\":\"test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void patch_shouldReturnOk() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setDoctorId(1L);
        Mockito.when(doctorService.findById(1L)).thenReturn(Optional.of(doctor));
        Mockito.when(doctorService.save(any(Doctor.class))).thenReturn(doctor);
        mockMvc.perform(patch("/api/doctors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"branch\":\"patched\"}"))
                .andExpect(status().isOk());
    }
}
