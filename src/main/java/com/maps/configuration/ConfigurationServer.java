package com.maps.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

/**
 * Configuração de servidor incluindo headers de segurança e políticas.
 * 
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Configuration
public class ConfigurationServer {

    @Bean
    public OncePerRequestFilter securityHeadersFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, 
                                          HttpServletResponse response, 
                                          FilterChain filterChain) throws ServletException, IOException {
                
                String requestURI = request.getRequestURI();
                
                // Headers básicos de segurança para todas as respostas
                response.setHeader("X-Content-Type-Options", "nosniff");
                response.setHeader("X-Frame-Options", "DENY");
                response.setHeader("X-XSS-Protection", "1; mode=block");
                response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
                
                // Content Security Policy diferenciado por tipo de endpoint
                if (requestURI.startsWith("/api/") || requestURI.startsWith("/auth/")) {
                    // CSP estrita para APIs REST
                    response.setHeader("Content-Security-Policy", getApiCSPPolicy());
                    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                } else if (requestURI.startsWith("/actuator/")) {
                    // CSP para endpoints administrativos
                    response.setHeader("Content-Security-Policy", "default-src 'none'");
                    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
                    response.setHeader("Pragma", "no-cache");
                } else {
                    // CSP compatível com Thymeleaf para páginas web
                    String nonce = generateNonce();
                    response.setHeader("Content-Security-Policy", getWebCSPPolicy(nonce));
                    request.setAttribute("cspNonce", nonce);
                    response.setHeader("Cache-Control", "no-cache, private");
                }
                
                filterChain.doFilter(request, response);
            }
        };
    }

    /**
     * Gera uma política CSP estrita para APIs REST
     */
    private String getApiCSPPolicy() {
        return "default-src 'none'; " +
               "frame-ancestors 'none'; " +
               "connect-src 'self'; " +
               "script-src 'none'; " +
               "style-src 'none'; " +
               "base-uri 'none'";
    }

    /**
     * Gera uma política CSP compatível com Thymeleaf
     */
    private String getWebCSPPolicy(String nonce) {
        return "default-src 'self'; " +
               "script-src 'self' 'nonce-" + nonce + "' 'unsafe-inline'; " +
               "style-src 'self' 'nonce-" + nonce + "' 'unsafe-inline' https://fonts.googleapis.com; " +
               "img-src 'self' data: https:; " +
               "font-src 'self' https://fonts.gstatic.com; " +
               "connect-src 'self'; " +
               "frame-ancestors 'none'; " +
               "base-uri 'self'; " +
               "form-action 'self'";
    }

    /**
     * Gera um nonce aleatório para CSP
     */
    private String generateNonce() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }
}