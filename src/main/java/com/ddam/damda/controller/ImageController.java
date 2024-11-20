package com.ddam.damda.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.Images;
import com.ddam.damda.images.model.service.ImagesService;
import com.ddam.damda.jwt.model.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/images")
public class ImageController {
    
    private final ImagesService imageService;
    
    public ImageController(ImagesService imageService) {
        this.imageService = imageService;
    }
    
    @Operation(summary = "file 업로드", description = "user, groupInfo 이미지 업로드")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse("fail", "파일이 비어있습니다", 400));
            }
            
            int imageId = imageService.saveProfileImage(file);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("success", 
                    String.format("%d번 이미지가 업로드되었습니다", imageId), 201));
            
        } catch (IOException e) {
            log.error("이미지 업로드 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("error", "서버 내부 에러 발생", 500));
        }
    }
    
    @Operation(summary = "이미지 조회", description = "이미지 ID로 이미지 조회")
    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable int imageId) {
        try {
            byte[] imageBytes = imageService.getImageBytes(imageId);
            if (imageBytes == null) {
                return ResponseEntity.notFound().build();
            }
            
            Images image = imageService.findById(imageId);
            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getFileType()))
                .body(imageBytes);
                
        } catch (IOException e) {
            log.error("이미지 조회 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("error", "이미지 조회 실패", 500));
        }
    }
}
