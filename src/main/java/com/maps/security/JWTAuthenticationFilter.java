package com.maps.security;

import com.maps.MapsApplication;
import com.maps.service.ServiceCustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Value("${application.version}")
    private String version;
    public final JWTGenerator jwtGenerator;
    public final ServiceCustomUserDetails serviceCustomUserDetails;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            getJWTFromRequest(request)
                    .filter(jwtGenerator::validateJWT)
                    .map(jwtGenerator::getUsernameFromJWT)
                    .ifPresent(username->authenticateUser(username, request));
        } catch (Exception ex) {
            LOGGER.info("Unable to authenticate user: {}", ex.getMessage());
        }
        response.addHeader("X-API-Version", version);
        filterChain.doFilter(request, response);
    }
    private Optional<String> getJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
    private void authenticateUser(String username, HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(username);
            if (userDetails != null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
    }
}