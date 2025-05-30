package com.maps.service;

import com.maps.MapsApplication;
import com.maps.persistence.MapStruct;
import com.maps.persistence.MapperInterface;
import com.maps.persistence.model.Role;
import com.maps.persistence.model.User;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserPassword;
import com.maps.persistence.payload.response.DTOResponseUser;
import com.maps.persistence.repository.RepositoryGeneric;
import com.maps.persistence.repository.RepositoryRole;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.utils.E2EE;
import com.maps.utils.Information;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author Marcelo Ribeiro Gadelha
 * @mail gadelha.ti@gmail.com
 * @link www.gadelha.eti.br
 **/

@Service
public class ServiceUser extends ServiceGeneric<User, DTORequestUser, DTOResponseUser> {

    private final Information information;
    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final ServiceUserTOTP serviceUserTOTP;
    private final ServiceEmail serviceEmail;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final E2EE e2EE;
    private final MapperInterface<User, DTORequestUser, DTOResponseUser> mapperInterface;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    public ServiceUser(RepositoryGeneric<User> repositoryGeneric, MapperInterface<User, DTORequestUser, DTOResponseUser> mapperInterface, RepositoryUser repositoryUser, Information information, ServiceUserTOTP serviceUserTOTP, Environment env, PasswordEncoder passwordEncoder, E2EE e2EE, RepositoryRole repositoryRole, ServiceEmail serviceEmail) {
        super(new Information(), repositoryGeneric, mapperInterface);
        this.repositoryUser = repositoryUser;
        this.mapperInterface = mapperInterface;
        this.serviceUserTOTP = serviceUserTOTP;
        this.information = information;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.e2EE = e2EE;
        this.repositoryRole = repositoryRole;
        this.serviceEmail = serviceEmail;
    }
    @Override @Transactional
    public DTOResponseUser create(DTORequestUser created){
        User user = MapStruct.MAPPER.toObject(created);
        String password = generateSecurePassword();
        String secret = serviceUserTOTP.generateSecret();
        user.setPassword(passwordEncoder.encode(password));
        try {
            user.setSecret(e2EE.encrypt(secret));
            Set<Role> roles = new HashSet<>();
            roles.add(repositoryRole.findByName("VIEWER"));
            user.setRole(roles);
            serviceEmail.sendSimpleMessage(user.getEmail(), "Created requested", "Username: " + created.getUsername() + "\nPassword: " + password + "\nSecret: " + secret + "\nTotpKey: " +
                    String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", user.getUsername(), user.getUsername() + "@auth.com", e2EE.decrypt(user.getSecret()), env.getRequiredProperty("application.name")));
        } catch (Exception e) {
            LOGGER.error("Error to {} generating TOTP secret for {}: {}", information.getCurrentUser(), created, e.getMessage());
            throw new BadCredentialsException("Invalid secret");
        }
        LOGGER.info("{} creating a new user", information.getCurrentUser().orElse("Unknown User"));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override @Transactional
    public DTOResponseUser update(DTORequestUser updated){
        User user = repositoryUser.findById(updated.getId()).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        user.setUsername(updated.getUsername());
        user.setEmail(updated.getEmail());
        user.setRole(updated.getRole());
        user.setActive(true);
        LOGGER.info("{} updating entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), updated.getId());
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    public boolean existsByUsername(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByUsernameIgnoreCase(value);
    }
    public boolean existsByUsernameAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }
        return repositoryUser.existsByUsernameIgnoreCaseAndIdNot(value, id);
    }
    public boolean existsByEmail(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByEmailIgnoreCase(value);
    }
    public boolean existsByEmailAndIdNot(String value, UUID id) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Value must not be null or empty.");
        }
        return repositoryUser.existsByEmailIgnoreCaseAndIdNot(value, id);
    }
    @Transactional
    public DTOResponseUser changePassword(DTORequestUserPassword updated){
        User user = isValidToChange(updated.getId());
        try {
            Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.getPassword()));
            repositoryUser.save(user);
            serviceEmail.sendSimpleMessage(
                    user.getEmail(),
                    "Change password requested",
                    "Username: " + user.getUsername() + "\nPassword: " + updated.getPassword() + "\nSecret: " + e2EE.decrypt(user.getSecret()) + "\nTotpKey: " +
                    String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", user.getUsername(), user.getUsername() + "@auth.com", e2EE.decrypt(user.getSecret()), env.getRequiredProperty("application.name")));
            LOGGER.info("{} changing user password with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
            return MapStruct.MAPPER.toDTO(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset password for user: " + user.getUsername());
        }
    }
    @Transactional
    public DTOResponseUser resetPassword(UUID id) {
        User user = isValidToChange(id);
        try {
            String password = generateSecurePassword();
            user.setPassword(passwordEncoder.encode(password));
            repositoryUser.save(user);
            serviceEmail.sendSimpleMessage(
                    user.getEmail(),
                    "Reset password requested",
                    "Username: " + user.getUsername() + "\nPassword: " + password + "\nSecret: " + e2EE.decrypt(user.getSecret()) + "\nTotpKey: " +
                            String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", user.getUsername(), user.getUsername() + "@auth.com", e2EE.decrypt(user.getSecret()), env.getRequiredProperty("application.name")));
            LOGGER.info("{} changing user password with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
            return MapStruct.MAPPER.toDTO(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset password for user: " + user.getUsername());
        }
    }
    @Transactional
    public DTOResponseUser resetSecret(UUID id) {
        User user = isValidToChange(id);
        try {
            Objects.requireNonNull(user).setSecret(e2EE.encrypt(serviceUserTOTP.generateSecret()));
            repositoryUser.save(user);
            serviceEmail.sendSimpleMessage(
                    user.getEmail(),
                    "Reset TOTP requested",
                    "Username: " + user.getUsername() + "\nSecret: " + e2EE.decrypt(user.getSecret()) + "\nTotpKey: " +
                    String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", user.getUsername(), user.getUsername() + "@auth.com", e2EE.decrypt(user.getSecret()), env.getRequiredProperty("application.name")));
            LOGGER.info("{} resetting user totp with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
            return MapStruct.MAPPER.toDTO(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset TOTP for user: " + user.getUsername());
        }
    }
    public User isValidToChange(UUID id) {
        User user = repositoryUser.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        User userCurrent = repositoryUser.findByUsername(information.getCurrentUser().orElse("Unknown User")).orElseThrow(() -> new EntityNotFoundException("Current user not found"));
        if (userCurrent.getUsername() != null && user.getUsername() != null &&
                userCurrent.getUsername().equals(user.getUsername()) ||
                userCurrent.getRole().stream().anyMatch(role -> role.getName().equals("ADMIN"))) {
            return user;
        } else {
            LOGGER.warn("{} attempted unauthorized access to user with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
            throw new EntityNotFoundException("i Resource not found");
        }
    }
    @Transactional
    public String generateSecurePassword() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(upper.charAt(random.nextInt(upper.length())));
        password.append(lower.charAt(random.nextInt(lower.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(special.charAt(random.nextInt(special.length())));
        String allChars = upper + lower + digits + special;
        for (int i = 4; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        char[] chars = password.toString().toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}