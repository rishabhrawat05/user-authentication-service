package com.travel.user_management.UserConfig;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.travel.user_management.JwtAuth.JwtAuthenticationFilter;

/**
 * This class configures the security settings for the Travel application.
 * It sets up JWT-based authentication and manages the application's security filter chain.
 * 
 * Key Features:
 * - Stateless session management.
 * - Custom JWT filter integration.
 * - Permits certain endpoints for public access (e.g., signup and signin).
 * - Secures other endpoints by requiring authentication.
 */
@Configuration
@EnableWebSecurity
public class UserConfig {

    private final JwtAuthenticationFilter jwtFilter;

    /**
     * Constructor for injecting the JwtAuthenticationFilter dependency.
     *
     * @param jwtFilter The JWT authentication filter to process tokens.
     */
    public UserConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Configures the SecurityFilterChain for the application.
     * 
     * - Disables CSRF for stateless API design.
     * - Configures permitted and secured endpoints.
     * - Integrates the JWT filter into the filter chain.
     * - Enables basic authentication and disables session-based authentication.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If there is an error in configuration.
     */
    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for APIs
            .csrf(csrf -> csrf.disable())
            
            // Configure authorization rules
            .authorizeHttpRequests(request -> request
                .requestMatchers("/user/signup").permitAll() // Public endpoint for user registration
                .requestMatchers("/user/signin").permitAll() // Public endpoint for user login
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            
            // Configure stateless session management
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Enable form-based login
            .formLogin(withDefaults());
        
        // Add the JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(httpBasic -> {}); // Enable HTTP Basic Authentication (optional)
        
        return http.build();
    }

    /**
     * Configures the AuthenticationManager bean to manage authentication processes.
     *
     * @param builder The AuthenticationConfiguration builder.
     * @return The AuthenticationManager bean.
     * @throws Exception If there is an error in configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
