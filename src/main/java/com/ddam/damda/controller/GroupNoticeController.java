package com.ddam.damda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
            PageInfo<GroupNotice> page = groupNoticeService.selectAllGroupNotice(preq);
            if (page != null && !page.getList().isEmpty()) {
                return ResponseEntity.ok(page);
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "공지사항이 없습니다.", 400));
        } catch (Exception e) {
            log.error("공지사항 목록 조회 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "GroupNotice 상세보기", description = "gnoticeId 를 이용하여 해당 GroupNotice의 모든 정보를 불러오는 메서드")
	@GetMapping("/{gnoticeId}")
    public ResponseEntity<?> selectGroupNotice(@PathVariable int gnoticeId) {
        try {
            GroupNotice notice = groupNoticeService.selectGroupNotice(gnoticeId);
            if (notice != null) {
                return ResponseEntity.ok(notice);
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "공지사항을 찾을 수 없습니다.", 400));
        } catch (Exception e) {
            log.error("공지사항 조회 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "GroupNotice 최신글 가져오기", description = "groupId 를 이용하여 해당 그룹의 최신 GroupNotice의 제목을 String으로 불러오는 메서드")
	@GetMapping("/latest/{gnoticeId}")
    public ResponseEntity<?> selectLatestGroupNotice(@PathVariable int groupId) {
        try {
            String title = groupNoticeService.selectLatestGroupNotice(groupId);
            if (title != null) {
                return ResponseEntity.ok(title);
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "공지사항을 찾을 수 없습니다.", 400));
        } catch (Exception e) {
            log.error("공지사항 조회 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "GroupNotice 추가", description = "GroupNotice DTO의 groupId, title, content 를 이용하여 GroupNotice를 추가하는 메서드")
	@PostMapping("")
	   public ResponseEntity<?> addGroupNotice(
	            @RequestPart("groupNotice") GroupNotice groupNotice,
	            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
	        try {
	            int result = groupNoticeService.insertGroupNotice(groupNotice, images);
	            if (result > 0) {
	                return ResponseEntity.ok()
	                    .body(new ApiResponse("success", "공지사항이 등록되었습니다.", 200));
	            }
	            return ResponseEntity.badRequest()
	                .body(new ApiResponse("fail", "공지사항 등록에 실패했습니다.", 400));
	        } catch (Exception e) {
	            log.error("공지사항 등록 실패", e);
	            return ResponseEntity.internalServerError()
	                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
	        }
	    }
	
	@Operation(summary = "GroupNotice 삭제", description = "gnoticeId 를 이용하여 해당 GroupNotice를 삭제하는 메서드")
	@DeleteMapping("/{gnoticeId}")
	   public ResponseEntity<?> deleteGroupNotice(@PathVariable int gnoticeId) {
        try {
            int result = groupNoticeService.deleteGroupNotice(gnoticeId);
            if (result > 0) {
                return ResponseEntity.ok()
                    .body(new ApiResponse("success", "공지사항이 삭제되었습니다.", 200));
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "공지사항 삭제에 실패했습니다.", 400));
        } catch (Exception e) {
            log.error("공지사항 삭제 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
	
	@Operation(summary = "GroupNotice 수정", description = "GroupNotice DTO의 title, content, gnoticeId 를 이용하여 GroupNotice를 수정하는 메서드")
	@PutMapping("")
    public ResponseEntity<?> editGroupNotice(
            @RequestPart("groupNotice") GroupNotice groupNotice,
            @RequestPart(value = "images", required = false) List<MultipartFile> newImages,
            @RequestPart(value = "deleteImageIds", required = false) List<Integer> deleteImageIds) {
        try {
            int result = groupNoticeService.updateGroupNotice(groupNotice, newImages, deleteImageIds);
            if (result > 0) {
                return ResponseEntity.ok()
                    .body(new ApiResponse("success", "공지사항이 수정되었습니다.", 200));
            }
            return ResponseEntity.badRequest()
                .body(new ApiResponse("fail", "공지사항 수정에 실패했습니다.", 400));
        } catch (Exception e) {
            log.error("공지사항 수정 실패", e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse("error", "서버 오류가 발생했습니다.", 500));
        }
    }
}
