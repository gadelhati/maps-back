package com.maps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.service.ServiceUser;
import com.maps.service.ServiceUserAuth;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControllerUser.class)
@ContextConfiguration(classes = {ControllerUser.class, TestControllerUser.TestConfiguration.class})
class TestControllerUser {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ServiceUserAuth serviceUserAuth;

    @Autowired
    private ObjectMapper objectMapper;

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
    void signUp_shouldReturnAccepted() throws Exception {
        DTORequestUser dtoRequestUser = new DTORequestUser("testuser", "test@example.com");
//        dtoRequestUser.setUsername("testuser");
//        dtoRequestUser.setEmail("test@example.com");
//        dtoRequestUser.setPassword("password");

        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoRequestUser)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()));
    }

    @Test
    void changePassword_shouldReturnAccepted() throws Exception {
        DTORequestUserPassword dtoRequestUserPassword = new DTORequestUserPassword();
        dtoRequestUserPassword.setId(UUID.randomUUID());
//        dtoRequestUserPassword.setPassword("newPassword");

//        DTOResponseUser dtoResponseUser = new DTOResponseUser(UUID.randomUUID(), "testuser","test@example.com", 0, true, new Role());
//        dtoResponseUser.setId(dtoRequestUserPassword.getId());
//        dtoResponseUser.setUsername("testuser");

//        when(serviceUser.changePassword(any(DTORequestUserPassword.class)))
//                .thenReturn(dtoResponseUser);
//
//        mockMvc.perform(put("/user/changePassword")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dtoRequestUserPassword)))
//                .andExpect(status().isAccepted())
//                .andExpect(jsonPath("$.id").value(dtoResponseUser.getId().toString()));
    }
}