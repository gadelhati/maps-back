package com.maps.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class Information {

    public Optional<String> getCurrentUser(){
        try {
            return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                    .map(auth -> {
                        Object principal = auth.getPrincipal();
                        if (principal instanceof UserDetails) {
                            String username = ((UserDetails) principal).getUsername();
                            log.info("Current authenticated user: {}", username);
                            return username;
                        }
                        return principal.toString();
                    });
        } catch (Exception e){
            log.error("Error getting current user: ", e);
            return Optional.empty();
        }
    }
}
