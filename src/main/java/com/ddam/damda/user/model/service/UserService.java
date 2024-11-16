package com.ddam.damda.user.model.service;

import java.util.Optional;

import com.ddam.damda.user.model.User;

public interface UserService {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	User save(User user);
	
	boolean validByUsername(String username);
	
	boolean validByEmail(String email);

}
