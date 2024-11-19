package com.ddam.damda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
//@CrossOrigin("*")
public class DemoController {
	
	/*
	 *  이 쪽은 user의 role을 다루는 페이지로 없어도 무방한 페이지 or 수정해서 관리자 페이지 만들 수도..??
	 */
    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
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