package com.superstore.config;

import com.superstore.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The WebSecurityConfig class is responsible for configuring the security of the web application.
 * It provides authentication and authorization configurations using JWT token-based authentication.
 *
 * This class is annotated with @Configuration, @EnableWebSecurity, @EnableMethodSecurity, and @AllArgsConstructor.
 * It also has a JwtAuthenticationFilter as a dependency.
 *
 * The class provides the following methods:
 * - passwordEncoder(): Creates a PasswordEncoder bean using BCryptPasswordEncoder.
 * - authenticationManager(AuthenticationConfiguration configuration): Creates an AuthenticationManager bean by accessing
 *    the authentication manager from the provided AuthenticationConfiguration.
 * - securityFilterChain(HttpSecurity http): Configures the security filter chain using HttpSecurity.
 *    - Disables CSRF protection
 *    - Configures permissions for different endpoints
 *    - Sets session creation policy to STATELESS
 *    - Adds JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
 *
 * Note: The JwtAuthenticationFilter, JwtService, User, and other dependencies are not included in this documentation,
 *       but are required for the proper functioning of the WebSecurityConfig class.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class WebSecurityConfig {


    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/v1/users", "/v1/users/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api-docs", "/swagger-ui/index.html").permitAll()

                        .requestMatchers("/v1/favorites").authenticated()

                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
