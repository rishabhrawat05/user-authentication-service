package com.travel.user_management.UserDto;

/**
 * Data Transfer Object (DTO) for handling login responses.
 * 
 * Contains the token issued upon successful authentication,
 * which is used for subsequent API calls.
 */
public class LoginResponse {

  
    private String token;

    /**
     * Constructor to initialize the LoginResponse with a token.
     * 
     * @param token The JWT token issued after successful login.
     */
    public LoginResponse(String token) {
        this.token = token;
    }

    /**
     * Gets the token issued in the login response.
     * 
     * @return The JWT token as a String.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token in the login response.
     * 
     * @param token The JWT token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
