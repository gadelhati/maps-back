# Security Overview

The security of the Maps Backend API is managed by Spring Security and follows a **Stateless** authentication model based on **JWT (JSON Web Tokens)**.

## 1. Authentication (JWT)

*   **Fluxo:** The user sends credentials to the authentication endpoint. If successful, a JWT is generated and returned.
*   **Uso:** The client must include the JWT in the header of all subsequent requests. `Authorization: Bearer <token>`.

## 2. Authorization (Access Control)

Access control is implemented at two levels:

1.  **Nível de Endpoint:** Configured in `SecurityConfig` for specific routes..
2.  **Nível de Método:** Use the `@PreAuthorize` annotation on the `ControllerGeneric` to check the user's *Roles* (`ADMIN`, `MODERATOR`, `USER`) and *Authorities* (`user:create`, `user:retrieve`, etc.) before executing the method.

## 3. Additional Protection

*   **Rate Limiting:** Implemented to protect against brute-force and overload attacks.
*   **CSRF:** Enabled for requests that are not API requests (e.g., Thymeleaf forms).
*   **Security Headers:** Configuring HSTS, X-Content-Type-Options, and X-Frame-Options to mitigate common attacks.