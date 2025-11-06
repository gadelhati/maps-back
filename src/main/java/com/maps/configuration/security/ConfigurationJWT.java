package com.maps.configuration.security;

import com.maps.MapsApplication;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Component
public class ConfigurationJWT {
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);
    @Value("${application.jwtIssuer}")
    private String issuer;
    @Value("${application.jwtAudience}")
    private String audience;
    @Value("${application.jwtExpiration}")
    private Integer expiration;
    @Value("${application.jwtSecret}")
    private String secretKey;

    private SecretKey getSigningKey() {
        if (secretKey == null || secretKey.isBlank()) {
            LOGGER.warn("JWT secret key not configured. Using random in-memory key");
            byte[] randomKey = new byte[64];
            new SecureRandom().nextBytes(randomKey);
            return new SecretKeySpec(randomKey, "HmacSHA512");
        }
        return new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
    }

    public String generateToken(String authentication) {
        return Jwts.builder()
                .audience().add(audience).and()
                .header().add("typ", "JWT").and()
                .issuer(issuer)
                .subject(authentication)
                .notBefore(new Date())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    public String getUsernameFromJWT(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateJWT(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()).build()
                    .parseSignedClaims(token).getPayload();
            return true;
        } catch (SecurityException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("validateToken, exception: {}", e.getMessage());
        }
        return false;
    }
}