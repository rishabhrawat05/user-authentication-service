package com.travel.user_management.UserDto;

import com.travel.user_management.UserModel.Role;

/**
 * Data Transfer Object (DTO) for handling user information.
 * 
 * This DTO is used to transfer user data, including personal details and role information,
 * between different layers of the application.
 */
public class UserDto {

   
    private Long id;


    private String name;

    
    private String username;

    
    private String email;

    
    private String password;

    
    private String phoneNumber;

    
    private Role role;

    /**
     * Gets the user's ID.
     * 
     * @return The user's ID as a Long.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     * 
     * @param id The user's ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user's full name.
     * 
     * @return The user's name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's full name.
     * 
     * @param name The user's name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's username.
     * 
     * @return The username as a String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * 
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the user's email address.
     * 
     * @return The email address as a String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     * 
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's password.
     * 
     * @return The password as a String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's phone number.
     * 
     * @return The phone number as a String.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the user's phone number.
     * 
     * @param phoneNumber The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the user's role.
     * 
     * @return The role as a Role object.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * 
     * @param role The role to set.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
