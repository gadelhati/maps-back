package com.maps.integration.config;

import com.maps.persistence.model.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Configuração de testes de integração
 * Fornece beans e dados de teste para testes de integração
 * 
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 */
@TestConfiguration
@Profile("integration-test")
public class IntegrationTestConfiguration {

    @Bean
    @Primary
    public PasswordEncoder testPasswordEncoder() {
        return new BCryptPasswordEncoder(4); // Força reduzida para testes mais rápidos
    }

    /**
     * Cria dados de teste para privilégios
     */
    public static Privilege createTestPrivilege(String name) {
        Privilege privilege = new Privilege();
        privilege.setName(name);
        return privilege;
    }

    /**
     * Cria dados de teste para roles
     */
    public static Role createTestRole(String name, Set<Privilege> privileges) {
        Role role = new Role();
        role.setName(name);
        role.setPrivilege(privileges != null ? privileges : new HashSet<>());
        return role;
    }

    /**
     * Cria dados de teste para usuários
     */
    public static User createTestUser(String username, String email, Set<Role> roles) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("$2a$04$rWKOmVJrJBX9tZ3pJuHaouCE2Q7z6xz5t9m5XYqD2f4pW6c8Fz8qC"); // "testpass123"
        user.setAttempt(0);
        user.setActive(true);
        user.setRole(roles != null ? roles : new HashSet<>());
        return user;
    }

    /**
     * Cria dados de teste para países
     */
    public static Country createTestCountry(String name) {
        Country country = new Country();
        country.setName(name);
        return country;
    }

    /**
     * Cria dados de teste para estados
     */
    public static State createTestState(String name, Country country) {
        State state = new State();
        state.setName(name);
        state.setCountry(country);
        return state;
    }

    /**
     * Cria dados de teste para cidades
     */
    public static City createTestCity(String name, State state) {
        City city = new City();
        city.setName(name);
        city.setState(state);
        return city;
    }

    /**
     * Cria dados de teste para tokens
     */
    public static Token createTestToken(String refreshToken) {
        Token tokenEntity = new Token();
        tokenEntity.setRefreshToken(UUID.fromString(refreshToken));
        tokenEntity.setActive(true);
        return tokenEntity;
    }
}