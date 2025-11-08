# Persistence Package

This package contains all data persistence-related classes including entity mapping, repositories, and data transfer objects.

## Main Persistence Files

### Entity Mappers (MapStruct)

- **`MapStruct.java`** - Main MapStruct configuration and common mapping utilities
- **`MapperAddress.java`** - Maps between Address entities and DTOs
- **`MapperChart.java`** - Maps between Chart entities and DTOs
- **`MapperChartArea.java`** - Maps between ChartArea entities and DTOs
- **`MapperCity.java`** - Maps between City entities and DTOs
- **`MapperCountry.java`** - Maps between Country entities and DTOs
- **`MapperGaugeStation.java`** - Maps between GaugeStation entities and DTOs
- **`MapperInternationalChart.java`** - Maps between InternationalChart entities and DTOs
- **`MapperMaritimeArea.java`** - Maps between MaritimeArea entities and DTOs
- **`MapperPrivilege.java`** - Maps between Privilege entities and DTOs
- **`MapperResearch.java`** - Maps between Research entities and DTOs
- **`MapperResearcher.java`** - Maps between Researcher entities and DTOs
- **`MapperRole.java`** - Maps between Role entities and DTOs
- **`MapperState.java`** - Maps between State entities and DTOs
- **`MapperToken.java`** - Maps between Token entities and DTOs
- **`MapperUser.java`** - Maps between User entities and DTOs
- **`MapperInterface.java`** - Common interface definitions for all mappers

## Subpackages

### model/
Contains JPA entity classes representing database tables:
- **Core Entities**: User, Role, Privilege, Token
- **Geographic Entities**: Country, State, City, ChartArea
- **Maritime Entities**: Chart, InternationalChart, MaritimeArea
- **Research Entities**: Research, Researcher, GaugeStation
- **Audit Entities**: Base classes with auditing fields

### payload/
Contains Data Transfer Objects (DTOs) for API communication:
- **request/**: DTOs for incoming API requests
- **response/**: DTOs for outgoing API responses
- Validation annotations and constraints

### repository/
Contains Spring Data JPA repository interfaces:
- CRUD operations for all entities
- Custom query methods
- Specifications for complex queries
- Pagination and sorting support

## Architecture Overview

### Entity Mapping Strategy

The persistence layer uses MapStruct for efficient entity-to-DTO mapping:

```java
@Mapper(componentModel = "spring")
public interface MapperUser extends MapperInterface<User, DTORequestUser, DTOResponseUser> {
    // Automatic mapping between User entity and DTOs
}
```

### Repository Pattern

All repositories extend `RepositoryGeneric` interface providing:
- **Standard CRUD**: findById, save, delete, findAll
- **Pagination**: Pageable support for large datasets
- **Auditing**: Automatic tracking of creation and modification
- **Soft Delete**: Logical deletion using deleted_at field

### Data Transfer Objects

#### Request DTOs
- Contain validation annotations
- Handle incoming API data
- Prevent over-posting attacks
- Support partial updates

#### Response DTOs
- Control data exposure
- Include computed fields
- Support nested relationships
- Prevent data leakage

### Database Design

#### Auditing Fields
All entities include:
- `created_at`: Timestamp of creation
- `updated_at`: Timestamp of last modification
- `created_by`: User who created the record
- `modified_by`: User who last modified the record
- `deleted_at`: Soft deletion timestamp

#### Relationships
- **User-Role**: Many-to-many relationship
- **Role-Privilege**: Many-to-many relationship
- **Geographic Hierarchy**: Country → State → City
- **Research Data**: Researcher → Research → GaugeStation

### Geographic Data Support

The persistence layer supports spatial data through:
- **PostGIS**: PostgreSQL spatial extensions
- **JTS**: Java Topology Suite for geometric operations
- **Spatial Queries**: Geographic distance and area calculations
- **Coordinate Systems**: Multiple spatial reference systems

### Performance Optimizations

- **Lazy Loading**: On-demand relationship loading
- **Caching**: Caffeine cache for frequently accessed data
- **Batch Operations**: Efficient bulk data operations
- **Connection Pooling**: Optimized database connections
- **Query Optimization**: Indexed columns and efficient queries

This persistence layer provides a robust, scalable foundation for the MAPS application's data management needs.