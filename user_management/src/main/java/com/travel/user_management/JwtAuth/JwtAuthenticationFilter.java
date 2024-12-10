package com.travel.user_management.JwtAuth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This filter handles JWT-based authentication for every incoming HTTP request. 
 * It extracts and validates the JWT token, sets the user authentication context, 
 * and ensures security is enforced for all endpoints.
 * 
 * Key Features:
 * - Extracts the JWT token from the "Authorization" header.
 * - Validates the token and retrieves the associated username.
 * - Loads user details and sets authentication in the Security Context.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs the JwtAuthenticationFilter with dependencies.
     *
     * @param jwtHelper          Utility class for JWT-related operations.
     * @param userDetailsService Service to load user details from the database.
     */
    public JwtAuthenticationFilter(JwtAuthenticationHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters each incoming request to validate the JWT token and set user authentication.
     *
     * @param request     The incoming HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to proceed with the request.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException      if an input or output error is detected.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Extract the Authorization header
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        // Validate the token format (must start with "Bearer ")
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7); // Extract the token part
            username = jwtHelper.getUsernameFromToken(token);

            // If the username is valid and no authentication exists in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Check if the token is valid (not expired)
                if (!jwtHelper.isTokenExpired(token)) {
                    // Create an authentication token
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Set the authentication in the security context
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    System.out.println("Token is expired or user details not found.");
                }
            }
        }

        // Proceed with the request
        filterChain.doFilter(request, response);
    }
}
