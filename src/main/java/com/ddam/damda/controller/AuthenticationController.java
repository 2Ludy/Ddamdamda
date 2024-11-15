package com.ddam.damda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.jwt.model.User;
import com.ddam.damda.jwt.model.service.AuthenticationService;
import com.ddam.damda.jwt.model.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final TokenService tokenRepository;

	public AuthenticationController(AuthenticationService authService, TokenService tokenRepository) {
		super();
		this.authService = authService;
		this.tokenRepository = tokenRepository;
	}

	//http://localhost:8080/refresh_token postrefresh를 이용하여 accesstoken 얻기
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody User request
            ) {
    	try {
    		//아래 처럼 회원가입했다고 토큰을 줄 필요없다. 
    		//AuthenticationResponse are=authService.register( request);
    		//return ResponseEntity.ok(are);
    		authService.register(request);
			return new ResponseEntity<>(new ApiResponse("success","register",200), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("fail","register",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User request
    ) {
    	try {
    		return ResponseEntity.ok(authService.authenticate(request));
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("fail","login",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
    	try {
    		 return  ResponseEntity.ok(authService.refreshToken(request, response)); 
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("fail","refreshToken",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
       
    }
}
/*

handler 처리        
http://localhost:8080/logout
Authorization
Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2Mzk2MjAsImV4cCI6MTczMDcyNjAyMH0.CH4C08C-feolvzGD53UktfG8VIeDFubxY2pJAcKGHZpAUDtgXsyEk_HMkM2qE78V

   
http://localhost:8080/register
{
"firstName":"king",
"lastName":"james",
"username":"james@naver.com",
"password":"123456",
"role":"ADMIN"
}
{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgwNTEsImV4cCI6MTczMDcyNDQ1MX0.iFvKRNT4X84ijteTIfljncpJ8Q9cjkIgdhttWSro9PJa08k1KotnlJawI8VLNTKC",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgwNTEsImV4cCI6MTczMTI0Mjg1MX0.9oWxq-41lHWlm1qVeco-xrFgsSmiXR67yfSPShbCdtxDWQP-z8RarcP86OS_5VG8",
"message": "User registration was successful"
}
http://localhost:8080/register
{
"firstName":"kal",
"lastName":"toad",
"username":"toad@naver.com",
"password":"123456",
"role":"USER"
}
{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0b2FkQG5hdmVyLmNvbSIsImlhdCI6MTczMDYzODI0NSwiZXhwIjoxNzMwNzI0NjQ1fQ.CaJzbKx0o1Mnt9aVIkMVwwJBTEYMJYkS6H5AXtdfJElaASP4wuc981lkNKvGzbPj",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0b2FkQG5hdmVyLmNvbSIsImlhdCI6MTczMDYzODI0NSwiZXhwIjoxNzMxMjQzMDQ1fQ.RXuAZGYHGhXaJEIMmNSL9B_AxUYnTwwCUnAGbjtP0HobrD_N8ZMULptW-UdjnO6Y",
"message": "User registration was successful"
}

http://localhost:8080/login
{
"username":"james@naver.com",
"password":"123456"
}
{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgzNjYsImV4cCI6MTczMDcyNDc2Nn0.OLaeqzeIJW4SLgVb6YZRljyvlIrmyYwu-cbnVrE8eZ6nyXtwDX1iPBvH8PQ7iMgI",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgzNjYsImV4cCI6MTczMTI0MzE2Nn0.llSRhdF7PB2XoxuV5Dahgi7Po-SBSxZ26fL7boyJPg7s2zVB0tRrsq_CkzBNcxSf",
"message": "User login was successful"
}

http://localhost:8080/admin_only
Authorization
Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgwNTEsImV4cCI6MTczMDcyNDQ1MX0.iFvKRNT4X84ijteTIfljncpJ8Q9cjkIgdhttWSro9PJa08k1KotnlJawI8VLNTKC


http://localhost:8080/refresh_token
Authorization
Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzgwNTEsImV4cCI6MTczMDcyNDQ1MX0.iFvKRNT4X84ijteTIfljncpJ8Q9cjkIgdhttWSro9PJa08k1KotnlJawI8VLNTKC

{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzkwMjUsImV4cCI6MTczMDcyNTQyNX0.YZei9gXEAh7sXyVqNeRJTW9B-YnTeSepJIkh0F3MwpKMWJ6RA9AvS_LAP1CxroYD",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2MzkwMjUsImV4cCI6MTczMTI0MzgyNX0.P-ey_52SOFamcqTHQiY3qFp14fa6H0XjATan4El6jPueZyKvylIvaQinmeo94ivE",
"message": "New token generated"
}

Authorization
Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0b2FkQG5hdmVyLmNvbSIsImlhdCI6MTczMDYzODI0NSwiZXhwIjoxNzMwNzI0NjQ1fQ.CaJzbKx0o1Mnt9aVIkMVwwJBTEYMJYkS6H5AXtdfJElaASP4wuc981lkNKvGzbPj

*
*/