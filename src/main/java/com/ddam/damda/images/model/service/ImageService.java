package com.ddam.damda.images.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.Image;
import com.ddam.damda.images.model.mapper.ImageMapper;

import jakarta.annotation.PostConstruct;

@Service
public class ImageService {
    private final ImageMapper imageMapper;
    private final ResourceLoader resourceLoader;

    @Value("${file.upload.directory}")
    private String uploadDir;

    public ImageService(ImageMapper imageMapper, ResourceLoader resourceLoader) {
        this.imageMapper = imageMapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        Resource resource = resourceLoader.getResource(uploadDir);
        File directory = new File(resource.getFile().getAbsolutePath());
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public int saveProfileImage(MultipartFile file) throws IOException {
        // 먼저 DB에 저장하여 ID 받기
        Image image = new Image();
        image.setFileType(file.getContentType());
        imageMapper.save(image);  // auto increment ID 생성
        
        // 생성된 ID를 파일명으로 사용
        String fileName = image.getId() + getFileExtension(file.getOriginalFilename());
        
        // 리소스 경로 가져오기
        Resource resource = resourceLoader.getResource(uploadDir);
        String basePath = resource.getFile().getAbsolutePath();
        
        // 프로필 경로 생성
        File profileDir = new File(basePath + "/profile");
        if (!profileDir.exists()) {
            profileDir.mkdirs();
        }

        // 파일 저장 경로 설정 및 저장
        String filePath = "profile/" + fileName;
        Path path = Paths.get(profileDir.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // DB 업데이트
        image.setFilePath(filePath);
        image.setFileName(fileName);
        imageMapper.updateFileInfo(image);

        return image.getId();
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public byte[] getImageBytes(int imageId) throws IOException {
        Image image = imageMapper.findById(imageId);
        if (image == null) return null;

        Resource resource = resourceLoader.getResource(uploadDir);
        String basePath = resource.getFile().getAbsolutePath();
        Path imagePath = Paths.get(basePath, image.getFilePath());
        
        return Files.readAllBytes(imagePath);
    }
}
