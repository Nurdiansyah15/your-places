package com.nurd.your.places.securities;

import com.nurd.your.places.exceptions.handlers.CustomAccessDeniedHandler;
import com.nurd.your.places.exceptions.handlers.CustomAuthEntryPointHandler;
import com.nurd.your.places.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityChain {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthEntryPointHandler customAuthEntryPointHandler;

    @Autowired
    private CustomAuthFilter customAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/users/**").hasRole("ADMIN")
                                .requestMatchers("/api/places/**", "/api/place/**").permitAll()
                                .requestMatchers("/api/user/**").authenticated()
                                .requestMatchers("/api/posts/**").authenticated()
                                .requestMatchers("/api/public/posts/**").permitAll()
                                .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthEntryPointHandler).accessDeniedHandler(customAccessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customAuthFilter, JwtAuthFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource));
        return http.build();
    }
}
