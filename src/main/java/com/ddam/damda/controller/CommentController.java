package com.ddam.damda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.service.CommentService;
import com.ddam.damda.common.util.CPageRequest;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/comment")
//@CrossOrigin("*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Operation(summary = "comment 리스트를 페이지네이션으로 가져오는 메서드", description = "CPageRequest DTO의 boardId, pageNum, pageSize를 세개를 이용하여 특정 boardId의 코멘트 리스트를 페이지 네이션으로 가져옴")
	@PostMapping("/page")
	public ResponseEntity<?> selectAllComment(@RequestBody CPageRequest preq) {
		try {
			PageInfo<Comment> comment = commentService.selectAllComments(preq);
			if(comment != null && !comment.getList().isEmpty()) return new ResponseEntity<PageInfo<Comment>>(comment, HttpStatus.OK
					);
			return new ResponseEntity<>(new ApiResponse("No content", "selectAllComment", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "Comment 상세", description = "Comment의 id를 이용하여 해당 id의 코멘트 정보를 모두 가져오는 메서드")
	@GetMapping("/{id}")
	public ResponseEntity<?> selectComment(@PathVariable int id) {
		try {
			Comment comment = commentService.selectComment(id);
			if(comment != null) return new ResponseEntity<Comment>(comment,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectComment", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "comment 추가", description = "Comment DTO의 userId, content, boardId를 이용하여 특정 board에 Comment를 추가하는 메서드, 실행 시 board테이블의 comments_count가 올라감")
	@PostMapping("")
	public ResponseEntity<?> addComment(@RequestBody Comment comment) {
		try {
			int isS = commentService.insertComment(comment);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "comment 삭제", description = "Comment의 id를 이용하여 comment를 삭제하는 메서드, 실행 시 board테이블의 comments_count가 내려감")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable int id) {
		try {
			int isS = commentService.deleteComment(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "comment 수정", description = "Comment DTO의 content를 이용하여 comment를 수정하는 메서드")
	@PutMapping("")
	public ResponseEntity<?> editComment(@RequestBody Comment comment) {
		try {
			int isS = commentService.updateComment(comment);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "editComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "editComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "editComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
