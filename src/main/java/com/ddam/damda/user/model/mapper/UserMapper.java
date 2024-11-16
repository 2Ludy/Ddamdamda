package com.ddam.damda.user.model.mapper;

import com.ddam.damda.user.model.User;

public interface UserMapper {
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	int save(User user);
	
	User validByUsername(String username);
	
	User validByEmail(String email);

}
