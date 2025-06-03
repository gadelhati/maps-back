package com.maps.exception;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({StaleStateException.class, OptimisticLockException.class})
//    public ResponseEntity<ApiError> handleOptimisticLockingException(RuntimeException ex, HttpServletRequest request) {
//        ValidationError validationError = new ValidationError(
//                "version",
//                null,
//                "Outdated version of the registry."
//        );
//        ApiError apiError = new ApiError(
//                HttpStatus.CONFLICT,
//                "Record has been changed or removed by another process.",
//                request.getRequestURI(),
//                List.of(validationError)
//        );
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
//    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllUncaughtExceptions(Exception exception, WebRequest webRequest) {
        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError(
                "globalError",
                null,
                exception.getMessage()
        ));
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred",
                webRequest.getDescription(false),
                validationErrors
        );
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException runtimeException) {
        List<ValidationError> validationErrors = new ArrayList<>();
        validationErrors.add(new ValidationError(
                "runtimeError",
                null,
                runtimeException.getMessage()
        ));
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "Resource not found",
                runtimeException.getMessage(),
                validationErrors
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
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