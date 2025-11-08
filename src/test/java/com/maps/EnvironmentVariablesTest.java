package com.maps;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test to verify environment variables loading
 */
@SpringBootTest
@ActiveProfiles("test")
public class EnvironmentVariablesTest {

    @Value("${application.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${recaptcha.site}")
    private String recaptchaSite;

    @Test
    public void testEnvironmentVariablesLoaded() {
        System.out.println("JWT Secret loaded: " + (jwtSecret != null ? "✓" : "✗"));
        System.out.println("Mail Username loaded: " + (mailUsername != null ? "✓" : "✗"));
        System.out.println("Recaptcha Site loaded: " + (recaptchaSite != null ? "✓" : "✗"));
        
        assertNotNull(jwtSecret, "JWT Secret should be loaded");
        assertNotNull(mailUsername, "Mail Username should be loaded");
        assertNotNull(recaptchaSite, "Recaptcha Site should be loaded");
    }
}