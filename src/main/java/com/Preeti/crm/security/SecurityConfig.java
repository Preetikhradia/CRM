package com.Preeti.crm.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ✅ LEADS RULES
                        .requestMatchers(HttpMethod.GET, "/api/leads/**").hasAnyRole("ADMIN", "SALES")
                        .requestMatchers(HttpMethod.POST, "/api/leads/**").hasAnyRole("ADMIN", "SALES")
                        .requestMatchers(HttpMethod.PUT, "/api/leads/**").hasRole("ADMIN")    // Only Admin can Edit Leads
                        .requestMatchers(HttpMethod.DELETE, "/api/leads/**").hasRole("ADMIN") // Only Admin can Delete Leads

                        // ✅ CUSTOMERS RULES
                        .requestMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("ADMIN", "SALES")
                        .requestMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("ADMIN", "SALES")
                        .requestMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("ADMIN", "SALES") // Both can Edit Customers
                        .requestMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN") // Only Admin can Delete Customers

                        // General
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}