package com.maps.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Slf4j
public class DotenvConfig implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        
        try {
            // Try to load .env file from the root directory
            Dotenv dotenv = Dotenv.configure()
                    .directory("./")
                    .ignoreIfMissing()
                    .load();
            
            Map<String, Object> envVars = new HashMap<>();
            
            // Load all .env variables into Spring environment
            dotenv.entries().forEach(entry -> {
                envVars.put(entry.getKey(), entry.getValue());
                // Also set as system property for compatibility
                System.setProperty(entry.getKey(), entry.getValue());
                log.debug("Loaded env variable: {} = {}", entry.getKey(), 
                    entry.getKey().contains("PASSWORD") || entry.getKey().contains("SECRET") 
                        ? "***HIDDEN***" : entry.getValue());
            });
            
            if (!envVars.isEmpty()) {
                environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envVars));
                log.info("Successfully loaded {} environment variables from .env file", envVars.size());
                String[] criticalVars = {"MAIL_USERNAME", "MAIL_PASSWORD", "JWT_SECRET"};
                for (String var : criticalVars) {
                    if (envVars.containsKey(var)) {
                        log.debug("✓ {} loaded successfully", var);
                    } else {
                        log.warn("⚠ {} not found in .env file", var);
                    }
                }
            } else {
                log.warn("No variables found in .env file");
            }
            
        } catch (Exception e) {
            log.error("Failed to load .env file: {}", e.getMessage(), e);
        }
    }
}