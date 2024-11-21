package com.ddam.damda.user.model.mapper;

import com.ddam.damda.user.model.User;

public interface UserMapper {
	
	User findByEmail(String email);
	
	User findByUsername(String username);
	
	User findById(int id);
	
	int save(User user);
	
	User validByUsername(String username);
	
	User validByEmail(String email);

	int updatePassword(String email, String password);
	
	String findUserNameById(int id);
	
	int updateUser(User user);
	
	int deleteUser(int id);
	
	void deleteUserLikes(int id);
	
	void deleteUserComment(int id);
	
	void deleteUserGroupMember(int id);

}
