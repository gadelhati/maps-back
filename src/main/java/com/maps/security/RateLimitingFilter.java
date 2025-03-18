package com.maps.security;

import com.maps.MapsApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Component
public class RateLimitingFilter implements Filter {
    private final ConcurrentMap<String, Bucket> cache = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MapsApplication.class);
    private ScheduledExecutorService scheduler;

    private static final int DEFAULT_REQUESTS_PER_MINUTE = 100;
    private static final int DEFAULT_BURST_MULTIPLIER = 2;
    private static final int DEFAULT_CLEANUP_INTERVAL = 15;
    private static final int MINIMUM_REQUESTS = 200;

    @Value("${rate.limit.requests:" + DEFAULT_REQUESTS_PER_MINUTE + "}")
    private int requestsPerMinute;

    @Value("${rate.limit.cleanup.interval:" + DEFAULT_CLEANUP_INTERVAL + "}")
    private int cleanupIntervalMinutes;

    @Value("${rate.limit.burst.multiplier:" + DEFAULT_BURST_MULTIPLIER + "}")
    private int burstMultiplier;

    @PostConstruct
    public void init() {
        validateConfiguration();

        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "rate-limit-cleanup");
            thread.setDaemon(true);
            return thread;
        });

        scheduler.scheduleAtFixedRate(
                this::cleanupCache,
                cleanupIntervalMinutes,
                cleanupIntervalMinutes,
                TimeUnit.MINUTES
        );

        LOGGER.info("Rate limiting initialized with: requests={}, burst={}, cleanup={}min",
                requestsPerMinute, burstMultiplier, cleanupIntervalMinutes);
    }

    private void validateConfiguration() {
        if (requestsPerMinute <= 0) {
            LOGGER.warn("Invalid rate.limit.requests ({}), using default: {}",
                    requestsPerMinute, DEFAULT_REQUESTS_PER_MINUTE);
            requestsPerMinute = DEFAULT_REQUESTS_PER_MINUTE;
        }

        if (burstMultiplier <= 0) {
            LOGGER.warn("Invalid rate.limit.burst.multiplier ({}), using default: {}",
                    burstMultiplier, DEFAULT_BURST_MULTIPLIER);
            burstMultiplier = DEFAULT_BURST_MULTIPLIER;
        }

        if (cleanupIntervalMinutes <= 0) {
            LOGGER.warn("Invalid rate.limit.cleanup.interval ({}), using default: {}",
                    cleanupIntervalMinutes, DEFAULT_CLEANUP_INTERVAL);
            cleanupIntervalMinutes = DEFAULT_CLEANUP_INTERVAL;
        }
    }

    private Bucket createNewBucket() {
        // Ensure we have at least one token for both capacity and refill
        int tokensPerMinute = Math.max(requestsPerMinute, MINIMUM_REQUESTS);
        int capacity = tokensPerMinute * Math.max(burstMultiplier, 1);

        return Bucket.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(capacity)
                        .refillGreedy(tokensPerMinute, Duration.ofMinutes(1))
                        .build())
                .build();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        String clientId = getClientIdentifier(httpRequest);
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            Bucket bucket = cache.computeIfAbsent(clientId, k -> createNewBucket());
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            addRateLimitHeaders(httpResponse, probe);

            if (probe.isConsumed()) {
                chain.doFilter(request, response);
            } else {
                handleRateLimitExceeded(httpResponse, probe);
            }
        } catch (Exception e) {
            LOGGER.error("Error in rate limiting for client {}: {}", clientId, e.getMessage(), e);
            // Fail open - allow request in case of rate limiting errors
            chain.doFilter(request, response);
        }
    }

    private String getClientIdentifier(HttpServletRequest request) {
        String clientIp = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .filter(ip -> !ip.isEmpty())
                .map(ip -> ip.split(",")[0].trim())
                .orElseGet(() -> Optional.ofNullable(request.getHeader("X-Real-IP"))
                        .orElse(request.getRemoteAddr()));

        return String.format("%s:%s:%s",
                clientIp,
                request.getRequestURI(),
                request.getMethod());
    }

    private void addRateLimitHeaders(HttpServletResponse response, ConsumptionProbe probe) {
        response.addHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
        response.addHeader("X-RateLimit-Limit", String.valueOf(requestsPerMinute));
        response.addHeader("X-RateLimit-Reset", String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000_000));
    }

    private void handleRateLimitExceeded(HttpServletResponse response, ConsumptionProbe probe) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        long retryAfterSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
        response.addHeader("Retry-After", String.valueOf(retryAfterSeconds));

        Map<String, Object> errorResponse = Map.of(
                "status", HttpStatus.TOO_MANY_REQUESTS.value(),
                "error", "Too Many Requests",
                "message", String.format("Rate limit exceeded. Please try again in %d seconds.", retryAfterSeconds)
        );

        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

    private void cleanupCache() {
        LOGGER.debug("Starting rate limit cache cleanup");
        int initialSize = cache.size();

        cache.entrySet().removeIf(entry -> {
            ConsumptionProbe probe = entry.getValue().tryConsumeAndReturnRemaining(0);
            return probe != null &&
                    probe.getRemainingTokens() == (requestsPerMinute * burstMultiplier);
        });

        LOGGER.debug("Rate limit cache cleanup completed. Entries removed: {}",
                initialSize - cache.size());
    }

    @PreDestroy
    public void destroy() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    LOGGER.warn("Rate limit cleanup scheduler did not terminate in time");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warn("Rate limit cleanup scheduler shutdown interrupted");
            }
        }
        cache.clear();
    }
}