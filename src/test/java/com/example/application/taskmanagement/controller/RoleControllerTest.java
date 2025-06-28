package com.example.application.taskmanagement.controller;

import com.example.application.controller.RoleController;
import com.example.application.domain.Role;
import com.example.application.service.IRoleService;
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

@WebMvcTest(RoleController.class)
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRoleService roleService;

    @Test
    void getAll_shouldReturnOk() throws Exception {
        Mockito.when(roleService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturnNotFound() throws Exception {
        Mockito.when(roleService.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_shouldReturnOk() throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        Mockito.when(roleService.save(any(Role.class))).thenReturn(role);
        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roleId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        Mockito.when(roleService.findById(1L)).thenReturn(Optional.of(new Role()));
        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void update_shouldReturnOk() throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        Mockito.when(roleService.findById(1L)).thenReturn(Optional.of(role));
        Mockito.when(roleService.save(any(Role.class))).thenReturn(role);
        mockMvc.perform(put("/api/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"roleId\":1,\"name\":\"test\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void patch_shouldReturnOk() throws Exception {
        Role role = new Role();
        role.setRoleId(1L);
        Mockito.when(roleService.findById(1L)).thenReturn(Optional.of(role));
        Mockito.when(roleService.save(any(Role.class))).thenReturn(role);
        mockMvc.perform(patch("/api/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"patched\"}"))
                .andExpect(status().isOk());
    }
}
