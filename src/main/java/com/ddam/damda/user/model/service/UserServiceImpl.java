package com.ddam.damda.user.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Transactional
	@Override
	public Optional<User> findByEmail(String email) {
		User user = userMapper.findByEmail(email);
		return Optional.ofNullable(user);
	}

	@Transactional
	@Override
	public Optional<User> findByUsername(String username) {
		User user = userMapper.findByUsername(username);
		return Optional.ofNullable(user);
	}
	
	@Transactional
	@Override
	public User save(User user) {
		int isS = userMapper.save(user);
		if(isS > 0) {
			return userMapper.findByEmail(user.getEmail());
		}
		return null;
	}

}
