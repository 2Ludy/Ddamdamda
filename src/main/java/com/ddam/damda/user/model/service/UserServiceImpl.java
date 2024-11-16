package com.ddam.damda.user.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@Transactional
	@Override
	public boolean validByUsername(String username) {
		User user = userMapper.validByUsername(username);
		 return user != null;
	}
	
	@Transactional
	@Override
	public boolean validByEmail(String email) {
		User user = userMapper.validByEmail(email);
		return user != null;
	}
	
	@Transactional
	@Override
	public boolean updatePassword(String email, String newPassword) {
			User user = userMapper.findByEmail(email);

            // 2. 새 비밀번호 암호화
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            
            // 3. 비밀번호 업데이트
            int result = userMapper.updatePassword(email, encodedNewPassword);
            
            return result > 0;
	}
}
