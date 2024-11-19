package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.jwt.model.Token;
import com.ddam.damda.jwt.model.service.JwtService;
import com.ddam.damda.jwt.model.service.TokenService;
import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
//@CrossOrigin("*")
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private TokenService tokenRepository;

	@Operation(summary = "username이 중복되는지 확인하는 메서드", description = "String으로 username을 받아 판단")
    @GetMapping("/vaildname/{username}")
    public ResponseEntity<?> vaildName(@PathVariable String username) {
    	try {
    		boolean isS = userService.validByUsername(username);
    		if(isS) {
    			return new ResponseEntity<>(new ApiResponse("fail", "이미 사용중인 이름입니다", 409), HttpStatus.CONFLICT);
    		}
    		return new ResponseEntity<>(new ApiResponse("success", "사용 가능한 이름입니다.", 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("error", "서버 오류가 발생했습니다.", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
	@Operation(summary = "email이 중복되는지 확인하는 메서드", description = "String으로 email을 받아 판단")
    @GetMapping("/vaildemail/{email}")
    public ResponseEntity<?> vaildEmail(@PathVariable String email) {
    	try {
    		boolean isS = userService.validByEmail(email);
    		if(isS) {
    			return new ResponseEntity<>(new ApiResponse("fail", "이미 사용중인 이메일입니다", 409), HttpStatus.CONFLICT);
    		}
    		return new ResponseEntity<>(new ApiResponse("success", "사용 가능한 이메일입니다.", 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("error", "서버 오류가 발생했습니다.", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
//    @PostMapping("/edit")
//    public ResponseEntity<?> editProfile(
//            @RequestHeader("Authorization") String authHeader,
//            @RequestBody User request) {
//        try {
//            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//                return new ResponseEntity<>(
//                    new ApiResponse("fail", "유효하지 않은 토큰입니다.", 401),
//                    HttpStatus.UNAUTHORIZED
//                );
//            }
//
//            String token = authHeader.substring(7);
//            String email = jwtService.extractUsername(token);
//            User currentUser = userService.findByEmail(email).orElseThrow();
//            
//            boolean isUpdated = false;
//
//            // 비밀번호 변경
//            if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
//                boolean passwordChanged = userService.updatePassword(email, request.getPassword());
//                if (passwordChanged) {
//                    List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(currentUser.getId());
//                    validTokens.forEach(t -> t.setIsLoggedOut(true));
//                    tokenRepository.saveAll(validTokens);
//                    isUpdated = true;
//                }
//            }
//
//            // username 변경
//            if (request.getUsername() != null && !request.getUsername().equals(currentUser.getUsername())) {
//                currentUser.setUsername(request.getUsername());
//                isUpdated = true;
//            }
//
//            // profile_image_id 변경
//            if (request.getProfileImageId() != currentUser.getProfileImageId()) {
//                currentUser.setProfileImageId(request.getProfileImageId());
//                isUpdated = true;
//            }
//
//            // 변경사항이 있으면 저장
//            if (isUpdated) {
//                userService.updateUser(currentUser);
//                return new ResponseEntity<>(
//                    new ApiResponse("success", "정보가 수정되었습니다.", 200),
//                    HttpStatus.OK
//                );
//            } else {
//                return new ResponseEntity<>(
//                    new ApiResponse("fail", "변경된 정보가 없습니다.", 400),
//                    HttpStatus.BAD_REQUEST
//                );
//            }
//        } catch (Exception e) {
//            log.error("Profile update error: ", e);
//            return new ResponseEntity<>(
//                new ApiResponse("error", "서버 오류가 발생했습니다.", 500),
//                HttpStatus.INTERNAL_SERVER_ERROR
//            );
//        }
//    }
}