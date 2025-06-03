package com.maps.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ApiError> buildApiError(HttpStatus status, String title, String field, String message, HttpServletRequest request) {
        List<ValidationError> validationErrors = List.of(new ValidationError(field, null, message));
        ApiError apiError = new ApiError(status, title, request.getRequestURI(), validationErrors);
        return new ResponseEntity<>(apiError, status);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        return buildApiError(HttpStatus.UNAUTHORIZED, "Invalid credentials", "Credentials", exception.getMessage(), request);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        return buildApiError(HttpStatus.UNAUTHORIZED, "Authentication failed", "Authentication", exception.getMessage(), request);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return buildApiError(HttpStatus.NOT_FOUND, "Resource not found", "resource", exception.getMessage(), request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaughtExceptions(Exception exception, HttpServletRequest request) {
        return buildApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", "globalError", exception.getMessage(), request);
    }
    @Override @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders httpHeaders,
            @NonNull HttpStatusCode httpStatusCode,
            @NonNull WebRequest webRequest) {
        List<ValidationError> validationErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.add(new ValidationError(
                        error.getField(),
                        error.getRejectedValue(),
                        error.getDefaultMessage()))
        );
        ex.getBindingResult().getGlobalErrors().forEach(error ->
                validationErrors.add(new ValidationError(
                        error.getObjectName(),
                        null,
                        error.getDefaultMessage()))
        );
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                webRequest.getDescription(false),
                validationErrors
        );
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}