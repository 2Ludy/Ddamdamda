package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.Likes;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/notice")
//@CrossOrigin("*")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Operation(summary = "특정 user의 Notice 리스트 조회", description = "userId를 이용하여 해당 id의 알림 정보를 모두 가져오는 메서드")
	@GetMapping("/{userId}")
	public ResponseEntity<?> selectAllNotice(@PathVariable int userId) {
		try {
			List<Notice> notices = noticeService.selectAllNotice(userId);
			if(notices.size() > 0) return new ResponseEntity<List<Notice>>(notices,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectAllNotice", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "해당 Notice가 가리키는 객체 정보 조회", description = "userId를 이용하여 해당 id의 알림 정보를 모두 가져오는 메서드")
	@PostMapping("/info")
	public ResponseEntity<?> selectNoticeInfo(@RequestBody Notice notice) {
		try {
			String referenceType = notice.getReferenceType();
			int referenceId = notice.getReferenceId();
			if(referenceType.equals("like")) {
				Likes likes = noticeService.selectLikesNotice(referenceId);
				return new ResponseEntity<Likes>(likes, HttpStatus.OK); 
			}else if(referenceType.equals("comment")) {
				Comment comment = noticeService.selectCommentNotice(referenceId);
				return new ResponseEntity<Comment>(comment, HttpStatus.OK);
			}else if(referenceType.equals("group_notice")) {
				GroupNotice groupNotice = noticeService.selectGroupNotice(referenceId);
				return new ResponseEntity<GroupNotice>(groupNotice, HttpStatus.OK);
			}else if(referenceType.equals("group_member")) {
				return new ResponseEntity<>(new ApiResponse("Group Member", "selectNoticeInfo", 200), HttpStatus.OK);
			}
			
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectNoticeInfo", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "해당 Notice를 읽음처리", description = "id를 이용하여 해당 알림을 읽음 처리 하는 메서드")
	@PutMapping("/{id}")
	public ResponseEntity<?> readNotice(@PathVariable int id) {
		try {
			int isS = noticeService.updateNoticeRead(id);
			if(isS > 0) return new ResponseEntity<>(new ApiResponse("Success", "readNotice", 200), HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Fail", "readNotice", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "readNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "해당 Notice를 삭제처리", description = "id를 이용하여 해당 알림을 삭제 처리 하는 메서드")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteNotice(@PathVariable int id) {
		try {
			int isS = noticeService.deleteNotice(id);
			if(isS > 0) return new ResponseEntity<>(new ApiResponse("Success", "deleteNotice", 200), HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Fail", "deleteNotice", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
