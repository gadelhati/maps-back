# Utils Package

This package contains utility classes that provide common functionality and helper methods used throughout the MAPS application.

## Main Utility Files

### Security & Encryption

- **`E2EE.java`** - End-to-End Encryption utilities providing:
  - **AES Encryption**: Symmetric encryption for sensitive data
  - **Key Generation**: Secure cryptographic key creation
  - **Data Protection**: Encrypt/decrypt sensitive information
  - **Security Standards**: Industry-standard encryption algorithms

### Authentication & QR Code

- **`QRCode.java`** - QR Code generation utilities for:
  - **TOTP Setup**: Generate QR codes for two-factor authentication
  - **Authentication Apps**: Compatible with Google Authenticator, Authy
  - **Secret Keys**: Encode TOTP secret keys in QR format
  - **User Registration**: Facilitate mobile app setup

### System Information

- **`Information.java`** - System information and environment utilities:
  - **Application Metadata**: Version, build information
  - **System Properties**: Runtime environment details
  - **Configuration Info**: Application settings summary
  - **Health Checks**: System status and diagnostics

### Caching Constants

- **`CacheConstants.java`** - Cache configuration constants and utilities:
  - **Cache Names**: Standardized cache key definitions
  - **TTL Settings**: Time-to-live configurations for different cache types
  - **Cache Policies**: Eviction and refresh strategies
  - **Performance Tuning**: Cache size and duration constants

## Utility Features

### Encryption Capabilities

The `E2EE` class provides:

```java
// Encrypt sensitive data
String encryptedData = E2EE.encrypt(sensitiveData, secretKey);

// Decrypt when needed
String originalData = E2EE.decrypt(encryptedData, secretKey);
```

**Use Cases:**
- Protecting user personal information
- Securing API keys and tokens
- Database field encryption
- Temporary data protection

### QR Code Generation

The `QRCode` class supports:

```java
// Generate TOTP QR code for user
byte[] qrCodeImage = QRCode.generateTOTPQRCode(username, secretKey, issuer);
```

**Features:**
- **TOTP Integration**: Direct integration with authenticator apps
- **Custom Branding**: Application-specific QR code styling
- **Error Correction**: High reliability QR code generation
- **Multiple Formats**: Support for various image formats

### System Information Access

The `Information` class provides:

```java
// Get application version
String version = Information.getApplicationVersion();

// Get system status
Map<String, Object> status = Information.getSystemStatus();
```

**Capabilities:**
- **Version Tracking**: Application and dependency versions
- **Environment Detection**: Development vs. production
- **Resource Monitoring**: Memory and CPU usage
- **Configuration Summary**: Active profiles and settings

### Cache Management

The `CacheConstants` class defines:

```java
// Cache configuration
public static final String USER_ROLES_CACHE = "user-roles";
public static final Duration CACHE_TTL = Duration.ofMinutes(30);
```

**Cache Types:**
- **User Roles**: Fast access control checks
- **Permissions**: Reduced database queries
- **Session Data**: Temporary user information
- **Geographic Data**: Spatial calculation results

## Design Principles

### Singleton Pattern

Utility classes follow singleton or static patterns:
- **No State**: Stateless utility methods
- **Thread Safety**: Concurrent access support
- **Performance**: Minimal memory footprint
- **Reusability**: Common functionality across layers

### Security First

All utilities prioritize security:
- **Encryption Standards**: Industry-best practices
- **Key Management**: Secure key generation and storage
- **Data Protection**: Sensitive information handling
- **Audit Logging**: Security event tracking

### Performance Optimization

Utilities are designed for efficiency:
- **Caching**: Reduce computational overhead
- **Lazy Loading**: Initialize resources on demand
- **Resource Management**: Proper cleanup and disposal
- **Memory Efficiency**: Minimal object allocation

## Common Usage Patterns

### Configuration Management

```java
// Get cache TTL for specific operation
Duration ttl = CacheConstants.getTTL(operationType);

// Check if feature is enabled
boolean enabled = Information.isFeatureEnabled("two-factor-auth");
```

### Security Operations

```java
// Encrypt user data before storage
String encrypted = E2EE.encrypt(userData, userKey);

// Generate setup QR for new user
byte[] qrCode = QRCode.generateSetupQR(user);
```

### System Monitoring

```java
// Log system information
logger.info("System Status: {}", Information.getSystemStatus());

// Check application health
if (Information.isHealthy()) {
    // Continue operation
}
```

This utilities package provides essential cross-cutting functionality that enhances security, performance, and maintainability across the entire MAPS application.