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

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.service.GroupInfoService;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/groupinfo")
@CrossOrigin("*")
public class GroupInfoController {
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	@PostMapping("/page")
	public ResponseEntity<?> selectAllGroupInfos(@RequestBody GPageRequest preq) {
		try {
			PageInfo<GroupInfo> groupInfo = groupInfoService.selectAllGroupInfos(preq);
			if(groupInfo != null && !groupInfo.getList().isEmpty()) return new ResponseEntity<PageInfo<GroupInfo>>(groupInfo, HttpStatus.OK
					);
			return new ResponseEntity<>(new ApiResponse("No content", "selectAllGroupInfos", 400), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectAllGroupInfos", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> selectGroupInfo(@PathVariable int id) {
		try {
			GroupInfo groupInfo = groupInfoService.selectGroupInfo(id);
			if(groupInfo != null) return new ResponseEntity<GroupInfo>(groupInfo,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectGroupInfo", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "selectGroupInfo", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> addGroupInfo(@RequestBody GroupInfo groupInfo) {
		try {
			int isS = groupInfoService.insertGroupInfo(groupInfo);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "addGroupInfo", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "addGroupInfo", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "addGroupInfo", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGroupInfo(@PathVariable int id) {
		try {
			int isS = groupInfoService.deleteGroupInfo(id);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "deleteGroupInfo", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "deleteGroupInfo", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "deleteGroupInfo", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> editGroupInfo(@RequestBody GroupInfo groupInfo) {
		try {
			int isS = groupInfoService.updateGroupInfo(groupInfo);
			if(isS > 0) {
				return new ResponseEntity<>(new ApiResponse("Success", "editGroupInfo", 200), HttpStatus.OK);
			}
			return new ResponseEntity<>(new ApiResponse("fail", "editGroupInfo", 400), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiResponse("Error", "editGroupInfo", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
