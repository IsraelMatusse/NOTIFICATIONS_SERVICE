package com.personal_projects.notifications_qpi.infrastructure.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal_projects.notifications_qpi.dtos.internal.ErrorResponse;
import com.personal_projects.notifications_qpi.infrastructure.exceptions.UnauthorizedException;
import com.personal_projects.notifications_qpi.services.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthenticationFilter extends OncePerRequestFilter {

    private final ApiKeyService apiKeyService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationFilter(ApiKeyService apiKeyService, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.apiKeyService = apiKeyService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private final Set<String> publicPaths = new HashSet<>(List.of(
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-key/generate",
            "/api-key/valid",
            "actuator/**"
    ));
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return publicPaths.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws  IOException {
        try {
            String apiKey = extractApiKey(request);

            if (apiKey == null) {
                handleException(response, new UnauthorizedException("API key não encontrada"), HttpStatus.UNAUTHORIZED);
                return;
            }

            boolean apiKeyValid = apiKeyService.validateKey(apiKey);
            if (!apiKeyValid) {
                handleException(response, new UnauthorizedException("API key inválida"), HttpStatus.UNAUTHORIZED);
                return;
            }

            ApiKeyAuthentication authentication = new ApiKeyAuthentication(apiKey, true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void handleException(HttpServletResponse response, Exception exception, HttpStatus status) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(status.value(), exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }


    private String extractApiKey(HttpServletRequest request) {
        String apiKey = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (apiKey != null && apiKey.startsWith("Bearer ")) {
            return apiKey.substring(7);
        }
        return null;
    }
}



