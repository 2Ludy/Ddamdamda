package com.ddam.damda.user.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddam.damda.jwt.model.CustomUserDetails;
import com.ddam.damda.user.model.User;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> nUser = userService.findByEmail(username);
		return nUser.map(CustomUserDetails::new)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
