package com.ddam.damda.controller;

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

import com.ddam.damda.common.util.GCPageRequest;
import com.ddam.damda.group.model.GComment;
import com.ddam.damda.group.model.service.GCommentService;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/gcomment")
//@CrossOrigin("*")
public class GCommentController {
	
	@Autowired
	private GCommentService gCommentService;
	
	@Operation(summary = "GComment 리스트를 페이지네이션으로 가져오는 메서드", description = "GCPageRequest DTO의 gnoticeId, pageNum, pageSize를 세개를 이용하여 특정 gnoticeId의 코멘트 리스트를 페이지 네이션으로 가져옴")
	@PostMapping("/page")
	public ResponseEntity<?> selectAllGComment(@RequestBody GCPageRequest preq) {
		try {
			PageInfo<GComment> gComment = gCommentService.selectAllGComments(preq);
			if(gComment != null && !gComment.getList().isEmpty()) return new ResponseEntity<PageInfo<GComment>>(gComment, HttpStatus.OK
					);
			return new ResponseEntity<>(new ApiResponse("No content", "selectAllGComment", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllGComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GComment 상세", description = "GComment의 id를 이용하여 해당 id의 코멘트 정보를 모두 가져오는 메서드")
	@GetMapping("/{id}")
	public ResponseEntity<?> selectGComment(@PathVariable int id) {
		try {
			GComment gComment = gCommentService.selectGComment(id);
			if(gComment != null) return new ResponseEntity<GComment>(gComment,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectGComment", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectGComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GComment 추가", description = "GComment DTO의 userId, content, gnoticeId를 이용하여 특정 groupNotice에 gComment를 추가하는 메서드")
	@PostMapping("")
	public ResponseEntity<?> addGComment(@RequestBody GComment gComment) {
		try {
			int isS = gCommentService.insertGComment(gComment);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addGComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addGComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addGComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GComment 삭제", description = "GComment의 id를 이용하여 gComment를 삭제하는 메서드")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGComment(@PathVariable int id) {
		try {
			int isS = gCommentService.deleteGComment(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteGComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteGComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteGComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GComment 수정", description = "GComment DTO의 id, content를 이용하여 gComment를 수정하는 메서드")
	@PutMapping("")
	public ResponseEntity<?> editGComment(@RequestBody GComment gComment) {
		try {
			int isS = gCommentService.updateGComment(gComment);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "editGComment", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "editGComment", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "editGComment", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
