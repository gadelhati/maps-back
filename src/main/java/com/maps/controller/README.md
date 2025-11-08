# Controller Package

This package contains all REST API controllers that handle HTTP requests and define the application's web endpoints.

## Main Controller Files

### Authentication & User Management

- **`ControllerAuth.java`** - Handles authentication endpoints (login, logout, token refresh, password reset)
- **`ControllerUser.java`** - Manages user-related operations (registration, profile management, user administration)
- **`ControllerThymeleaf.java`** - Provides server-side rendered pages using Thymeleaf templates

### Geographic & Maritime Data

- **`ControllerChart.java`** - Manages nautical charts and chart-related operations
- **`ControllerChartArea.java`** - Handles chart area definitions and boundaries
- **`ControllerInternationalChart.java`** - Manages international nautical chart standards
- **`ControllerMaritimeArea.java`** - Handles maritime zone and area management
- **`ControllerGaugeStation.java`** - Manages meteorological and oceanographic gauge stations

### Geographic Locations

- **`ControllerCountry.java`** - Manages country data and geographic boundaries
- **`ControllerState.java`** - Handles state/province information within countries
- **`ControllerCity.java`** - Manages city and urban area data
- **`ControllerCompositeUnit.java`** - Handles composite geographic units

### Research & Data Collection

- **`ControllerResearch.java`** - Manages research projects and data collection campaigns
- **`ControllerResearcher.java`** - Handles researcher profiles and credentials

### System Administration

- **`ControllerRole.java`** - Manages user roles and role assignments
- **`ControllerPrivilege.java`** - Handles user privileges and permissions
- **`ControllerUpload.java`** - Manages file upload operations and metadata

### Generic Operations

- **`ControllerGeneric.java`** - Provides generic CRUD operations for common entities
- **`ControllerInterface.java`** - Defines common controller interfaces and contracts

## Architecture

All controllers follow RESTful design principles and include:

- **HTTP Method Mapping**: GET, POST, PUT, DELETE operations
- **Request/Response DTOs**: Data Transfer Objects for API communication
- **Validation**: Input validation using Bean Validation annotations
- **Error Handling**: Standardized error responses via GlobalExceptionHandler
- **Security**: Role-based access control and authentication requirements
- **Documentation**: OpenAPI/Swagger annotations for API documentation

## Common Features

- **CRUD Operations**: Create, Read, Update, Delete functionality
- **Pagination**: Support for paginated responses
- **Filtering**: Query parameter-based filtering
- **Sorting**: Configurable result sorting
- **Rate Limiting**: Request throttling for API protection
- **CORS Support**: Cross-origin resource sharing configuration

## Response Format

All controllers return standardized JSON responses with appropriate HTTP status codes and follow the application's error handling conventions.