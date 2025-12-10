package com.maps.configuration;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Configuration
public class ConfigurationCors {

    @Autowired
    private Environment environment;

    /**
     * CORS configuration for Spring MVC
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {

                String allowedOriginsStr = environment.getProperty("ALLOWED_ORIGINS", 
                    "https://maps-front.vercel.app,https://maps-front.onrender.com,http://localhost:8080,http://localhost:10000,http://localhost:5173,https://maps.chm.mb,https://www.maps.chm.mb");
                
                List<String> allowedOriginsList = Arrays.asList(allowedOriginsStr.split(","));
                
                registry.addMapping("/**")
                        .allowedOriginPatterns(allowedOriginsList.toArray(new String[0]))
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }

    /**
     * CORS configuration for Spring Security
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        String allowedOriginsStr = environment.getProperty("ALLOWED_ORIGINS", 
            "https://maps-front.vercel.app,https://maps-front.onrender.com,http://localhost:10000,http://localhost:5173,https://maps.chm.mb,https://www.maps.chm.mb,moz-extension://c740ec45-5e71-4408-af24-dd6a5b1f37a2");
        
        List<String> allowedOriginsList = Arrays.asList(allowedOriginsStr.split(","));
        configuration.setAllowedOriginPatterns(allowedOriginsList);
        
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
