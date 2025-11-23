# 🗺️ _Maps_ API

[![GitHub stars](https://img.shields.io/github/stars/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)
[![GitHub forks](https://img.shields.io/github/forks/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)

![GitHub last commit](https://img.shields.io/github/last-commit/gadelhati/maps-back)
![Test Coverage](https://img.shields.io/badge/coverage-5%25-orange)
![Build Status](https://img.shields.io/badge/build-passing-brightgreen)
![Code Quality](https://img.shields.io/badge/code%20quality-A-brightgreen)

## **Necessary Tech stack**

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue?logo=postgresql)
![PostGIS](https://img.shields.io/badge/PostGIS-3.5-blue?logo=postgis)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)
![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.11-green?logo=testing)
![Testcontainers](https://img.shields.io/badge/Testcontainers-1.20.4-blue?logo=testcontainers)

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-2025.1.1.1-000000?logo=intellijidea)
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2.2-brightgreen?logo=spring)
![Maven](https://img.shields.io/badge/Maven-3.12.1-blue?logo=apachemaven)

## **Description**

**Maps API** is a robust and modern platform for consolidating georeferenced data, designed for spatial analysis, real-time monitoring, and interdisciplinary applications.

## **Summary**
- [Architecture & Technologies](#architecture--technologies)
- [Quality & Testing](#quality--testing)
- [Roadmap](#roadmap)
- [Environment Configuration](#environment-configuration)
- [How to start](#how-to-start)
- [Deploy](#deploy)
- [API Documentation](#api-documentation)
- [Contribution](#contribution)
- [Useful Git Commands](#useful-git-commands-)
- [Developers](#developers)
- [License](#license)

## **Architecture & Technologies**

### **Detailed Tech Stack**

| Category          | Tecnology / Padrão                            | Detalhes                                                      |
|:------------------|:----------------------------------------------|:--------------------------------------------------------------|
| **Framework**     | Java 17, Spring Boot 3.5.4                    | Modern and robust framework for RESTful APIs                  |
| **Persistence**   | PostgreSQL 17.5, PostGIS, Hibernate Spatial   | Native support for geospatial data and complex queries        |
| **Security**      | Spring Security 6.2.2, JWT, @PreAuthorize     | Authentication via JWT and granular access control            |
| **Design**        | ControllerGeneric, Java Records, HATEOAS      | Abstraction of CRUD operations, immutable DTOs                |
| **Quality**       | JaCoCo 0.8.11, PIT 1.15.8, Surefire, Failsafe | Code coverage, mutation testing, unit and integration testing |
| **Testing**       | Testcontainers 1.20.4, Mockito, JUnit 5       | Testing with real PostgreSQL containers, advanced mocks       |
| **DevOps**        | Docker, Maven, CI/CD Ready                    | Full containerization and build/deploy automation             |
| **Documentation** | OpenAPI 3, Swagger UI, JavaDoc                | Interactive API documentation                                 |

### 🎨 **Padrões de Design Implementados**
- **Generic Controller Pattern**: Reusable CRUD operations
- **DTO Pattern com Records**: Immutable and type-safe data transfer
- **Repository Pattern**: Persistence abstraction
- **Service Layer Pattern**: Isolated business logic
- **Exception Handler**: Centralized error handling
- **Security Interceptors**: Multi-layered access control

## **Quality & Testing**

### 📈 **Current Coverage Metrics**

GENERAL COVERAGE: 5%
TOTAL NUMBER OF TESTS: 126

| Package                                 | Coverage |        |
|:----------------------------------------|:--------:|:------:|
| 📁 com.maps.persistence.model           |   100%   |   ✅   |
| 📁 com.maps.configuration.interceptor   |   40%    |   🟡   |
| 📁 com.maps.utils                       |   34%    |   🟡   |
| 📁 com.maps.persistence.payload.request |   18%    |   🟠   |
| 📁 com.maps.configuration               |   18%    |   🟠   |
| 📁 com.maps.controller                  |    6%    |   🔴   |
| 📁 com.maps.service                     |    2%    |   🔴   |
| 📁 demais pacotes                       |    0%    |   🔴   |

### **Types of Tests Implemented**
1. **Unit Tests**: Service layer, Utils, DTOs
2. **Integration Tests**: Testcontainers + PostgreSQL
3. **Controller Tests**: MockMVC with validation REST
4. **Validation Tests**: Bean Validation and custom annotations
5. **Security Tests**: JWT, authentication, authorization

## **Roadmap**

### 🚧 **in development**
- [x] ✅ **Testing Expansion**: 135 tests (+600%)
- [x] ✅ **Base Coverage**: 5% established with robust tools
- [ ] 🔄 **Repository Integration**: @DataJpaTest com Testcontainers

#### **Release v2.1.0 - Testing Excellence**
- [ ] **Target: 25% coverage** with full service layer
- [ ] **Mutation Testing**: PIT execution & analysis
- [ ] **Integration Tests**: End-to-end API scenarios
- [ ] **Performance Benchmarks**: JMH for critical operations

#### **Release v2.2.0 - Advanced Features**
- [ ] **GeoSpatial Queries**: Advanced geospatial queries
- [ ] **Real-time Updates**: WebSocket for real-time data
- [ ] **OAuth2 Integration**: Social login

### 🎯 **in concept**
- [ ] **Message Streaming**: Apache Kafka ou RabbitMQ
- [ ] **Caching Layer**: Redis para performance
- [ ] **CI/CD Pipeline**: GitHub Actions
- [ ] **Observability**: Prometheus + Grafana

## **Environment Configuration**

### 📋 **Prerequisites**

| Tool              | Minimum version |                                                          Download | Details                            |
|:------------------|:---------------:|------------------------------------------------------------------:|:-----------------------------------|
| **Java JDK**      |       17        |              [OpenJDK 17](https://adoptium.net/temurin/releases/) | Project's main runtime             |
| **PostgreSQL**    |      17.5       |                [PostgreSQL](https://www.postgresql.org/download/) | Primary SGBD with PostGIS          |
| **Maven**         |      3.8+       |             [Apache Maven](https://maven.apache.org/download.cgi) | Build tool e dependency management |
| **IntelliJ IDEA** |     2024.1+     |             [JetBrains](https://www.jetbrains.com/idea/download/) | IDE recommended                    |
| **Docker**        |      20.0+      | [Docker Desktop](https://www.docker.com/products/docker-desktop/) | Containerization                   |
| **DBeaver**       |      23.0+      |                        [DBeaver CE](https://dbeaver.io/download/) | Client PostgreSQL                  |

### 🐘 **PostgreSQL + PostGIS configuration**

```sql
-- create database
CREATE DATABASE maps;

-- connect to the database
\c maps;

-- enable PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- create schema
CREATE SCHEMA IF NOT EXISTS maps;

-- verify installation
SELECT version();
SELECT PostGIS_Version();
```

### 🔧 **Environment Variables**

create the `.env` file in the project root
```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=maps
DB_USERNAME=postgres
DB_PASSWORD=password
DB_SCHEMA=maps

# JWT Configuration
JWT_SECRET=your_super_secret_jwt_key_here_min_256_bits
JWT_EXPIRATION=86400000

# server Configuration
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/maps

# profile configuration (dev, test, prd)
SPRING_PROFILES_ACTIVE=dev
```

## **How to start**

### **Installation & Setup**

```bash
# clone the repository
git clone https://github.com/gadelhati/maps-back
cd maps-back

# configure the database

# configure environment variables
cp .env.example .env
# edite o .env

# install dependencies
mvn clean install

# execute tests
mvn test

# execute the application
mvn spring-boot:run
```

### **Development Commands**

```bash
# run in developer mode (CSRF disabled)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# run in production mode (CSRF enabled)  
mvn spring-boot:run -Dspring-boot.run.profiles=prd

# run tests with coverage report
mvn clean test jacoco:report

# perform mutation testing (test quality validation)
mvn org.pitest:pitest-maven:mutationCoverage

# production build (create WAR file)
mvn clean package

# run with Docker
docker build -t maps .
docker run -p 8080:8080 maps

# check application health
curl http://localhost:8080/maps/actuator/health
```

### 📊 **Monitoring & Reporting**

```bash
# view test coverage
start target/site/jacoco/index.html

# view PIT report (mutation testing)
start target/pit-reports/index.html

# application metrics (Actuator)
curl http://localhost:8080/maps/actuator/metrics

# stop the application on the specific port (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

## **Deploy**

### 🐳 **Docker**

```bash
# image build
docker build -t maps:latest .

# run with Docker Compose (includes PostgreSQL + PostGIS)
docker-compose up -d

# run only the application
docker run -d \
  --name maps \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_HOST=host.docker.internal \
  -e DB_PASSWORD=your_password \
  maps:latest
```

### ☁️ **Deploy em Cloud Provider**

#### 🏢 **AWS ECS / Azure Container Instances**
```yaml
# docker-compose.prod.yml exemplo
version: '3.8'
services:
  maps:
    image: maps:latest
    ports:
      - "80:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prd
      - DB_HOST=${DB_HOST}
      - DB_PASSWORD=${DB_PASSWORD}
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/maps/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
```

### 🏢 **Traditional Deployment (Tomcat Server)**

```bash
# generate WAR file
mvn clean package -Pprd

# deploy on linux server
service tomcat stop
rm /opt/tomcat/webapps/maps*.war
rm -Rfv /opt/tomcat/webapps/maps*
cp target/maps*.war /opt/tomcat/webapps/maps.war
chown tomcat:tomcat /opt/tomcat/webapps/maps.war
chmod 755 /opt/tomcat/webapps/maps.war
service tomcat start

# check deployment
curl http://your-server:8080/maps/actuator/health
```

## **API Documentation**

### 🌐 **Main Endpoints**

All resources follow the RESTful standard with complete CRUD operations
> **Base URL** [http://localhost:8080/maps](http://localhost:8080/maps`)

| Endpoint                                                  | Method                        | Description                                                         | Exemple              |
|-----------------------------------------------------------|:------------------------------|:--------------------------------------------------------------------|:---------------------|
| [CREATE](http://localhost:8080/maps/user)                 | `POST /{resource}`            | path to item creation                                               | `POST /user`         |
| [RETRIEVE](http://localhost:8080/maps/user/id)            | `GET /{resource}/{id}`        | path to search for item by id                                       | `GET /user/123`      |
| [RETRIEVE ALL](http://localhost:8080/maps/user/attribute) | `GET /{resource}/{attribute}` | path to search for item by attribute or all items without attribute | `GET /user/username` |
| [UPDATE](http://localhost:8080/maps/user/id)              | `PUT /{resource}/{id}`        | path to item update                                                 | `PUT /user/123`      |
| [DELETE](http://localhost:8080/maps/user/id)              | `DELETE /{resource}/{id}`     | path to item delete                                                 | `DELETE /user/123`   |

### 📚 **Swagger Documentation**
Access the interactive documentation when the application is running.
> **Swagger** [http://localhost:8080/maps/v1/swagger-ui/index.html](http://localhost:8080/maps/v1/swagger-ui/index.html)

### 🔗 **Links Úteis**
> **Home**: [http://localhost:8080/maps](http://localhost:8080/maps)

> **Health Check**: [http://localhost:8080/maps/actuator/health](http://localhost:8080/maps/actuator/health)

> **Metrics**: [http://localhost:8080/maps/actuator/metrics](http://localhost:8080/maps/actuator/metrics)

> **Info**: [http://localhost:8080/maps/actuator/info](http://localhost:8080/maps/actuator/info)

### 📍 **Available Resources**
| Resource      | Endpoint         | Description        | Exemple                    |
|:--------------|:-----------------|:-------------------|:---------------------------|
| **Users**     | `/maps/user`     | Gestão de usuários | `GET /maps/user/search`    |
| **Roles**     | `/maps/role`     | Controle de perfis | `POST /maps/role`          |
| **Charts**    | `/maps/chart`    | Cartas náuticas    | `GET /maps/chart/1`        |
| **Research**  | `/maps/research` | Dados de pesquisa  | `PUT /maps/research/1`     |
| **Countries** | `/maps/country`  | Países e regiões   | `GET /maps/country/search` |
| **Cities**    | `/maps/city`     | Cidades            | `DELETE /maps/city/1`      |

### 🔐 **Authentication & Authorization**

```http
# login and obtaining the JWT
POST /maps/auth/login
Content-Type: application/json
{
    "username": "12345678", 
    "password": "P@ssword123"
}

# response
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer ",
    "expiration": "2024-12-11T10:30:00Z"
}

# use the token in subsequent requests
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📊 **Pagination**

```http
# pagination
GET /maps/user/search?page=2&size=5

# ordering
GET /maps/user/search?sort=name,desc&sort=email,asc

# custom filters  
GET /maps/user/search?name=João&active=true&page=0&size=10
```

## **Contribution**

### 🤝 **How to contribute**
Follow the Commit Convention (see below)

1. **Fork** this repository
2. **Create** a branch to your feature or fix (`git checkout -b feature/feature-name`)
3. Implement your changes
4. Make sure all tests pass and code coverage has not decreased (`mvn test`)
5. **Commit** your changes (`git commit -m 'feat(amazing): add amazing feature'`)
6. **Push** to the develop branch (`git push -u origin feature/feature-name`)
7. Open a **Pull Request** (PR)

### 🏷️ **Commit Conventions**

This project adopts standardized commit conventions:

| Tipo       |                   Description                    | Exemplo                                                       |
|:-----------|:------------------------------------------------:|:--------------------------------------------------------------|
| `feat`     |        A new feature for the application         | `feat(geo): adds a search endpoint using coordinates.`        |
| `fix`      |                    Bug fixes                     | `fix(auth): fixes authentication error during login.`         |
| `docs`     |          Changes to documentation files          | `docs(readme): update the deployment section in the README.`  |
| `style`    |          Styling and formatting changes          | `style(city): adjust indentation in ControllerCity.`          |
| `refactor` | Code refactoring without changing functionality. | `refactor(generic): optimize ServiceGeneric to use Optional.` |
| `perf`     |           Performance-related changes            | `perf(layer): optimizes SQL query in the persistence layer.`  |
| `test`     |           Creating or modifying tests            | `test(city): adds unit testing to ServiceCity.`               |
| `chore`    |        Changes to config, build, CI files        | `chore(configuration): updates Spring Boot version to 3.5.4`  |

### 🛠️ **Setup for Development**

```bash
# clone repository
git clone https://github.com/gadelhati/maps-back
cd maps-back
# add remote repository
git remote add upstream https://github.com/gadelhati/maps-back
# install dependencies
mvn clean install
# run tests
mvn test
# integrity check
mvn validate
# clean, test and generates the JaCoCo test coverage report
mvn clean test jacoco:report
# target: maintain coverage above 5%
```

### 📏 **Code Standards**

- **Test Coverage**: Maintain or improve current coverage (5%+)
- **Architecture**: Follow established standards (Service/Controller/Repository)
- **Security**: Implement security tests for new endpoints
- **Clean Code**: Follow SOLID and DRY principles

### 🐛 **Reporting Bugs**

When reporting bugs, please include:
- **Environment**: OS, Java version
- **Steps to reproduce**: Detailed steps
- **Expected vs Actual**: Expected vs. actual behavior
- **Logs**: Stack trace or relevant logs

### 💡 **Suggesting Features**

For feature suggestions:
- **Use Case**: Describe the use case
- **Business Value**: What value does it add to the project
- **Acceptance Criteria**: specific conditions to be met

## **Useful Git Commands**  

```bash
# create a new branch and switch to this new branch
git checkout -b feature/new-feature
# remove a local branch
git branch -d feature/feature-removed
# removes a branch from the remote repository
git push --delete origin feature/feature-removed

# download the updates, but don't change your branch
git fetch remote-branch
# download the updates, and rewrite history
git rebase remote-branch/main
# download the updates, end edit from the penultimate commit
git rebase -i HEAD~3
# push your local branch to the remote
git push origin feature/your-feature

# create a tag
git tag -a v1.4.0 -m "Release version 1.4.0"
# send tag to remote
git push origin v1.4.0

# displays the commit history
git log --oneline --graph --decorate
# displays the repository status
git status --short
# differences between staging and the last commit
git diff --staged
```

## **Developers**

### **🤝 Contributors**
> **[Gadelha TI](https://github.com/gadelhati)** - *Architect & Lead Developer*

### **📞 Contact**
> **Email:** gadelhati@gmail.com

## **License**

This project is licensed under the **MIT License** - see the [MIT LICENSE]( https://choosealicense.com/licenses/mit/) file for details.

```text
MIT License

Copyright (c) 2024 Gadelha TI

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
---

<div align="center">

**⭐ Did you like the project? Leave a star! ⭐**

[![GitHub stars](https://img.shields.io/github/stars/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)
[![GitHub forks](https://img.shields.io/github/forks/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)

**Made by [Gadelha TI](https://github.com/gadelhati)**

</div>
