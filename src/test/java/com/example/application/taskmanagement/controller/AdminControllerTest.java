package com.example.application.taskmanagement.controller;

import com.example.application.taskmanagement.domain.Admin;
import com.example.application.taskmanagement.service.IAdminService;
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

@WebMvcTest(AdminController.class)
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdminService adminService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(adminService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(adminService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/admins/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Admin admin = new Admin();
        admin.setAdminId(1L);
        Mockito.when(adminService.save(any(Admin.class))).thenReturn(admin);
        mockMvc.perform(post("/api/admins")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(adminService.findById(1L)).thenReturn(Optional.of(new Admin()));
        mockMvc.perform(delete("/api/admins/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        Admin admin = new Admin();
        admin.setAdminId(1L);
        Mockito.when(adminService.findById(1L)).thenReturn(Optional.of(admin));
        Mockito.when(adminService.save(any(Admin.class))).thenReturn(admin);
        mockMvc.perform(put("/api/admins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"adminId\":1,\"rank\":\"test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void patch_shouldReturnOk() throws Exception {
        Admin admin = new Admin();
        admin.setAdminId(1L);
        Mockito.when(adminService.findById(1L)).thenReturn(Optional.of(admin));
        Mockito.when(adminService.save(any(Admin.class))).thenReturn(admin);
        mockMvc.perform(patch("/api/admins/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rank\":\"patched\"}"))
                .andExpect(status().isOk());
    }
}
