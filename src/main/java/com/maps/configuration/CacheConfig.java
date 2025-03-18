package com.maps.configuration;

import com.maps.persistence.model.Privilege;
import com.maps.persistence.model.Role;
import com.maps.persistence.repository.RepositoryUser;
import com.maps.utils.CacheConstants;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {
    private final RepositoryUser repositoryUser;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // Cache para roles
        CaffeineCache rolesCache = new CaffeineCache(CacheConstants.ROLES_CACHE,
                Caffeine.newBuilder()
                        .initialCapacity(CacheConstants.INITIAL_CAPACITY)
                        .maximumSize(CacheConstants.MAXIMUM_SIZE)
                        .expireAfterWrite(CacheConstants.CACHE_TTL)
                        .build(key -> loadUserRoles(key.toString()))); // Função de carregamento

        // Cache para permissions
        CaffeineCache permissionsCache = new CaffeineCache(CacheConstants.PERMISSIONS_CACHE,
                Caffeine.newBuilder()
                        .initialCapacity(CacheConstants.INITIAL_CAPACITY)
                        .maximumSize(CacheConstants.MAXIMUM_SIZE)
                        .expireAfterWrite(CacheConstants.CACHE_TTL)
                        .build(key -> loadUserPermissions(key.toString()))); // Função de carregamento

        cacheManager.setCaches(Arrays.asList(rolesCache, permissionsCache));
        return cacheManager;
    }
    private Set<String> loadUserRoles(String username) {
        return repositoryUser.findByUsername(username)
                .map(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
    private Set<String> loadUserPermissions(String username) {
        return repositoryUser.findByUsername(username)
                .map(user -> user.getRoles().stream()
                        .flatMap(role -> role.getPrivileges().stream())
                        .map(Privilege::getName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }
}