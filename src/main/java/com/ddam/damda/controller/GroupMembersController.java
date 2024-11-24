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

import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.GroupMembers;
import com.ddam.damda.group.model.service.GroupMembersService;
import com.ddam.damda.jwt.model.ApiResponse;
import com.ddam.damda.user.model.User;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/groupmembers")
//@CrossOrigin("*")
public class GroupMembersController {
	
	@Autowired
	private GroupMembersService groupMembersService;
	
	@Operation(summary = "특정 그룹의 그룹원 리스트 보기", description = "groupId를 이용하여 USER TABLE 에 IN 하여 특정 그룹의 그룹원들의 정보를 가져오는 메서드 List<User> 를 리턴해용~~")
	@GetMapping("/{groupId}")
	public ResponseEntity<?> selectAllGroupMembers(@PathVariable int groupId) {
		try {
			List<User> users = groupMembersService.selectAllGroupMembers(groupId);
			if(users.size() != 0) return new ResponseEntity<List<User>>(users,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("fail", "selectAllGroupMembers", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllGroupMembers", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "특정 그룹의 그룹원의 가입 날짜 보기", description = "GroupMembers의 userId와 groupId를 이용하여 createdAt을 반환")
	@PostMapping("/{groupId}")
	public ResponseEntity<?> selectGroupMembersCreated(@RequestBody GroupMembers groupMembers) {
		try {
			String createdAt = groupMembersService.selectAllGroupMembers(groupMembers);
			if(createdAt != null) return new ResponseEntity<String>(createdAt,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("fail", "selectAllGroupMembers", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllGroupMembers", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupMembers 추가", description = "GroupMembers DTO의 groupId, userId 를 이용하여 그룹 멤버를 추가하는 메서드, group_info 테이블에 member_count 가 증가됨")
	@PostMapping("")
	public ResponseEntity<?> addGroupMembers(@RequestBody GroupMembers groupMembers) {
		try {
			int isS = groupMembersService.insertGroupMembers(groupMembers);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addGroupMembers", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addGroupMembers", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addGroupMembers", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupMembers 삭제, 거의 이거 쓰면 됩니다", description = "GroupMembers DTO의 groupId, userId 를 이용하여 그룹 멤버를 삭제하는 메서드, group_info 테이블에 member_count 가 감소됨")
	@DeleteMapping("")
	public ResponseEntity<?> deleteGroupMembers(@RequestBody GroupMembers groupMembers) {
		try {
			int isS = groupMembersService.deleteGroupMembers(groupMembers);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteGroupMembers", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteGroupMembers", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteGroupMembers", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Operation(summary = "GroupMembers 삭제, id를 이용한건데 음.. 임시로 만든거라 거의 안쓸겁니다", description = "id를 이용하여 GroupMembers를 삭제하는 메서드, group_info 테이블에 member_count 가 감소됨")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGroupMembersById(@PathVariable int id) {
		try {
			int isS = groupMembersService.deleteGroupMembersById(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteGroupMembersById", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteGroupMembersById", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteGroupMembersById", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
