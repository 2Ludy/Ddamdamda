package com.ddam.damda.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddam.damda.images.model.GnoticeImage;
import com.ddam.damda.images.model.service.GnoticeImageService;
import com.ddam.damda.jwt.model.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/gnoticeimage")
public class GnoticeImageController {
    
    private final GnoticeImageService gnoticeImageService;
    
    public GnoticeImageController(GnoticeImageService gnoticeImageService) {
        this.gnoticeImageService = gnoticeImageService;
    }
    
    @Operation(summary = "이미지 조회", description = "이미지 ID로 이미지 파일 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        try {
            byte[] imageBytes = gnoticeImageService.getImageBytes(id);
            if (imageBytes == null) {
                return ResponseEntity.notFound().build();
            }
            
            GnoticeImage image = gnoticeImageService.findById(id);
            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getFileType()))
                .body(imageBytes);
                
        } catch (IOException e) {
            log.error("이미지 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("error", "이미지 조회 실패", 500));
        }
    }
    
    @Operation(summary = "이미지 삭제", description = "이미지 ID로 이미지 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable int id) {
        try {
            gnoticeImageService.deleteImage(id);
            return ResponseEntity.ok()
                .body(new ApiResponse("success", "이미지가 삭제되었습니다.", 200));
        } catch (IOException e) {
            log.error("이미지 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("error", "이미지 삭제 실패", 500));
        }
    }

    @Operation(summary = "공지사항의 이미지 목록 조회", description = "공지사항 ID로 해당 공지사항의 이미지 목록 조회")
    @GetMapping("/gnotice/{gnoticeId}")
    public ResponseEntity<?> getGnoticeImages(@PathVariable int gnoticeId) {
        try {
            List<GnoticeImage> images = gnoticeImageService.findByGnoticeId(gnoticeId);
            return ResponseEntity.ok(images);
        } catch (Exception e) {
            log.error("공지사항 이미지 목록 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("error", "이미지 목록 조회 실패", 500));
        }
    }
}
