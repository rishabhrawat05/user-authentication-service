package com.travel.user_management.JwtAuth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * This class provides utility methods for handling JSON Web Tokens (JWT) 
 * in the user management module of the Travel application. 
 * It includes methods for generating, parsing, and validating JWTs.
 * 
 * Key Features:
 * - Token generation for user authentication.
 * - Token validation and expiration handling.
 * - Parsing token claims to extract user-specific details.
 */
@Component
public class JwtAuthenticationHelper {
    
    /**
     * The secret key used for signing and validating the JWTs.
     * Configured in the application properties file.
     */
    @Value("${jwt.secretKey}}")
    private String secretKey;
    
    /**
     * The validity duration of the JWT in seconds (1 hour).
     */
    private final long JWT_TOKEN_VALIDITY = 60 * 60;

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the username embedded in the token.
     */
    public String getUsernameFromToken(String token) {
        String username = getClaimsFromToken(token).getSubject();
        return username;
    }

    /**
     * Parses the claims from the provided JWT token.
     *
     * @param token the JWT token.
     * @return the claims contained within the token.
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /**
     * Checks whether the provided JWT token has expired.
     *
     * @param token the JWT token.
     * @return true if the token has expired, false otherwise.
     */
    public Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expDate = claims.getExpiration();
        return expDate.before(new Date());
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user's details, including the username.
     * @return the generated JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()), SignatureAlgorithm.HS512)
                .compact();
    }
}
