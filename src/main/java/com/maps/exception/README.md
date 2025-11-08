# Exception Package

This package contains custom exception classes and error handling mechanisms for the MAPS application.

## Main Exception Files

### Core Exception Classes

- **`ApiError.java`** - Standard error response model containing error details, status codes, and messages
- **`GlobalExceptionHandler.java`** - Centralized exception handler using @ControllerAdvice for consistent error responses
- **`ValidationError.java`** - Specific error model for validation failures with field-level error details
- **`Validator.java`** - Custom validation utilities and helper methods

### Specific Exception Types

- **`ResourceNotFoundException.java`** - Thrown when requested resources are not found (HTTP 404)
- **`MissingTOTPKeyAuthenticatorException.java`** - Thrown when TOTP (Time-based One-Time Password) authentication fails

## Subpackages

### annotation/
Contains custom validation annotations and constraint validators:
- Custom Bean Validation annotations
- Field-level validation rules
- Cross-field validation constraints

## Exception Handling Strategy

### Global Exception Handler

The `GlobalExceptionHandler` provides centralized error handling for:

- **Validation Errors**: Bean Validation failures with detailed field messages
- **Authentication Errors**: Security-related exceptions with appropriate responses
- **Resource Errors**: Not found exceptions with helpful error messages
- **System Errors**: Internal server errors with sanitized responses
- **Custom Errors**: Application-specific business rule violations

### Error Response Format

All exceptions are converted to standardized `ApiError` responses containing:

```json
{
  "timestamp": "2025-11-07T18:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/users",
  "details": [
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ]
}
```

### HTTP Status Code Mapping

- **400 Bad Request**: Validation errors, malformed requests
- **401 Unauthorized**: Authentication failures
- **403 Forbidden**: Authorization failures
- **404 Not Found**: Resource not found errors
- **409 Conflict**: Business rule violations
- **500 Internal Server Error**: Unexpected system errors

## Validation Features

### Field Validation

- Email format validation
- Password strength requirements
- Required field validation
- String length constraints
- Numeric range validation

### Business Rule Validation

- Unique constraint validation
- Cross-field dependency validation
- Custom business logic validation
- Data integrity validation

## Security Considerations

- **Error Message Sanitization**: Prevents information leakage
- **Stack Trace Protection**: Sensitive details hidden in production
- **Rate Limiting**: Prevents exception-based attacks
- **Logging**: Comprehensive error logging for debugging

## Usage Examples

```java
// Throwing custom exceptions
throw new ResourceNotFoundException("User not found with ID: " + id);

// Validation error handling
@Valid @RequestBody UserDto userDto // Automatically handled by GlobalExceptionHandler
```

This exception handling system ensures consistent, secure, and user-friendly error responses across the entire application.