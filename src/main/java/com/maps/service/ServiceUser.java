package com.maps.service;

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
import com.maps.utils.QRCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Slf4j
@Service
public class ServiceUser extends ServiceGeneric<User, DTORequestUser, DTOResponseUser> {

    private final Information information;
    private final RepositoryUser repositoryUser;
    private final RepositoryRole repositoryRole;
    private final ServiceTOTP serviceTOTP;
    private final ServiceEmail serviceEmail;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final E2EE e2EE;
    
    public ServiceUser(RepositoryGeneric<User> repositoryGeneric, MapperInterface<User, DTORequestUser, DTOResponseUser> mapperInterface, RepositoryUser repositoryUser, Information information, ServiceTOTP serviceTOTP, Environment env, PasswordEncoder passwordEncoder, E2EE e2EE, RepositoryRole repositoryRole, ServiceEmail serviceEmail) {
        super(User.class, information, repositoryGeneric, mapperInterface);
        this.repositoryUser = repositoryUser;
        this.serviceTOTP = serviceTOTP;
        this.information = information;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.e2EE = e2EE;
        this.repositoryRole = repositoryRole;
        this.serviceEmail = serviceEmail;
    }
    @Override
    public DTOResponseUser create(DTORequestUser created){
        User user = MapStruct.MAPPER.toObject(created);
        String password = generateSecurePassword();
        String secret = serviceTOTP.generateSecret();
        user.setPassword(passwordEncoder.encode(password));
        try {
            user.setSecret(e2EE.encrypt(secret));
            Set<Role> roles = new HashSet<>();
            roles.add(repositoryRole.findByName("VIEWER"));
            user.setRole(roles);
            user.setActive(true);
            user.setAttempt(0);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = buildWelcomeEmailContent(user.getUsername(), password, secret);
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Account Created", emailContent, qrCodeBytes, "qrcode.png", "image/png");
        } catch (MailException e) {
            log.error("Error sending email for {}: {}", user.getUsername(), e.getMessage());
            throw new BadCredentialsException("Failed to send welcome email");
        } catch (Exception e) {
            log.error("Error generating TOTP secret for {}: {}", created, e.getMessage(), e);
            throw new BadCredentialsException("Invalid secret");
        }
        log.info("{} creating a new user", information.getCurrentUser().orElse("Unknown User"));
        return MapStruct.MAPPER.toDTO(repositoryUser.save(user));
    }
    @Override
    public DTOResponseUser update(UUID id, DTORequestUser updated){
        User user = repositoryUser.findById(id).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        user.setUsername(updated.username());
        user.setEmail(updated.email());
        user.setRole(updated.role());
        user.setActive(true);
        log.info("{} updating entity with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
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
    public DTOResponseUser changePassword(DTORequestUserPassword updated){
        User user = isValidToChange(updated.id());
        try {
            Objects.requireNonNull(user).setPassword(passwordEncoder.encode(updated.password()));
            repositoryUser.save(user);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = buildWelcomeEmailContent(user.getUsername(), updated.password(), e2EE.decrypt(user.getSecret()));
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Change password requested", emailContent, qrCodeBytes, "qrcode.png", "image/png");
            log.info("{} changing user password with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
            return MapStruct.MAPPER.toDTO(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset password for user: " + user.getUsername());
        }
    }
    public DTOResponseUser resetPassword(String username) {
        User user = isValidToChange(username);
        try {
            String password = generateSecurePassword();
            user.setPassword(passwordEncoder.encode(password));
            repositoryUser.save(user);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = buildWelcomeEmailContent(user.getUsername(), password, e2EE.decrypt(user.getSecret()));
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Reset password requested", emailContent, qrCodeBytes, "qrcode.png", "image/png");
            log.info("{} changing user password with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
            return MapStruct.MAPPER.toDTO(user);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to reset password for user: " + user.getUsername());
        }
    }
    public DTOResponseUser resetSecret(String username) {
        User user = isValidToChange(username);
        String secret = serviceTOTP.generateSecret();
        try {
            Objects.requireNonNull(user).setSecret(e2EE.encrypt(secret));
            repositoryUser.save(user);
            byte[] qrCodeBytes = QRCode.generateQRCodeBytes(buildTotpUri(user.getUsername(), user.getSecret()), 200);
            String emailContent = buildWelcomeEmailContent(user.getUsername(), "Your password is the same as before", secret);
            serviceEmail.sendHtmlMessageWithAttachment(user.getEmail(), "Reset TOTP requested", emailContent, qrCodeBytes, "qrcode.png", "image/png");
            log.info("{} resetting user totp with ID: {}", information.getCurrentUser().orElse("Unknown User"), user.getId());
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
            log.warn("{} attempted unauthorized access to user with ID: {}", information.getCurrentUser().orElse("Unknown User"), id);
            throw new EntityNotFoundException("i Resource not found");
        }
    }
    public User isValidToChange(String username) {
        try {
            repositoryUser.findByUsername(username.trim()).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        } catch (Exception e) {
            throw new EntityNotFoundException("Resource not found");
        }
        User user = repositoryUser.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Resource not found"));
        if (user.getUsername() != null) {
            return user;
        } else {
            log.warn("{} attempted unauthorized access to user with username: {}", information.getCurrentUser().orElse("Unknown User"), username);
            throw new EntityNotFoundException("i Resource not found");
        }
    }
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
    private String buildTotpUri(String username, String secret) throws Exception {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                username,
                username + "@auth.com",
                e2EE.decrypt(secret),
                env.getRequiredProperty("application.name")
        );
    }
    private String buildWelcomeEmailContent(String username, String password, String secret) {
        return String.format("""
            <p><strong>Username:</strong> %s</p>
            <p><strong>Password:</strong> %s</p>
            <p><strong>Secret:</strong> %s</p>
            <p><strong>TOTP QR Code:</strong> Veja o anexo "qrcode.png"</p>
            <p>Escaneie o QR Code com seu aplicativo autenticador (Google Authenticator, Authy, etc.)</p>
            """, username, password, secret);
    }
}
