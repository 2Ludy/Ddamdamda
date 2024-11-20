package com.ddam.damda.user.model.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.user.model.User;

public interface UserService {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	User findById(int id);
	
	User save(User user);
	
	boolean validByUsername(String username);
	
	boolean validByEmail(String email);
	
	boolean updatePassword(String email, String newPassword);
	
	String findUserNameById(int id);
	
	boolean updateProfile(User currentUser, MultipartFile imageFile, User request) throws IOException;

}
