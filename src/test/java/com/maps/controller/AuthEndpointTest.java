package com.maps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test for REST API authentication endpoints
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
public class AuthEndpointTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthLoginEndpoint_ShouldNotRedirectToThymeleaf() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        
        // Create DTORequestUserAuth with all required parameters
        DTORequestUserAuth loginRequest = new DTORequestUserAuth("testuser", "testPassword123!", 123456, "test-captcha");
        
        String jsonRequest = objectMapper.writeValueAsString(loginRequest);
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(header().doesNotExist("Location")) // No redirect header - this is the key test
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        System.out.println("✅ /auth/login correctly returns JSON response instead of redirecting to login page");
    }
    
    @Test 
    public void testWebLoginPage_ShouldBeAccessible() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        
        mockMvc.perform(get("/login")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
        
        System.out.println("✅ /login page is accessible for web interface");
    }
}