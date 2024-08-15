package com.nurd.your.places.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthFilter extends OncePerRequestFilter {

    @Value("${X-Admin-Creation}")
    private String xAdminCreation;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();
        boolean urlAdminCreation = pathMatcher.match("/api/auth/admin", request.getRequestURI());

        if (request.getMethod().equals("POST") && urlAdminCreation) {

            if (!request.getHeader("X-Admin-Creation").equals(xAdminCreation)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }


}
