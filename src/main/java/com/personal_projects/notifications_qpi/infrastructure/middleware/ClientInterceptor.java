package com.personal_projects.notifications_qpi.infrastructure.middleware;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientInterceptor implements HandlerInterceptor {

    private final Set<String> publicPaths = new HashSet<>(List.of(
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-key/generate",
            "/api-key/valid"
    ));
    @Override
    public boolean preHandle(@NotNull  HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(publicPaths.stream().anyMatch(p -> request.getRequestURI().startsWith(p))){
            return true;
        }else {
            ClientContext.setCurrentTenant(request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", ""));
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ClientContext.clear();
    }


}
