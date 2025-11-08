# Service Package

This package contains the business logic layer of the MAPS application, implementing core business rules and coordinating between controllers and repositories.

## Main Service Files

### Authentication & Security

- **`ServiceAuth.java`** - Handles authentication, login, logout, password management, and token operations
- **`ServiceUser.java`** - Manages user registration, profile updates, password changes, and user administration
- **`ServiceCustomUserDetails.java`** - Implements Spring Security UserDetailsService for authentication
- **`ServiceTOTP.java`** - Manages Time-based One-Time Password (TOTP) for two-factor authentication
- **`ServiceRecaptcha.java`** - Integrates Google reCAPTCHA for bot protection

### Geographic & Maritime Data

- **`ServiceChart.java`** - Manages nautical charts, metadata, and chart-related business logic
- **`ServiceChartArea.java`** - Handles chart area definitions, boundaries, and spatial operations
- **`ServiceInternationalChart.java`** - Manages international chart standards and compliance
- **`ServiceMaritimeArea.java`** - Handles maritime zones, territorial waters, and navigation areas
- **`ServiceGaugeStation.java`** - Manages meteorological and oceanographic measurement stations

### Geographic Locations

- **`ServiceCountry.java`** - Manages country data, boundaries, and geographic information
- **`ServiceState.java`** - Handles state/province management within countries
- **`ServiceCity.java`** - Manages city data, urban areas, and municipal information
- **`ServiceCompositeUnit.java`** - Handles complex geographic composite units

### Research & Data Management

- **`ServiceResearch.java`** - Manages research projects, data collection campaigns, and scientific studies
- **`ServiceResearcher.java`** - Handles researcher profiles, credentials, and academic information

### System Administration

- **`ServiceRole.java`** - Manages user roles, role assignments, and role hierarchies
- **`ServicePrivilege.java`** - Handles user privileges, permissions, and access control
- **`ServiceAuditorAwareImpl.java`** - Implements Spring Data auditing for tracking data changes

### Infrastructure Services

- **`ServiceEmail.java`** - Email service interface definition
- **`ServiceEmailImpl.java`** - Email service implementation using SMTP
- **`ServiceStorage.java`** - File storage service interface
- **`ServiceStorageImplement.java`** - File storage implementation for uploads and file management

### Generic Operations

- **`ServiceGeneric.java`** - Provides generic CRUD operations and common business logic
- **`ServiceInterface.java`** - Defines common service interfaces and contracts

## Service Architecture

### Business Logic Layer

Services implement the core business rules and coordinate between:
- **Controllers**: Receive and validate requests
- **Repositories**: Perform data operations
- **External APIs**: Integrate with third-party services

### Transaction Management

All services use Spring's transaction management:
```java
@Transactional
public User createUser(DTORequestUser request) {
    // Business logic with automatic transaction handling
}
```

### Validation & Business Rules

Services enforce:
- **Data Integrity**: Cross-entity validation
- **Business Constraints**: Domain-specific rules
- **Security Policies**: Access control and data protection
- **Data Consistency**: Maintain referential integrity

### Cache Integration

Services utilize caching for performance optimization:
- **User Roles**: Cached for quick access control checks
- **Permissions**: Cached to reduce database queries
- **Geographic Data**: Cached for spatial operations

### Error Handling

Services implement comprehensive error handling:
- **Validation Errors**: Business rule violations
- **Resource Errors**: Not found or conflict scenarios
- **Integration Errors**: External service failures
- **System Errors**: Infrastructure-related issues

## Key Features

### Email Integration

- **SMTP Configuration**: Gmail integration for email services
- **Template Support**: HTML email templates
- **Notification System**: Automated email notifications
- **Error Handling**: Robust email delivery error management

### File Management

- **Upload Handling**: Secure file upload processing
- **Storage Management**: File system and cloud storage support
- **Metadata Tracking**: File information and versioning
- **Security**: File type validation and malware protection

### Geographic Processing

- **Spatial Operations**: Geographic calculations and transformations
- **Coordinate Systems**: Multiple spatial reference system support
- **Boundary Validation**: Geographic boundary checking
- **Distance Calculations**: Haversine formula and spatial queries

### Security Features

- **Password Security**: BCrypt hashing and strength validation
- **Token Management**: JWT token generation and validation
- **Two-Factor Authentication**: TOTP implementation
- **Rate Limiting**: Request throttling and abuse prevention

### Audit Trail

- **Change Tracking**: Automatic audit logging
- **User Attribution**: Track who made changes
- **Timestamp Recording**: When changes occurred
- **Data History**: Maintain historical data versions

This service layer provides a comprehensive business logic foundation that is secure, scalable, and maintainable.