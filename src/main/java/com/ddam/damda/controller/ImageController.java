package com.ddam.damda.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/images")
public class ImageController {
    
	@Autowired
    private ImagesService imageService;
    
    @PostMapping("/profile")
    public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file) {
        try {
            // 파일 유효성 검사
            if (file.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse("fail", "파일이 비어있습니다", 400), HttpStatus.BAD_REQUEST);
            }
            
            // 이미지 파일 타입 검사
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
            	 return new ResponseEntity<>(new ApiResponse("fail", "이미지 파일만 업로드 가능합니다", 400), HttpStatus.BAD_REQUEST);
            }
            
            // 이미지 저장
            int imageId = imageService.saveProfileImage(file);
            
            return new ResponseEntity<>(new ApiResponse("success", imageId + "번 이미지가 성공적으로 업로드되었습니다", 201),HttpStatus.CREATED);
            
        } catch (IOException e) {
            return new ResponseEntity<>(new ApiResponse("error", "서버 내부 에러 발생", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(@PathVariable int imageId) {
        try {
            Images image = imageService.findById(imageId);
            if (image == null) {
                return ResponseEntity.notFound().build();
            }
            
            byte[] imageBytes = imageService.getImageBytes(imageId);
            if (imageBytes == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getFileType()))
                .body(imageBytes);
                
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to retrieve image: " + e.getMessage());
        }
    }
}
