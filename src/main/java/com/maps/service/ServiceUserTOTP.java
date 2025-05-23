package com.maps.service;

import com.maps.MapsApplication;
import com.maps.exception.MissingTOTPKeyAuthenticatorException;
import com.maps.persistence.model.User;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.utils.E2EE;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base32;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
@RequiredArgsConstructor
public class ServiceUserTOTP {

    private final RepositoryUser repositoryUser;
    private final Environment env;
    private final E2EE e2EE;
    private final static Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);

    public void validateTOTP(String userName, Integer totpKey) {
        LOGGER.info("Validating TOTP for user: {}", userName);
        User user = repositoryUser.findByUsername(userName).orElseThrow(() -> new BadCredentialsException("User not found"));
        String secret = user.getSecret();
        try {
            secret = e2EE.decrypt(secret);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid secret");
        }
        if (StringUtils.hasText(secret)) {
            if (totpKey != null) {
                try {
                    if (!verifyCode(secret, totpKey, Integer.parseInt(env.getRequiredProperty("application.time")))) {
                        LOGGER.info("TOTP code {} was not valid for user {}", totpKey, userName);
                        throw new BadCredentialsException("Invalid TOTP code");
                    }
                } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new InternalAuthenticationServiceException("Failed to verify TOTP code due to cryptographic error", e);
                }
            } else {
                throw new MissingTOTPKeyAuthenticatorException("TOTP code is mandatory");
            }
        }
    }
    public String generateSecret() {
        byte[] buffer = new byte[10];
        new SecureRandom().nextBytes(buffer);
        return new String(new Base32().encode(buffer));
    }
    public boolean verifyCode(String secret, int code, int variance)
            throws InvalidKeyException, NoSuchAlgorithmException {
        long timeIndex = System.currentTimeMillis() / 1000 / 30;
        byte[] secretBytes = new Base32().decode(secret);
        for (int i = -variance; i <= variance; i++) {
            long calculatedCode = getCode(secretBytes, timeIndex + i);
            if (calculatedCode == code) {
                return true;
            }
        }
        return false;
    }
    private long getCode(byte[] secret, long timeIndex) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(new SecretKeySpec(secret, "HmacSHA1"));
        ByteBuffer buffer = ByteBuffer.allocate(8).putLong(timeIndex);
        byte[] hash = mac.doFinal(buffer.array());
        int offset = hash[19] & 0xf;
        long truncatedHash = hash[offset] & 0x7f;
        for (int i = 1; i < 4; i++) {
            truncatedHash <<= 8;
            truncatedHash |= hash[offset + i] & 0xff;
        }
        return truncatedHash % 1000000;
    }
}