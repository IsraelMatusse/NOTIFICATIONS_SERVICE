package com.personal_projects.notifications_qpi.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.Collections;

public class ApiKeyAuthentication implements Authentication {

    private final String apiKey;
    private final boolean authenticated;

    public ApiKeyAuthentication(String apiKey, boolean authenticated) {
        this.apiKey = apiKey;
        this.authenticated = authenticated;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Cannot change authentication status");
    }

    @Override
    public String getName() {
        return apiKey;
    }
}