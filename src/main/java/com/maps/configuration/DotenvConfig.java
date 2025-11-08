package com.maps.configuration;

import io.github.cdimascio.dotenv.Dotenv;
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
            });
            
            if (!envVars.isEmpty()) {
                environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envVars));
                System.out.println("Loaded " + envVars.size() + " variables from .env file");
            }
            
        } catch (Exception e) {
            System.out.println("Could not load .env file: " + e.getMessage());
            System.out.println("Application will use default values or system environment variables");
        }
    }
}