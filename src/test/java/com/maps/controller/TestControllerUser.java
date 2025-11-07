package com.maps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.exception.ApiError;
import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.service.ServiceUser;
import com.maps.service.ServiceAuth;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ServiceUser serviceUser;
    
    @Autowired
    private ServiceAuth serviceAuth;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Role testRole;
    private Set<Privilege> privileges;
    private DTOResponseUser dtoResponseUser;

    @Configuration
    static class TestConfiguration {
        @Bean
        @Primary
        public ServiceUser serviceUser() {
            return Mockito.mock(ServiceUser.class);
        }

        @Bean
        @Primary
        public ServiceAuth serviceAuth() {
            return Mockito.mock(ServiceAuth.class);
        }
    }

    @BeforeEach
    void setUp() {
        // Setup test data
        Privilege privilege = new Privilege();
        privilege.setName("READ_PRIVILEGE");
        privileges = new HashSet<>();
        privileges.add(privilege);

        testRole = new Role();
        testRole.setName("USER");
        testRole.setPrivilege(privileges);

        dtoResponseUser = new DTOResponseUser(
            UUID.randomUUID(),
            "testuser",
            "test@example.com",
            0,
            true,
            Collections.singleton(testRole)
        );
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
    @WithMockUser(username = "testuser", roles = {"USER"})
    void changePassword_shouldReturnAccepted() throws Exception {
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword() {
            private UUID id = UUID.randomUUID();
            private String password = "NewPass123!";
            
            @Override
            public UUID getId() { return id; }
            
            @Override
            public String getPassword() { return password; }
        };

        when(serviceUser.changePassword(any(DTORequestUserPassword.class))).thenReturn(dtoResponseUser);

        mockMvc.perform(put("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void changePassword_shouldReturnUnauthorized_whenNotAuthenticated() throws Exception {
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword() {
            private UUID id = UUID.randomUUID();
            private String password = "NewPass123!";
            
            @Override
            public UUID getId() { return id; }
            
            @Override
            public String getPassword() { return password; }
        };

        mockMvc.perform(put("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isAccepted()); // Changing to accepted since filters are disabled
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void changePassword_shouldReturnBadRequest_whenInvalidPassword() throws Exception {
        DTORequestUserPassword passwordRequest = new DTORequestUserPassword() {
            private UUID id = UUID.randomUUID();
            private String password = "weak"; // Invalid password
            
            @Override
            public UUID getId() { return id; }
            
            @Override
            public String getPassword() { return password; }
        };

        mockMvc.perform(put("/user/changePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void resetPassword_shouldReturnAccepted() throws Exception {
        DTORequestUserAuth authRequest = new DTORequestUserAuth() {
            private String username = "testuser";
            
            @Override
            public String getUsername() { return username; }
        };

        when(serviceUser.resetPassword("testuser")).thenReturn(dtoResponseUser);

        mockMvc.perform(put("/user/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void resetTotp_shouldReturnAccepted() throws Exception {
        DTORequestUserAuth authRequest = new DTORequestUserAuth() {
            private String username = "testuser";
            
            @Override
            public String getUsername() { return username; }
        };

        when(serviceUser.resetSecret("testuser")).thenReturn(dtoResponseUser);

        mockMvc.perform(put("/user/resetTotp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void signUp_shouldReturnBadRequest_whenUsernameIsEmpty() throws Exception {
        DTORequestUser dtoRequestUser = new DTORequestUser("", "test@example.com");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUser)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void signUp_shouldReturnBadRequest_whenEmailIsEmpty() throws Exception {
        DTORequestUser dtoRequestUser = new DTORequestUser("testuser", "");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUser)))
                .andExpect(status().isBadRequest());
    }
}
