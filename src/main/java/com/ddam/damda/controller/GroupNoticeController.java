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

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.group.model.service.GroupNoticeService;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/groupnotice")
//@CrossOrigin("*")
public class GroupNoticeController {
	
	@Autowired
	private GroupNoticeService groupNoticeService;
	
	@Operation(summary = "모든 GroupNotice를 페이지네이션을 이용하여 조회", description = "GNPageRequest DTO의 pageNum, pageSize를 이용해 페이지네이션으로 조회하며, search가 null이 아니라면 제목||내용에 search가 있다면 조회, groupId로 어떤 그룹인지 판별하는 메서드")
	@PostMapping("/page")
	public ResponseEntity<?> selectAllGroupNotice(@RequestBody GNPageRequest preq) {
		try {
			PageInfo<GroupNotice> groupNotice = groupNoticeService.selectAllGroupNotice(preq);
			if(groupNotice != null && !groupNotice.getList().isEmpty()) return new ResponseEntity<PageInfo<GroupNotice>>(groupNotice, HttpStatus.OK
					);
			return new ResponseEntity<>(new ApiResponse("No content", "selectAllGroupNotice", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllGroupNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupNotice 상세보기", description = "gnoticeId 를 이용하여 해당 GroupNotice의 모든 정보를 불러오는 메서드")
	@GetMapping("/{gnoticeId}")
	public ResponseEntity<?> selectGroupNotice(@PathVariable int gnoticeId) {
		try {
			GroupNotice groupNotice = groupNoticeService.selectGroupNotice(gnoticeId);
			if(groupNotice != null) return new ResponseEntity<GroupNotice>(groupNotice,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectGroupNotice", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectGroupNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupNotice 추가", description = "GroupNotice DTO의 groupId, title, content 를 이용하여 GroupNotice를 추가하는 메서드")
	@PostMapping("")
	public ResponseEntity<?> addGroupNotice(@RequestBody GroupNotice groupNotice) {
		try {
			int isS = groupNoticeService.insertGroupNotice(groupNotice);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addGroupNotice", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addGroupNotice", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addGroupNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupNotice 삭제", description = "gnoticeId 를 이용하여 해당 GroupNotice를 삭제하는 메서드")
	@DeleteMapping("/{gnoticeId}")
	public ResponseEntity<?> deleteGroupNotice(@PathVariable int gnoticeId) {
		try {
			int isS = groupNoticeService.deleteGroupNotice(gnoticeId);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteGroupNotice", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteGroupNotice", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteGroupNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupNotice 수정", description = "GroupNotice DTO의 title, content, gnoticeId 를 이용하여 GroupNotice를 수정하는 메서드")
	@PutMapping("")
	public ResponseEntity<?> editGroupNotice(@RequestBody GroupNotice groupNotice) {
		try {
			int isS = groupNoticeService.updateGroupNotice(groupNotice);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "editGroupNotice", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "editGroupNotice", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "editGroupNotice", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
