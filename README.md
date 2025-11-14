# 🗺️ Maps Backend API

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

**Maps Backend API** is a robust and modern platform for consolidating georeferenced data, designed for spatial analysis, real-time monitoring, and interdisciplinary applications.

## **Sumary**
- [Architecture & Technologies](#-architecture--technologies)
- [Quality & Testing](#-quality--testing)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Como Usar](#-como-usar)
- [Deploy](#-deploy)
- [API Documentation](#-api-documentation)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

### 🌟 **Main Features:**
- ✅ **API RESTful completa** com operações CRUD
- ✅ **Dados geoespaciais** com PostgreSQL + PostGIS  
- ✅ **Segurança JWT** com controle granular de acesso
- ✅ **Arquitetura limpa** com padrões de design modernos
- ✅ **Alta cobertura de testes** (5% e crescendo)
- ✅ **Java Records** para DTOs modernos e imutáveis
- ✅ **Documentação automática** com OpenAPI/Swagger

## ️ **Architecture & Technologies**

### **Detailed Tech Stack**

| Categoria           | Tecnologia / Padrão                              | Detalhes                                                                                    |
|:--------------------|:-------------------------------------------------|:--------------------------------------------------------------------------------------------|
| **Backend**         | Java 17, Spring Boot 3.5.4                      | Framework moderno e robusto para APIs RESTful com suporte a Records                        |
| **Persistência**    | PostgreSQL 17.5, PostGIS, Hibernate Spatial     | Suporte nativo a dados geoespaciais e consultas complexas                                  |
| **Segurança**       | Spring Security 6.2.2, JWT, @PreAuthorize       | Autenticação via JWT e controle de acesso granular em nível de método                     |
| **Design**          | ControllerGeneric, Java Records, HATEOAS         | Abstração de operações CRUD, DTOs imutáveis e enriquecimento de respostas                 |
| **Qualidade**       | JaCoCo 0.8.11, PIT 1.15.8, Surefire, Failsafe  | Cobertura de código, mutation testing, testes unitários e de integração                   |
| **Testing**         | Testcontainers 1.20.4, Mockito, JUnit 5         | Testes com containers reais PostgreSQL, mocks avançados                                   |
| **DevOps**          | Docker, Maven, CI/CD Ready                       | Containerização completa e automação de build/deploy                                      |
| **Documentation**   | OpenAPI 3, Swagger UI, JavaDoc                   | Documentação interativa e completa da API                                                 |

### 🎨 **Padrões de Design Implementados**
- **Generic Controller Pattern**: Operações CRUD reutilizáveis
- **DTO Pattern com Records**: Transferência de dados imutável e type-safe
- **Repository Pattern**: Abstração de persistência
- **Service Layer Pattern**: Lógica de negócio isolada
- **Exception Handler**: Tratamento centralizado de erros
- **Security Interceptors**: Controle de acesso em múltiplas camadas

## **Quality & Testing**

### **Current Coverage Metrics**
```
GENERAL COVERAGE: 5%
┌─────────────────────────────────────────────────────┬──────────┐
│ Package                                             │ Coverage │
├─────────────────────────────────────────────────────┼──────────┤
│ 📁 com.maps.persistence.model                       │   100%   │ ✅
│ 📁 com.maps.configuration.interceptor               │    40%   │ 🟡  
│ 📁 com.maps.utils                                   │    34%   │ 🟡
│ 📁 com.maps.persistence.payload.request             │    18%   │ 🟠
│ 📁 com.maps.configuration                           │    18%   │ 🟠
│ 📁 com.maps.controller                              │     6%   │ 🔴
│ 📁 com.maps.service                                 │     2%   │ 🔴
│ 📁 demais pacotes                                   │     0%   │ 🔴
└─────────────────────────────────────────────────────┴──────────┘
TOTAL NUMBER OF TESTS: 126
```

### **Configured Quality Tools**
- **JaCoCo**: Cobertura de código com target de 85%
- **PIT Mutation Testing**: Validação da qualidade dos testes (80% target)
- **Testcontainers**: Testes de integração com PostgreSQL real
- **Static Analysis**: Checkstyle, SpotBugs integration ready
- **Performance Testing**: JMH benchmarks configurados

### **Types of Tests Implemented**
1. **Unit Tests**: Service layer, Utils, DTOs
2. **Integration Tests**: Testcontainers + PostgreSQL
3. **Testes de Controller**: MockMVC com validação REST
4. **Testes de Validação**: Bean Validation and custom annotations
5. **Testes de Segurança**: JWT, authentication, authorization

## **Roadmap**

### 🚧 **in development**
- [x] ✅ **Conversão para Records**: 40 DTOs modernizados
- [x] ✅ **Expansão de Testes**: 135 testes (crescimento +600%)
- [x] ✅ **Cobertura Base**: 5% estabelecida com ferramentas robustas
- [ ] 🔄 **Service Layer Testing**: ServiceAuth, ServiceUser completos  
- [ ] 🔄 **Repository Integration**: @DataJpaTest com Testcontainers
- [ ] 🔄 **Exception Handling**: GlobalExceptionHandler coverage

### **Next Releases**

#### **Release v2.1.0 - Testing Excellence** 
- [ ] 🎯 **Target: 25% coverage** com service layer completo
- [ ] 📊 **Mutation Testing**: PIT execution & analysis  
- [ ] 🔗 **Integration Tests**: End-to-end API scenarios
- [ ] 📈 **Performance Benchmarks**: JMH para operações críticas

#### **Release v2.2.0 - Advanced Features**
- [ ] 🌍 **GeoSpatial Queries**: Consultas geométricas avançadas
- [ ] 📡 **Real-time Updates**: WebSocket para dados em tempo real
- [ ] 🔐 **OAuth2 Integration**: Login social e federado
- [ ] 📱 **Mobile API Optimization**: Endpoints otimizados para mobile

### 💡 **Conceitos Futuros (Backlog)**
- [ ] 🌐 **Microservices Architecture**: Decomposição em serviços
- [ ] 📨 **Message Streaming**: Apache Kafka ou RabbitMQ
- [ ] ⚡ **Caching Layer**: Redis para performance
- [ ] 🚀 **CI/CD Pipeline**: GitHub Actions completo
- [ ] 📊 **Observability**: Prometheus + Grafana
- [ ] 🔄 **Event Sourcing**: Para auditoria avançada

## ⚙️ **Configuração do Ambiente**

### 📋 **Pré-requisitos**

| Ferramenta    | Versão Mínima |              Download | Detalhes |
|:--------------|:-------------:|:--------------------:|:---------|
| **Java JDK**  |      17       | [OpenJDK 17](https://adoptium.net/temurin/releases/) | Runtime principal do projeto |
| **PostgreSQL**|     17.5      | [PostgreSQL](https://www.postgresql.org/download/) | SGBD principal com PostGIS |
| **Maven**     |     3.8+      | [Apache Maven](https://maven.apache.org/download.cgi) | Build tool e dependency management |
| **IntelliJ IDEA** |  2024.1+  | [JetBrains](https://www.jetbrains.com/idea/download/) | IDE recomendada (opcional) |
| **Docker**    |     20.0+     | [Docker Desktop](https://www.docker.com/products/docker-desktop/) | Para containerização (opcional) |
| **DBeaver**   |     23.0+     | [DBeaver CE](https://dbeaver.io/download/) | Cliente PostgreSQL recomendado |

### 🐘 **Configuração PostgreSQL + PostGIS**

```sql
-- 1. Criar database
CREATE DATABASE maps;

-- 2. Conectar no database maps
\c maps;

-- 3. Habilitar PostGIS
CREATE EXTENSION IF NOT EXISTS postgis;

-- 4. Criar schema
CREATE SCHEMA IF NOT EXISTS maps;

-- 5. Verificar instalação
SELECT version();
SELECT PostGIS_Version();
```

### 🔧 **Variáveis de Ambiente**

Crie um arquivo `.env` na raiz do projeto:

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

# Server Configuration
SERVER_PORT=8080
SERVER_CONTEXT_PATH=/maps

# Profile Configuration (dev, test, prd)
SPRING_PROFILES_ACTIVE=dev
```

## 🚀 **Como Usar**

### 📥 **Instalação & Setup**

```bash
# 1. Clone o repositório
git clone https://github.com/gadelhati/maps-back
cd maps-back

# 2. Configure o banco de dados (PostgreSQL deve estar rodando)
# Certifique-se que PostgreSQL + PostGIS estão configurados

# 3. Configure as variáveis de ambiente
cp .env.example .env
# Edite o .env com suas configurações

# 4. Instale as dependências
mvn clean install

# 5. Execute os testes (opcional mas recomendado)
mvn test

# 6. Execute a aplicação
mvn spring-boot:run
```

### 🔄 **Comandos de Desenvolvimento**

```bash
# Executar em modo de desenvolvimento (CSRF desabilitado)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Executar em modo de produção (CSRF habilitado)  
mvn spring-boot:run -Dspring-boot.run.profiles=prd

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Executar mutation testing (validação da qualidade dos testes)
mvn org.pitest:pitest-maven:mutationCoverage

# Build para produção (criar arquivo WAR)
mvn clean package

# Executar com Docker
docker build -t maps-backend .
docker run -p 8080:8080 maps-backend

# Verificar saúde da aplicação
curl http://localhost:8080/maps/actuator/health
```

### 📊 **Monitoramento & Relatórios**

```bash
# Visualizar cobertura de testes
start target/site/jacoco/index.html

# Visualizar relatório do PIT (mutation testing)
start target/pit-reports/index.html

# Métricas da aplicação (Actuator)
curl http://localhost:8080/maps/actuator/metrics

# Parar aplicação na porta específica (Windows)
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

## 🚀 **Deploy**

### 🐳 **Docker (Recomendado)**

```bash
# Build da imagem
docker build -t maps-backend:latest .

# Executar com Docker Compose (inclui PostgreSQL + PostGIS)
docker-compose up -d

# Executar apenas a aplicação
docker run -d \
  --name maps-backend \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_HOST=host.docker.internal \
  -e DB_PASSWORD=your_password \
  maps-backend:latest
```

### ☁️ **Deploy em Cloud Provider**

#### **Heroku**
```bash
# 1. Login no Heroku
heroku login

# 2. Criar aplicação
heroku create maps-backend-api

# 3. Configurar PostgreSQL addon
heroku addons:create heroku-postgresql:mini

# 4. Configurar variáveis de ambiente
heroku config:set SPRING_PROFILES_ACTIVE=prd
heroku config:set JWT_SECRET=your_jwt_secret

# 5. Deploy
git push heroku main
```

#### **AWS ECS / Azure Container Instances**
```yaml
# docker-compose.prod.yml exemplo
version: '3.8'
services:
  maps-backend:
    image: maps-backend:latest
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

### 🏢 **Deploy Tradicional (Tomcat Server)**

```bash
# 1. Gerar arquivo WAR
mvn clean package -Pprd

# 2. Deploy no servidor Linux
service tomcat stop
rm /opt/tomcat/webapps/maps*.war
rm -Rfv /opt/tomcat/webapps/maps*
cp target/maps-*.war /opt/tomcat/webapps/maps.war
chown tomcat:tomcat /opt/tomcat/webapps/maps.war
chmod 755 /opt/tomcat/webapps/maps.war
service tomcat start

# 3. Verificar deployment
curl http://your-server:8080/maps/actuator/health
```

### 🔧 **Configuração de Produção**

```properties
# application-prd.properties (exemplo)
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
logging.level.org.hibernate.SQL=WARN
logging.level.com.maps=INFO

# Security headers
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true

# Actuator security
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=when_authorized
```

## 📖 **API Documentation**

### 🌐 **Endpoints Principais**

#### **Base URL:** `http://localhost:8080/maps`

### 📚 **Swagger Documentation**
Acesse a documentação interativa quando a aplicação estiver rodando:
> **🔗 [http://localhost:8080/maps/v1/swagger-ui/index.html](http://localhost:8080/maps/v1/swagger-ui/index.html)**

### 🔗 **Links Úteis**
- **🏠 Home**: [http://localhost:8080/maps](http://localhost:8080/maps)
- **❤️ Health Check**: [http://localhost:8080/maps/actuator/health](http://localhost:8080/maps/actuator/health)
- **📊 Metrics**: [http://localhost:8080/maps/actuator/metrics](http://localhost:8080/maps/actuator/metrics)
- **📋 Info**: [http://localhost:8080/maps/actuator/info](http://localhost:8080/maps/actuator/info)

### 🛣️ **Endpoints CRUD Padrão**

Todos os recursos seguem o padrão RESTful com operações CRUD completas:

```http
# 📝 Criar recurso
POST   /maps/{resource}

# 📖 Buscar por ID  
GET    /maps/{resource}/{id}

# 🔍 Buscar com filtros/paginação
GET    /maps/{resource}/search?page=0&size=10&sort=name,desc

# ✏️ Atualizar recurso
PUT    /maps/{resource}/{id}

# 🗑️ Deletar por ID
DELETE /maps/{resource}/{id}

# 🗑️ Deletar todos (admin only)
DELETE /maps/{resource}
```

### 📍 **Recursos Disponíveis**
| Recurso | Endpoint | Description | Exemplo |
|:--------|:---------|:----------|:--------|
| **Users** | `/maps/user` | Gestão de usuários | `GET /maps/user/search` |
| **Roles** | `/maps/role` | Controle de perfis | `POST /maps/role` |
| **Charts** | `/maps/chart` | Cartas náuticas | `GET /maps/chart/1` |
| **Research** | `/maps/research` | Dados de pesquisa | `PUT /maps/research/1` |
| **Countries** | `/maps/country` | Países e regiões | `GET /maps/country/search` |
| **Cities** | `/maps/city` | Cidades | `DELETE /maps/city/1` |

### 🔐 **Autenticação & Autorização**

```http
# Login e obtenção do JWT
POST /maps/auth/login
Content-Type: application/json
{
  "email": "user@example.com", 
  "password": "password123"
}

# Resposta
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "expiration": "2024-12-11T10:30:00Z"
}

# Usar o token em requests subsequentes
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### 📊 **Exemplos de Paginação**

```http
# Paginação básica
GET /maps/user/search?page=2&size=5

# Ordenação
GET /maps/user/search?sort=name,desc&sort=email,asc

# Filtros customizados  
GET /maps/user/search?name=João&active=true&page=0&size=10
```

## 🤝 **Contribuição**

### 📋 **Como Contribuir**

1. **🍴 Fork** este repositório
2. **🌿 Crie** uma branch para sua feature (`git checkout -b feature/amazing-feature`)
3. **💾 Commit** suas mudanças (`git commit -m 'feat: add amazing feature'`)
4. **📤 Push** para a branch (`git push origin feature/amazing-feature`)
5. **🔄 Abra** um Pull Request

### 🏷️ **Convenções de Commit**

Este projeto adota convenções de commit padronizadas para melhor rastreabilidade:

| Tipo       |                 Description                           | Exemplo                                             |
|:-----------|:---------------------------------------------------:|:----------------------------------------------------|
| `feat`     |            Um novo recurso para a aplicação         | `feat: adiciona endpoint de busca por coordenadas`  |
| `fix`      |                    Correções de bug                 | `fix: corrige erro de autenticação no login`        |
| `docs`     |         Alterações em arquivos de documentação      | `docs: atualiza seção de deploy no README`          |
| `style`    |       Alterações de estilização, formatação         | `style: ajusta indentação em ControllerCity`        |
| `refactor` |  Refatoração de código sem mudança de funcionalidade| `refactor: otimiza ServiceGeneric para usar Optional`|
| `perf`     |         Alterações relacionadas à performance       | `perf: otimiza consulta SQL na camada de persistência`|
| `test`     |            Criação ou modificação de testes         | `test: adiciona teste unitário para ServiceCity`    |
| `chore`    | Alterações em arquivos de config, build, CI         | `chore: atualiza versão do Spring Boot para 3.5.4`  |

### 🛠️ **Setup para Desenvolvimento**

```bash
# 1. Clone seu fork
git clone https://github.com/SEU_USERNAME/maps-back
cd maps-back

# 2. Configure o remote upstream
git remote add upstream https://github.com/gadelhati/maps-back

# 3. Instale dependências e execute testes
mvn clean install
mvn test

# 4. Configure pre-commit hooks (recomendado)
mvn validate

# 5. Verifique qualidade do código antes do commit
mvn clean test jacoco:report
# Target: Manter cobertura acima de 5%
```

### 📏 **Padrões de Código**

- **🎯 Cobertura de Testes**: Mantenha ou melhore a cobertura atual (5%+)
- **📝 Documentação**: Documente métodos públicos com JavaDoc
- **🏗️ Arquitetura**: Siga os padrões estabelecidos (Service/Controller/Repository)
- **🔒 Segurança**: Implemente testes de segurança para novos endpoints
- **♻️ Clean Code**: Siga princípios SOLID e DRY

### 🎯 **Áreas Prioritárias para Contribuição**

#### **🔥 Alta Prioridade**
- **Service Layer Testing**: Aumentar cobertura de ServiceAuth, ServiceUser
- **Repository Tests**: Implementar testes @DataJpaTest
- **Integration Tests**: Cenários end-to-end com Testcontainers

#### **🟡 Média Prioridade**  
- **Performance Optimization**: Benchmarks JMH
- **Exception Handling**: Testes do GlobalExceptionHandler
- **API Documentation**: Melhorar documentação OpenAPI

#### **🟢 Baixa Prioridade**
- **Frontend Integration**: Cliente React/Angular
- **Monitoring**: Métricas Prometheus/Grafana
- **CI/CD**: GitHub Actions pipeline

### 🐛 **Reportando Bugs**

Ao reportar bugs, inclua:
- **🖥️ Ambiente**: OS, Java version, Spring Boot version
- **📊 Steps to reproduce**: Passos detalhados
- **🎯 Expected vs Actual**: Comportamento esperado vs real
- **📋 Logs**: Stack trace ou logs relevantes
- **🧪 Test**: Teste unitário reproduzindo o bug (se possível)

### 💡 **Sugerindo Features**

Para sugestões de features:
- **🎯 Use Case**: Descreva o caso de uso
- **💼 Business Value**: Qual valor agrega ao projeto
- **🏗️ Implementation**: Sugestão de implementação (opcional)
- **🧪 Acceptance Criteria**: Critérios de aceitação

## 📊 **Comandos Git Úteis**

```bash
# Gerenciamento de branches
git checkout -b feature/nova-feature
git branch -d feature/feature-removida
git push --delete origin feature/feature-removida

# Sincronização com upstream
git fetch upstream
git rebase upstream/main

# Preparação para PR
git rebase -i HEAD~3  # Squash commits se necessário
git push -f origin feature/sua-feature

# Tags e releases
git tag -a v1.4.0 -m "Release version 1.4.0"
git push origin v1.4.0

# Úteis para desenvolvimento
git log --oneline --graph --decorate
git status --short
git diff --staged
```

## 👨‍💻 **Desenvolvedores**

### **🏆 Core Team**
- **[Gadelha TI](https://github.com/gadelhati)** - *Architect & Lead Developer*

### **🤝 Contribuidores**
Agradecemos a todos que contribuíram para este projeto!

<!-- [![Contributors](https://contrib.rocks/image?repo=gadelhati/maps-back)](https://github.com/gadelhati/maps-back/graphs/contributors) -->

### **📞 Contato**
- **💌 Email**: gadelhati@gmail.com
- **💼 LinkedIn**: [linkedin.com/in/gadelhati](https://linkedin.com/in/gadelhati)
- **🐙 GitHub**: [@gadelhati](https://github.com/gadelhati)

## **Licence**

This project is licensed under the **MIT License** - see the [MIT LICENCE](https://choosealicense.com/licenses/mit/) file for details.

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

## 🎉 **Agradecimentos**

- **☕ Spring Community** - Pelo framework excepcional
- **🐘 PostgreSQL Team** - Pelo SGBD robusto e confiável  
- **🗺️ PostGIS Community** - Por tornar dados geoespaciais acessíveis
- **🧪 Testing Libraries** - JUnit, Mockito, Testcontainers teams
- **🛠️ Open Source Community** - Por todas as bibliotecas utilizadas

---

<div align="center">

**⭐ Did you like the project? Leave a star! ⭐**

[![GitHub stars](https://img.shields.io/github/stars/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)
[![GitHub forks](https://img.shields.io/github/forks/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back/fork)
[![GitHub watchers](https://img.shields.io/github/watchers/gadelhati/maps-back?style=social)](https://github.com/gadelhati/maps-back)

**Made by [Gadelha TI](https://github.com/gadelhati)**

</div>
