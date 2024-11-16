package com.ddam.damda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.user.model.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
    private UserService userService;

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
}