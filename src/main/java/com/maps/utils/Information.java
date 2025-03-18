package com.maps.utils;

import com.maps.MapsApplication;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class Information {

    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);
    public Optional<String> getCurrentUser(){
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .map(auth -> {
                        Object principal = auth.getPrincipal();
                        if (principal instanceof UserDetails) {
                            String username = ((UserDetails) principal).getUsername();
                            LOGGER.info("Current authenticated user: {}", username);
                            return username;
                        }
                        return principal.toString();
                    });
        } catch (Exception e){
            LOGGER.error("Error getting current user: ", e);
            return Optional.empty();
        }
    }
}