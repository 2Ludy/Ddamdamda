package com.ddam.damda.jwt.model.service;

import java.util.Optional;

import com.ddam.damda.jwt.model.User;

public interface UserService {

	Optional<User> findByUsername(String username);

	User save(User user);

}
