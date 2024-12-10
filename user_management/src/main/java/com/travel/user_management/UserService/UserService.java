package com.travel.user_management.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

import com.travel.user_management.JwtAuth.JwtAuthenticationHelper;
import com.travel.user_management.UserConfig.PasswordEncoder;
import com.travel.user_management.UserDto.LoginRequest;
import com.travel.user_management.UserDto.LoginResponse;
import com.travel.user_management.UserDto.UserDto;
import com.travel.user_management.UserException.UserAlreadyExistsException;
import com.travel.user_management.UserException.UserNotFoundException;
import com.travel.user_management.UserModel.Role;
import com.travel.user_management.UserModel.User;
import com.travel.user_management.UserRepository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationHelper jwtHelper;
	
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, 
			UserDetailsService userDetialsService, JwtAuthenticationHelper jwtHelper) {
		
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetialsService;
		this.jwtHelper=jwtHelper;
	}
	
	/**
     * Registers a new user.
     * 
     * @param userDto Data transfer object containing user registration data.
     * @throws UserAlreadyExistsException if a user already exists with the given username.
     */
	
	public void signup(UserDto userDto) {
		Optional<User> optUser=userRepository.findByUsername(userDto.getUsername());
		if(optUser.isPresent()) {
			throw new UserAlreadyExistsException("User Already Exists with User Id " + userDto.getId());
		}
		User new_user=DtoToEntity(userDto);
		userRepository.save(new_user);
		
		
	}
	
	/**
     * Authenticates a user and generates a JWT token upon successful authentication.
     * 
     * @param loginRequest Contains the username and password for login.
     * @return A response containing the JWT token.
     * @throws BadCredentialsException if authentication fails.
     */
	
	public LoginResponse signin(LoginRequest loginRequest) {
		
		Authenticate(loginRequest.getUsername(), loginRequest.getPassword());
		
		UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getUsername());
		String token=jwtHelper.generateToken(userDetails);
		LoginResponse loginResponse=new LoginResponse(token);
		return loginResponse;
	}
	
	/**
     * Authenticates a user's credentials.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @throws BadCredentialsException if the credentials are invalid.
     */
	
	public void Authenticate(String username, String password) {
	    

	    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	    
	    try {
	        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	        
	    } catch (BadCredentialsException e) {
	        
	        throw new BadCredentialsException("Bad Credentials");
	    }
	}
	
	/**
     * Fetches a user by their ID.
     * 
     * @param Id The ID of the user to retrieve.
     * @return The User entity if found.
     * @throws UserNotFoundException if the user is not found.
     */
	
	public User getUserById(Long Id) {
		Optional<User> optUser=userRepository.findById(Id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("User not found with user id "+Id);
		}
		return optUser.get();
	}
	
	/**
     * Retrieves all users in the system.
     * 
     * @return A list of all users.
     */
	
	public List<User> getAllUser(){
		List<User> users=userRepository.findAll();
		if(users.isEmpty()) {
			return new ArrayList<User>();
		}
		return users;
	}
	
	/**
     * Updates a user's information by their ID.
     * 
     * @param Id The ID of the user to update.
     * @param userDto The updated user data.
     * @return The updated User entity.
     * @throws UserNotFoundException if the user is not found.
     */
	
	public User updateUserById(Long Id,UserDto userDto) {
		Optional<User> optUser=userRepository.findById(Id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("user not found");
		}
		User updated_user=DtoToEntity(userDto);
		userRepository.save(updated_user);
		return updated_user;
	}
	
	/**
     * Deletes a user by their ID.
     * 
     * @param Id The ID of the user to delete.
     * @throws UserNotFoundException if the user is not found.
     */
	
	public void deleteUserById(Long Id) {
		Optional<User> optUser=userRepository.findById(Id);
		if(optUser.isEmpty()) {
			throw new UserNotFoundException("user not found");
		}
		User user=optUser.get();
		userRepository.delete(user);
	}
	
	/**
     * Converts a UserDto to a User entity.
     * 
     * @param userDto The user data transfer object to convert.
     * @return The corresponding User entity.
     */
	
	public User DtoToEntity(UserDto userDto) {
		User user=new User();
		String encodedPassword=passwordEncoder.bCryptPasswordEncoder().encode(userDto.getPassword());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setPassword(encodedPassword);
		user.setRole(Role.USER);
		return user;
	}
}
