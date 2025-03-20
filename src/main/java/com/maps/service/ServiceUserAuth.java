package com.maps.service;

import com.maps.MapsApplication;
import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Token;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestToken;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.persistence.repository.RepositoryToken;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.security.JWTGenerator;
import com.maps.utils.E2EE;
import com.maps.utils.Information;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
@RequiredArgsConstructor
public class ServiceUserAuth {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;
    private final RepositoryToken repositoryToken;
    private final RepositoryUser repositoryUser;
    private final MapperInterface<Token, DTORequestToken, DTOResponseToken> mapperInterface;
    private final ServiceCustomUserDetails serviceCustomUserDetails;
    private final ServiceUserTOTP serviceUserTOTP;
    private final ServiceEmail serviceEmail;
    private final ServiceUser serviceUser;
    private final Information information;
    private final ServiceRecaptcha serviceRecaptcha;
    private final E2EE e2EE;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    public DTOResponseToken login(DTORequestUserAuth dtoRequestUserAuth) {
        captchaTest(dtoRequestUserAuth.getCaptchaToken());
        try {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(dtoRequestUserAuth.getUsername());
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoRequestUserAuth.getUsername(), dtoRequestUserAuth.getPassword()));
            serviceUserTOTP.validateTOTP(authentication.getName(), dtoRequestUserAuth.getTotpKey());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication.getName());
            UUID refreshToken = UUID.randomUUID();
            repositoryToken.save(new Token(refreshToken, true));
            return new DTOResponseToken(
                    token,
                    refreshToken,
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())
            );
        } catch (BadCredentialsException e) {
            addAttempt(dtoRequestUserAuth);
            LOGGER.error("Invalid credentials for user: {}", dtoRequestUserAuth.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        } catch (AuthenticationException e) {
            addAttempt(dtoRequestUserAuth);
            LOGGER.error("Authentication failed for user: {}", dtoRequestUserAuth.getUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
        }
    }

    public DTOResponseToken refresh(DTORequestToken dtoRequestToken) {
        if (repositoryToken.existsByRefreshToken(dtoRequestToken.getRefreshToken()) &&
            jwtGenerator.validateJWT(dtoRequestToken.getAccessToken())) {
            UserDetails userDetails = serviceCustomUserDetails.loadUserByUsername(
                    jwtGenerator.getUsernameFromJWT(dtoRequestToken.getAccessToken())
            );
            String tokenResponse = jwtGenerator.generateToken(jwtGenerator.getUsernameFromJWT(dtoRequestToken.getAccessToken()));
            Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
            return new DTOResponseToken(tokenResponse, dtoRequestToken.getRefreshToken(), roles);
        } else {
            logout(dtoRequestToken.getRefreshToken());
            return null;
        }
    }

    public DTOResponseToken logout(UUID refreshToken) {
        return repositoryToken.findByRefreshToken(refreshToken)
                .map(token -> {
                    repositoryToken.deleteById(token.getId());
                    return mapperInterface.toDTO(token);
                })
                .orElseThrow(() ->
                    new RuntimeException("Token not found.")
                );
    }
    public void addAttempt(DTORequestUserAuth dtoRequestUserAuth) {
        User entity = repositoryUser.findByUsername(dtoRequestUserAuth.getUsername()).orElseThrow(() -> new RuntimeException("Resource not found"));
        entity.setAttempt(entity.getAttempt() == null ? 0 : entity.getAttempt() + 1);
        if(entity.getAttempt() > 4) {
            entity.setActive(false);
            throw new RuntimeException("User blocked");
        }
        repositoryUser.save(entity);
    }
    public void validUser(String username) {
        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
        if(!entity.getActive()) throw new RuntimeException("User blocked");
    }
    public void register(String username, String email, String password, String captchaToken) {
        captchaTest(captchaToken);
        serviceUser.create(new DTORequestUser(username, email, password));
    }
    public void resetPassword(String username, String captchaToken) {
        captchaTest(captchaToken);
        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
        serviceEmail.sendSimpleMessage(entity.getEmail(), "Recovery password", entity.getPassword());
    }
    public void resetTotp(String username, String captchaToken) throws Exception {
        captchaTest(captchaToken);
        User entity = repositoryUser.findByUsername(username).orElseThrow(() -> new RuntimeException("Resource not found"));
        serviceEmail.sendSimpleMessage(entity.getEmail(), "Recovery totp", e2EE.decrypt(entity.getSecret()));
    }
    public void captchaTest(String captchaToken) {
        if (!serviceRecaptcha.validateCaptcha(captchaToken)) {
            LOGGER.error("Invalid or suspicious CAPTCHA: {}", captchaToken);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or suspicious CAPTCHA");
        }
    }
}