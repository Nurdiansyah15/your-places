package com.nurd.your.places.exceptions.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nurd.your.places.utils.dtos.formator.ErrorResponseFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthEntryPointHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponseFormat.builder().error("authentication required").status(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build()));
    }
}
