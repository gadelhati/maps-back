# Architecture Overview

The **Maps Backend API** is built on a layered architecture following the **MVC (Model-View-Controller)** pattern, adapted for REST APIs.

## 1. Main Layers

| Layer            | Package       | Responsibility                                                             |
|:-----------------|:--------------|:---------------------------------------------------------------------------|
| **Presentation** | `controller`  | Handles HTTP requests, validates DTOs, and delegates to the Service layer. |
| **Service**      | `service`     | Contains the core business and transactional logic.                        |
| **Persistence**  | `persistence` | Interacts with the database (JPA/Hibernate).                               |

---

## 2. Key Design Patterns

### 2.1. CRUD Abstraction (`ControllerGeneric` and `ServiceGeneric`)

* **`ControllerGeneric.java`:** Implements all CRUD operations, HATEOAS, and security rules (`@PreAuthorize`) in an abstract manner. This ensures that all entity endpoints follow the same pattern without code duplication.  
* **`ServiceGeneric.java`:** Contains common business logic for CRUD operations, such as pagination and sorting.

---

### 2.2. Geospatial Domain (PostGIS)

* Uses **PostGIS** to store and query georeferenced data.  
* **Hibernate Spatial** is the ORM used to map geospatial entities (e.g., `Geometry`, `Point`) to Java objects.

---

## 3. Request Flow (Example: City Creation)

1. **Request:** The client sends a `POST` request to `/city` with the `DTORequestCity`.  
2. **Controller:** `ControllerCity` receives the request, handled by `ControllerGeneric`.  
3. **Validation:** The `@Valid` annotation validates the `DTORequestCity`.  
4. **Service:** `ServiceCity` (inheriting from `ServiceGeneric`) executes business logic and transaction management.  
5. **Persistence:** `RepositoryCity` saves the entity to the database.  
6. **Response:** `ControllerGeneric` returns a `ResponseEntity.created` containing the `DTOResponseCity` and a HATEOAS link to the new resource.
