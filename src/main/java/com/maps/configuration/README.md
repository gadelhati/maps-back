# Configuration Package

This package contains all Spring Boot configuration classes that define application behavior and setup.

## Main Configuration Files

### Core Configurations

- **`ConfigurationAudit.java`** - Configures JPA auditing features (created_at, updated_at, created_by, modified_by)
- **`ConfigurationCache.java`** - Sets up application caching using Caffeine cache implementation for user roles and permissions
- **`ConfigurationCors.java`** - Configures Cross-Origin Resource Sharing (CORS) policies for API endpoints
- **`ConfigurationHateoas.java`** - Configures HATEOAS (Hypermedia as the Engine of Application State) for RESTful APIs
- **`ConfigurationJts.java`** - Configures JTS (Java Topology Suite) for geographic and spatial data processing
- **`ConfigurationMessages.java`** - Sets up internationalization and validation message sources
- **`ConfigurationOpenAPI.java`** - Configures Swagger/OpenAPI documentation for API endpoints
- **`ConfigurationStorage.java`** - Configures file storage and upload handling

## Subpackages

### interceptor/
Contains HTTP request interceptors for handling cross-cutting concerns:
- Request filtering and preprocessing
- Hibernate session management
- Security context setup

### security/
Contains security configuration classes:
- Authentication and authorization setup
- JWT token configuration
- Password encoding and security policies
- User details service configuration

## Purpose

These configuration classes customize Spring Boot's auto-configuration to meet the specific needs of the MAPS application, including:

- **Data Management**: Auditing, caching, and spatial data handling
- **API Features**: CORS, HATEOAS, OpenAPI documentation
- **Security**: Authentication, authorization, and data protection
- **Internationalization**: Multi-language support and validation messages
- **File Handling**: Upload and storage management

All configurations follow Spring Boot best practices and use proper annotation-based configuration patterns.