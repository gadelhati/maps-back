package com.maps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.exception.ApiError;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.service.ServiceUser;
import com.maps.service.ServiceUserAuth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerUser.class)
@ContextConfiguration(classes = {ControllerUser.class, TestControllerUser.TestConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
class TestControllerUser {

    @Autowired private MockMvc mockMvc;
    @Autowired private ServiceUser serviceUser;
    @Autowired private ServiceUserAuth serviceUserAuth;
    @Autowired private ObjectMapper objectMapper;
    private Role testRole;
    private Set<Privilege> privileges;

    @Configuration
    static class TestConfiguration {
        @Bean
        @Primary
        public ServiceUser serviceUser() {
            return Mockito.mock(ServiceUser.class);
        }

        @Bean
        @Primary
        public ServiceUserAuth serviceUserAuth() {
            return Mockito.mock(ServiceUserAuth.class);
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void signUp_shouldReturnAccepted() throws Exception {
        DTORequestUser dtoRequestUser = new DTORequestUser("testuser", "test@example.com");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUser)))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void signUp_shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        DTORequestUser dtoRequestUser = new DTORequestUser("testuser", "invalid-email");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void changePassword_shouldReturnAccepted() throws Exception {
        UUID userId = UUID.randomUUID();

        DTORequestUserPassword dtoRequestUserPassword = new DTORequestUserPassword();
        dtoRequestUserPassword.setId(userId);
//        dtoRequestUserPassword.setPassword("NewPass123!"); // Senha válida para passar validações

        Privilege readPrivilege = new Privilege();
        readPrivilege.setId(UUID.randomUUID());
        readPrivilege.setName("READ");

        privileges = new HashSet<>();
        privileges.add(readPrivilege);

        testRole = new Role("USER", privileges);
        testRole.setId(UUID.randomUUID());

        DTOResponseUser mockResponse = new DTOResponseUser(UUID.randomUUID(), "testuser", "test@example.com", 0, true, Collections.singleton(testRole));

        when(serviceUser.changePassword(any(DTORequestUserPassword.class))).thenReturn(mockResponse);

        mockMvc.perform(put("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUserPassword)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(userId.toString()));
    }
}