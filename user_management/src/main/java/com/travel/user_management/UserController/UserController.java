package com.travel.user_management.UserController;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.user_management.UserDto.LoginRequest;
import com.travel.user_management.UserDto.LoginResponse;
import com.travel.user_management.UserDto.UserDto;
import com.travel.user_management.UserModel.User;
import com.travel.user_management.UserService.UserService;

/**
 * Controller class for managing user-related operations.
 * 
 * Provides RESTful endpoints for user actions such as signup, signin, 
 * fetching users by ID, fetching all users, updating user details, 
 * and deleting users.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    
    /**
     * Constructor to inject the UserService dependency.
     *
     * @param userService The service layer to handle user-related operations.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles user signup requests.
     * 
     * @param userDto The DTO containing user details.
     * @return A ResponseEntity with a success message.
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        userService.signup(userDto);
        return ResponseEntity.ok("User Signup Successful");
    }

    /**
     * Handles user signin requests.
     * 
     * @param loginRequest The DTO containing login credentials (username and password).
     * @return A ResponseEntity with a LoginResponse containing token and user details.
     */
    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.signin(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    /**
     * Fetches a user by their ID.
     * 
     * @param Id The ID of the user.
     * @return A ResponseEntity containing the user details or a NOT_FOUND status if the user does not exist.
     */
    @GetMapping("/getuserbyid/{Id}")
    public ResponseEntity<User> getUserById(@PathVariable Long Id) {
        User user = userService.getUserById(Id);
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Fetches all users in the system.
     * 
     * @return A ResponseEntity containing a list of all users.
     */
    @GetMapping("/getallusers")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    /**
     * Updates a user's details by their ID.
     * 
     * @param Id The ID of the user to be updated.
     * @param userDto The DTO containing updated user details.
     * @return A ResponseEntity containing the updated user object.
     */
    @PutMapping("/updateuser/{Id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long Id, @RequestBody UserDto userDto) {
        User user = userService.updateUserById(Id, userDto);
        return ResponseEntity.ok(user);
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param Id The ID of the user to be deleted.
     * @return A ResponseEntity with a success message.
     */
    @DeleteMapping("/deleteuser/{Id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long Id) {
        userService.deleteUserById(Id);
        return ResponseEntity.ok("User Deleted Successfully");
    }
}
