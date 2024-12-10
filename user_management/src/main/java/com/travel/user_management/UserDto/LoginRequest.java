package com.travel.user_management.UserDto;

/**
 * Data Transfer Object (DTO) for handling login requests.
 * 
 * Contains the necessary fields for authenticating a user: 
 * username and password.
 */
public class LoginRequest {
    
    private String username;

    private String password;

    /**
     * Gets the username provided in the login request.
     * 
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the login request.
     * 
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password provided in the login request.
     * 
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the login request.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
