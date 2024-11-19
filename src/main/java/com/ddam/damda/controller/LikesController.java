package com.ddam.damda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.Likes;
import com.ddam.damda.board.model.service.LikesService;
import com.ddam.damda.jwt.model.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/likes")
//@CrossOrigin("*")
public class LikesController {
	
	@Autowired
	private LikesService likesService;
	
	@Operation(summary = "사용자가 특정 게시물에 Likes를 했는지 안했는지 판단하는 메서드", description = "Likes DTO의 userId, boardId 두개를 이용하여 해당 게시물에 로그인된 유저가 좋아요를 했는지 안했는지를 판단하기 위한 메서드")
	@GetMapping("")
	public ResponseEntity<?> haveLikes(@RequestBody Likes request) { // userId, boardId만 사용
		try {
			Likes likes = likesService.haveLikes(request);
			if(likes != null) return new ResponseEntity<Boolean>(true,HttpStatus.OK);
			return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "haveLikes", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Likes 추가", description = "Likes DTO의 userId, boardId를 이용하여 해당 게시물에 어떤 user가 좋아요를 했을때 좋아요를 추가하는 메서드, 실행 시 board테이블의 likes_count가 올라감")
	@PostMapping("")
	public ResponseEntity<?> addLikes(@RequestBody Likes likes) { // userId, boardId만 사용
		try {
			int isS = likesService.insertLikes(likes);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addLikes", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addLikes", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addLikes", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Likes 삭제", description = "Likes DTO의 userId, boardId를 이용하여 해당 좋아요가 존재한다면 그 id를 이용하여 좋아요를 삭제하는 메서드, 실행 시 board테이블의 likes_count가 내려감")
	@DeleteMapping("")
	public ResponseEntity<?> deleteLikes(@RequestBody Likes request) { // userId, boardId만 사용
		try {
			Likes likes = likesService.haveLikes(request);
			int id = likes.getId();
			int isS = likesService.deleteLikes(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteLikes", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteLikes", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteLikes", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
