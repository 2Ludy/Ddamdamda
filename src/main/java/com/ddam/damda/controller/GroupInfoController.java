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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.service.GroupInfoService;
import com.ddam.damda.jwt.model.ApiResponse;
import com.github.pagehelper.PageInfo;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/groupinfo")
//@CrossOrigin("*")
public class GroupInfoController {
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	@Operation(summary = "모든 GroupInfo를 페이지네이션을 이용하여 조회", description = "GPageRequest DTO의 pageNum, pageSize를 이용해 페이지네이션으로 조회하며, search가 null이 아니라면 제목||개요에 search가 들어가면 조회, mateStatus가 null이 아니라면 모집중||마감 상태 조회하는 메서드")
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
	
	@Operation(summary = "GroupInfo 상세보기", description = "id를 이용하여 해당 GroupInfo의 모든 정보를 불러오는 메서드")
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
	
	@Operation(summary = "GroupInfo 추가", description = "GroupInfo DTO의 groupName, description, adminId, groupImg, region, exerciseType, memberCount 를 이용하여 GroupInfo를 추가하는 메서드")
	@PostMapping("")
    public ResponseEntity<?> addGroupInfo(
            @RequestPart("groupInfo") GroupInfo groupInfo,
            @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            // 이미지 파일 유효성 검사
            if (imageFile.isEmpty()) {
                return new ResponseEntity<>(
                    new ApiResponse("fail", "그룹 이미지는 필수입니다.", 400), 
                    HttpStatus.BAD_REQUEST
                );
            }
            
            int isS = groupInfoService.insertGroupInfo(groupInfo, imageFile);
            if(isS > 0) {
                return new ResponseEntity<>(
                    new ApiResponse("success", "그룹이 생성되었습니다.", 200), 
                    HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                new ApiResponse("fail", "그룹 생성에 실패했습니다.", 400), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            log.error("그룹 생성 실패", e);
            return new ResponseEntity<>(
                new ApiResponse("error", "서버 오류가 발생했습니다.", 500), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }	
	
	@Operation(summary = "GroupInfo 삭제", description = "id를 이용하여 해당 GroupInfo를 삭제하는 메서드")
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
	
	@Operation(summary = "GroupInfo 수정", description = "GroupInfo DTO의 groupName, description, groupImg, mateStatus, region, exerciseType, memberCount 를 이용하여 GroupInfo를 수정하는 메서드, current_members도 해당 객체에 포함되어함!!!! 현재인원/최대인원의 로직을 판단함")
	@PutMapping("")
    public ResponseEntity<?> editGroupInfo(
            @RequestPart("groupInfo") GroupInfo groupInfo,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            int isS = groupInfoService.updateGroupInfo(groupInfo, imageFile);
            if(isS > 0) {
                return new ResponseEntity<>(
                    new ApiResponse("success", "그룹 정보가 수정되었습니다.", 200), 
                    HttpStatus.OK
                );
            }
            return new ResponseEntity<>(
                new ApiResponse("fail", "그룹 수정에 실패했습니다.", 400), 
                HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            log.error("그룹 수정 실패", e);
            return new ResponseEntity<>(
                new ApiResponse("error", "서버 오류가 발생했습니다.", 500), 
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
	
	@Operation(summary = "특정 User가 참여하는 GroupInfo 리스트 보기", description = "userId를 이용하여 해당 유저가 참여하고 있는 GroupInfo 리스트를 모두 가져오는 메서드")
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> selectUserGroupInfo(@PathVariable int userId) {
		try {
			List<GroupInfo> groupInfos = groupInfoService.selectUserGroupInfos(userId);
			if(groupInfos.size() != 0) return new ResponseEntity<List<GroupInfo>>(groupInfos,HttpStatus.OK);
			return new ResponseEntity<>(new ApiResponse("Not Found", "selectUserGroupInfo", 400), HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ApiResponse("Error", "selectUserGroupInfo", 500), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
