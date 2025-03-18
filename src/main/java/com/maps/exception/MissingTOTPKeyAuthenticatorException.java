package com.maps.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public class MissingTOTPKeyAuthenticatorException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public MissingTOTPKeyAuthenticatorException(String msg) {
        super(msg);
    }
}
