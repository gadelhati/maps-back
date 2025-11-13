** 🏗️ Package Structure:**
```
com.maps/
├── configuration/        # Modular and specialized configurations
│   ├── security/         # Isolated security (JWT, Filters, CSP)  
│   └── interceptor/      # Rate limiting, Audit
├── controller/           # Endpoints REST + Web (Thymeleaf)
├── persistence/          # Data layer
│   ├── model/            # JPA entities with auditing
│   ├── repository/       # Spring Data JPA
│   └── payload/          # DTOs request/response
├── service/              # Business logic
├── exception/            # Global error handling + custom annotations
└── utils/                # Helpers
```
## 🏗️ **ARQUITETURA TÉCNICA ATUAL**

### **Stack Principal:**
- **Spring Boot 3.5.4** (Java 17)
- **PostgreSQL + PostGIS 2025.1.1** (Geoespacial)
- **Maven** com dependency management otimizado
- **Caffeine 3.2.2** (Cache implementation)
- **JWT + TOTP** (Authentication)
- **JTS 1.20.0** (Spatial operations)

## 🎯 **ROADMAP**

** 🔐 Recursos de Segurança:**
- ✅ **JWT Authentication** com refresh tokens e HMAC-SHA512
- ✅ **TOTP Two-Factor Authentication** (Google Authenticator)
- ✅ **Rate Limiting** com Bucket4j (50-200 req/min configurável)
- ✅ **Google reCAPTCHA v3** integration para proteção anti-bot
- ✅ **CSRF Protection** configurável por ambiente
- ✅ **Content Security Policy** diferenciado (API vs Web)
- ✅ **Password Security** com BCrypt e policies robustas
- ✅ **Method-level Security** com `@PreAuthorize`
- 🔄 **OAuth2 integration** - Spring Authorization Server

** 🌍 Recursos Geoespaciais:**
- ✅ **PostGIS 2025.1.1** com JTS Topology Suite 1.20.0
- ✅ **Entidades geoespaciais**: Port, NavigationAid, MarsdenSquare, Boundary
- ✅ **Índices GIST** otimizados para queries espaciais
- ✅ **Jackson JTS** para serialização geoespacial
- ✅ **SRID 4326** (WGS84) padronizado

** ⚡	Otimizações de Performance:**
- ✅ **Caffeine Cache** para user roles e permissions
- ✅ **HikariCP Connection Pool** otimizado (20 max, 5 min idle)
- ✅ **Database Indexes** via JPA annotations (11 entidades)
- ✅ **Lazy Loading** em relationships JPA
- ✅ **Distributed Tracing** configurado (dev: 100%, prd: 10%)
- 🔄 **Pipeline automatizado** com GitHub Actions
- 🔄 **Multi-stage Dockerfile**

** 🎯 Validation Framework Customizado:**
- ✅ **15+ Custom Annotations**: @HasDigit, @HasLength, @UniqueEmail, @ExceptionValues
- ✅ **Global Exception Handler** com @ControllerAdvice
- ✅ **API Error Standardization** com ApiError e ValidationError
- ✅ **Internationalization** com ValidationMessages_pt_BR/en_US

**Recursos de Monitoring:**
- ✅ **Spring Boot Actuator** com endpoints health/info/metrics
- ✅ **Distributed Tracing** configuração diferenciada
- ✅ **HikariCP Metrics** habilitado
- ✅ **Environment-specific** configurations (dev/test/prd)
- 🔄 **Métricas customizadas com Micrometer** para dashboards (login attempts, auth duration, active users)
- 🔄 **Health checks específicos** do domínio (database, cache, application info)

** Email Features:**
- ✅ **SMTP Gmail Integration** configurado
- ✅ **HTML Templates** com attachments (QR codes TOTP)
- ✅ **Environment Variables** management com DotenvConfig
- ✅ **Error Handling** robusto com logging
- ✅ **Email Validation** nos DTOs com @Email annotation


- ✅ **8 Test Classes** implementadas cobrindo services, controllers e repositories
- ✅ **Unit Tests** com Mockito e JUnit 5
- ✅ **@WebMvcTest** para controllers  
- ✅ **@DataJpaTest** para repositories
- ✅ **JaCoCo Plugin** configurado
- 🔄 **Testes robustos** com Testcontainers
- 🔄 **Cobertura de Testes atual ~60%** - Aumentar para 85%
- 🔄 **Cache testing** - Testar hit/miss rates do Caffeine
- 🔄 **Mutation Testing** com PIT plugin

- ✅ **Logging**: @Slf4j implementado
- 🔄 **Configurar SonarQube** local code quality gates
- 🔄 **Dockerfile optimization**

## 📋 **COMANDOS PARA PRÓXIMA EXECUÇÃO**

### Phase 1: Critical Expansion (Target: 25% overall)
```
🚧 SERVICE LAYER
   ├── ServiceAuth (293 instructions) - Priority #1
   ├── ServiceUser (757 instructions) - Priority #2  
   ├── ServiceGeneric (314 instructions) - Priority #3
   └── Integration with repository mocking

🚧 PERSISTENCE LAYER  
   ├── Repository tests (@DataJpaTest)
   ├── MapStruct mappers validation
   ├── Entity relationship testing
   └── Database constraint validation

🚧 EXCEPTION HANDLING
   ├── GlobalExceptionHandler (120 instructions)
   ├── Custom exception tests
   ├── API error response validation
   └── Exception flow testing
```

```bash
# 1. Executar todos os 135 testes
mvn clean test jacoco:report

# 2. Visualizar relatório de cobertura
start target/site/jacoco/index.html

# 3. Próxima fase: Mutation testing completo
mvn org.pitest:pitest-maven:mutationCoverage

# 4. Continuous integration validation
mvn verify -Pcoverage-strict
```

### Phase 2: Advanced Quality (Target: 50% overall)
```
⚡ INTEGRATION TESTS
   ├── Testcontainers + PostgreSQL
   ├── End-to-end scenarios
   ├── API integration tests
   └── Security integration

🔬 MUTATION TESTING
   ├── PIT execution & analysis
   ├── Test quality validation
   ├── Dead code detection
   └── Test effectiveness measurement

📈 PERFORMANCE
   ├── Load testing scenarios
   ├── Database performance tests
   ├── API response time validation
   └── Memory usage optimization
```