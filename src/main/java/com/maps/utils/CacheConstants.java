package com.maps.utils;

import lombok.Getter;

import java.time.Duration;

@Getter
public final class CacheConstants {
    private CacheConstants() {} // Construtor privado para classe utilitária

    public static final String ROLES_CACHE = "user-roles";
    public static final String PERMISSIONS_CACHE = "user-permissions";
    public static final Duration CACHE_TTL = Duration.ofHours(1);
    public static final Duration CACHE_REFRESH = Duration.ofMinutes(45);
    public static final int INITIAL_CAPACITY = 100;
    public static final int MAXIMUM_SIZE = 5000;
}