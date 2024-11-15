package com.ddam.damda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.jwt.model.User;
import com.ddam.damda.jwt.model.service.UserService;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;

    @GetMapping("/user/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String username) {
    	try {
    		User user = userService.findByUsername(username).get();
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("fail","getUser",500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
/*


http://localhost:8080/login
{
"username":"james@naver.com",
"password":"123456"
}
{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2Mzk2MjAsImV4cCI6MTczMDcyNjAyMH0.CH4C08C-feolvzGD53UktfG8VIeDFubxY2pJAcKGHZpAUDtgXsyEk_HMkM2qE78V",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2Mzk2MjAsImV4cCI6MTczMTI0NDQyMH0.nrdjkoRcwaQbd_HLyjObYNfKMC2d8Vmlu8Vcpoyeuoha3L-8TU2fx7W7TVtweBJt",
"message": "User login was successful"
}

http://localhost:8080/admin_only
Authorization
Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2Mzk2MjAsImV4cCI6MTczMDcyNjAyMH0.CH4C08C-feolvzGD53UktfG8VIeDFubxY2pJAcKGHZpAUDtgXsyEk_HMkM2qE78V


{
"access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2NDAxMTQsImV4cCI6MTczMDcyNjUxNH0.O4nzjPJrNWUdbWu_CPXrMzx5mIYHdBikkkpaNbmhXlV1hDcOYuxt3_DZaslGkmk_",
"refresh_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJqYW1lc0BuYXZlci5jb20iLCJpYXQiOjE3MzA2NDAxMTQsImV4cCI6MTczMTI0NDkxNH0.Vl90L8yxK89l-OZeWQKBxZAKWwLyi05DFjm5zJvRuR3EHnlkNm6uIwd7pla1Oe5u",
"message": "User login was successful"
}

*/