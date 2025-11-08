package com.maps.configuration.security;

import com.maps.configuration.security.filter.FilterJWT;
import com.maps.configuration.security.filter.FilterRateLimiting;
import com.maps.service.ServiceCustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    public final ConfigurationJWT configurationJwt;
    public final FilterRateLimiting filterRateLimiting;
    public final ServiceCustomUserDetails serviceCustomUserDetails;
    
    @Autowired
    private CorsConfigurationSource corsConfigurationSource;
    
    @Value("${security.csrf.enabled:true}")
    private boolean csrfEnabled;
    
    @Value("${security.csrf.cookie.http-only:false}")
    private boolean csrfCookieHttpOnly;
    
    @Value("${security.csrf.cookie.secure:false}")
    private boolean csrfCookieSecure;
    
    @Value("${security.csrf.cookie.same-site:Lax}")
    private String csrfCookieSameSite;
    
    @Value("${security.csrf.ignore.patterns:/api/**,/auth/**}")
    private String[] csrfIgnorePatterns;

    /**
     * Security configuration for REST API endpoints
     * Higher priority (Order 1) - processed first
     */
    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .securityMatcher("/auth/**", "/api/**", "/v3/api-docs/**", "/swagger-ui/**", "/actuator/**", 
                        "/upload/**", "/chart/**", "/chartArea/**", "/city/**", "/country/**", 
                        "/gaugeStation/**", "/internationalChart/**", "/maritimeArea/**", "/privilege/**", 
                        "/research/**", "/researcher/**", "/role/**", "/state/**", "/user/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for REST APIs
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'none'; frame-ancestors 'none'; connect-src 'self'")
                        )
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/resetPassword").permitAll()
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        // Endpoints que requerem autenticação JWT
                        .requestMatchers("/upload/**", "/chart/**", "/chartArea/**", "/city/**", "/country/**", 
                                "/gaugeStation/**", "/internationalChart/**", "/maritimeArea/**", "/privilege/**", 
                                "/research/**", "/researcher/**", "/role/**", "/state/**", "/user/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filterRateLimiting, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Security configuration for web pages (Thymeleaf)
     * Lower priority (Order 2) - processed after API config
     */
    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity httpSecurity) throws Exception {
        if (csrfEnabled) {
            CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
            tokenRepository.setCookieHttpOnly(csrfCookieHttpOnly);
            tokenRepository.setSecure(csrfCookieSecure);
            // Note: SameSite configuration is handled at the server level or via cookie customizer
            
            httpSecurity.csrf(csrf -> csrf
                    .csrfTokenRepository(tokenRepository)
                    .ignoringRequestMatchers(csrfIgnorePatterns)
            );
        } else {
            httpSecurity.csrf(AbstractHttpConfigurer::disable);
        }
        
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .exceptionHandling(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; " +
                                        "script-src 'self' 'nonce-{nonce}'; " +
                                        "style-src 'self' 'nonce-{nonce}'; " +
                                        "img-src 'self' data: https:; " +
                                        "font-src 'self'; " +
                                        "connect-src 'self'; " +
                                        "frame-ancestors 'none'; " +
                                        "form-action 'self'")
                        )
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/image/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register", "/login", "/resetPassword", "/resetTotp", "/confirm", "/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup", "/signin", "/requiredPassword", "/requiredTotp").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/signup").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/resetPassword", "/user/resetTotp").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filterRateLimiting, UsernamePasswordAuthenticationFilter.class)
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .build();
    }
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public FilterJWT jwtAuthenticationFilter() {
        return new FilterJWT(configurationJwt, serviceCustomUserDetails);
    }
}
