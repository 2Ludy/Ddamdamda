package com.ddam.damda.jwt.model.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddam.damda.jwt.model.CustomUserDetails;
import com.ddam.damda.jwt.model.User;
@Service
public class UserDetailServiceImp implements UserDetailsService {

	private UserService userService;
	
	public UserDetailServiceImp(UserService userService) {
		super();
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> nUser = userService.findByUsername(username); //조인으로 수정
		return nUser.map(CustomUserDetails::new)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
