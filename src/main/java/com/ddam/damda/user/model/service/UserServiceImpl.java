package com.ddam.damda.user.model.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.service.ImagesService;
import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ImagesService imageService;
	
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

	@Override
	public User findById(int id) {
		return userMapper.findById(id);
	}

	@Override
	public String findUsernameById(int id) {
		return userMapper.findUserNameById(id);
	}
	
	@Override
	@Transactional
	public boolean updateProfile(User currentUser, MultipartFile imageFile, User request) throws IOException {
	    boolean isUpdated = false;
	    boolean passwordChanged = false;

	    // 1. 이미지 처리
	    if (imageFile != null && !imageFile.isEmpty()) {
	        int imageId = imageService.saveProfileImage(imageFile);
	        currentUser.setProfileImageId(imageId);
	        isUpdated = true;
	    }

	    // 2. 비밀번호 변경
	    if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
	        String encodedPassword = passwordEncoder.encode(request.getPassword());
	        userMapper.updatePassword(currentUser.getEmail(), encodedPassword);
	        passwordChanged = true;
	        isUpdated = true;
	    }

	    // 3. username 변경
	    if (request.getUsername() != null && !request.getUsername().equals(currentUser.getUsername())) {
	        currentUser.setUsername(request.getUsername());
	        isUpdated = true;
	    }

	    // 4. 변경사항이 있으면 저장
	    if (isUpdated) {
	        userMapper.updateUser(currentUser);
	    }

	    // 비밀번호 변경 여부만 반환
	    return passwordChanged;
	}

	@Override
	@Transactional
	public int deleteUser(int id) {
		
		userMapper.deleteUserLikes(id);
		userMapper.deleteUserGroupMember(id);
		userMapper.deleteUserComment(id);
		
		return userMapper.deleteUser(id);
	}
}
