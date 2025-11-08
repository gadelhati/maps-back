package com.maps.configuration.security;

import com.maps.configuration.security.filter.FilterJWT;
import com.maps.configuration.security.filter.FilterRateLimiting;
import com.maps.service.ServiceCustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(Customizer.withDefaults())
                .sessionManagement((session) -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny)
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline';")
                        )
                        .referrerPolicy(referrer -> referrer
                                .policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                )
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/image/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register", "/login", "/resetPassword", "/resetTotp", "/confirm", "/list").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**", "/signup", "/signin", "/requiredPassword", "/requiredTotp").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/signup").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/resetPassword", "/user/resetTotp").permitAll()
                        .requestMatchers("/upload/**", "/chart/**", "/chartArea/**", "/city/**", "/gaugeStation/**", "/internationalChart/**", "/maritimeArea/**", "/privilege/**", "/research/**", "/researcher/**", "/role/**", "/state/**", "/user/**").permitAll()
                        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filterRateLimiting, UsernamePasswordAuthenticationFilter.class)
                .formLogin((login) -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/error")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
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
