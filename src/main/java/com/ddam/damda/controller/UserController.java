package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse("error", "서버 오류가 발생했습니다.", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@Operation(summary = "user의 id를 이용하여 username을 가져오는 메서드", description = "int로 id를 받아 username을 가져오는 메서드")
    @GetMapping("/findusername/{id}")
    public ResponseEntity<?> findUsernameById(@PathVariable int id) {
    	try {
    		String username = userService.findUsernameById(id);
    		if(username != null) {
    			return new ResponseEntity<String>(username, HttpStatus.OK);
    		}
    		return new ResponseEntity<>(new ApiResponse("fail", "findUsernameById", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("error", "findUsernameById", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@Operation(summary = "id를 이용하여 user정보를 가져오는 메서드", description = "int로 id를 받아 user정보를 가져오는 메서드")
    @GetMapping("/{id}")
    public ResponseEntity<?> findId(@PathVariable int id) {
    	try {
    		User user = userService.findById(id);
    		if(user != null) {
    			return new ResponseEntity<User>(user, HttpStatus.OK);
    		}
    		return new ResponseEntity<>(new ApiResponse("fail", "findId", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("error", "findId", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@Operation(summary = "User Dto의 userId와 password를 받아 비밀번호가 유효한지 체크하는 메서드", description = "User Dto의 userId와 password를 받아 비밀번호가 유효한지 체크하는 메서드")
    @PostMapping("/validpassword")
    public ResponseEntity<?> validPassword(@RequestBody User user) {
    	try {
    		boolean isS = userService.validpassword(user);
    		if(isS) {
    			return new ResponseEntity<Boolean>(isS, HttpStatus.OK);
    		}
    		return new ResponseEntity<Boolean>(isS, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("error", "findId", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
	@Operation(summary = "password, username, 이미지 파일을 이용히여 수정", description = "password, username, 이미지 파일을 이용히여 수정")
	@PostMapping("/edit")
	public ResponseEntity<?> editProfile(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
	        @RequestPart("userData") User request) {
	    try {
	        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
	            return new ResponseEntity<>(
	                new ApiResponse("fail", "유효하지 않은 토큰입니다.", 401),
	                HttpStatus.UNAUTHORIZED
	            );
	        }

	        String token = authHeader.substring(7);
	        String email = jwtService.extractUsername(token);
	        User currentUser = userService.findByEmail(email).orElseThrow();
	        
	        // 프로필 업데이트
	        boolean isPasswordChanged = userService.updateProfile(currentUser, imageFile, request);
	        
	        if (isPasswordChanged) {
	            // 비밀번호가 변경된 경우 토큰 무효화
	            List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(currentUser.getId());
	            validTokens.forEach(t -> t.setIsLoggedOut(true));
	            tokenRepository.saveAll(validTokens);
	        }

	        return new ResponseEntity<>(
	            new ApiResponse("success", "프로필이 업데이트되었습니다.", 200),
	            HttpStatus.OK
	        );
	    } catch (Exception e) {
	        log.error("Profile update error: ", e);
	        return new ResponseEntity<>(
	            new ApiResponse("error", "서버 오류가 발생했습니다.", 500),
	            HttpStatus.INTERNAL_SERVER_ERROR
	        );
	    }
	}
	
	@Operation(summary = "user의 id를 이용하여 User를 삭제", description = "int로 id를 받아 User를 삭제하는 메서드, 좋아요, 가입된 그룹인원, 댓글수 또한 달라짐")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
    	try {
    		int isS = userService.deleteUser(id);
    		if(isS > 0) {
    			return new ResponseEntity<>(new ApiResponse("Success", "deleteUser", 200), HttpStatus.OK);
    		}
    		return new ResponseEntity<>(new ApiResponse("fail", "deleteUser", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse("error", "deleteUser", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}