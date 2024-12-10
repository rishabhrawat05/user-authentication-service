package com.travel.user_management.UserModel;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

/**
 * This class represents the User entity in the user management service. 
 * It maps to the "users" table in the database.
 *  The class is annotated with JPA and Lombok annotations for simplified persistence and 
 *  reduced boilerplate code.
 */

@Entity
@Table(name = "users")
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2086418729519619403L;

	/**
	 * The unique identifier for each user. This field is the primary key and is
	 * auto_generated.
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	/**
	 * The name of the user. This field cannot be null as specified by the nullable
	 * = false constraint.
	 */
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * The email address of the user. This field must be unique and not null. It
	 * also includes validation to ensure a valid email format.
	 */
	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Email is not valid")
	private String email;

	/**
	 * The username for the user. This field is required, unique, and cannot be
	 * null.
	 */
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	/**
	 * The phone number of the user. It must be unique and cannot be null. This can
	 * be used for contact purposes or two-factor authentication.
	 */
	@Column(name = "phone_number", nullable = false, unique = true)
	private String phoneNumber;

	/**
	 * The password for the user's account. Passwords should be stored in a hashed
	 * and salted format for security.
	 */
	@Column(name = "password", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	


}
