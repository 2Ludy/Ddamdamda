package com.ddam.damda.images.model.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.Images;
import com.ddam.damda.images.model.mapper.ImagesMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImagesService {
    private final ImagesMapper imageMapper;
    private final ResourceLoader resourceLoader;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    public ImageServiceImpl(ImagesMapper imageMapper, ResourceLoader resourceLoader) {
        this.imageMapper = imageMapper;
        this.resourceLoader = resourceLoader;
    }
    
    @PostConstruct
    public void init() {
        try {
            String path = uploadDir.replace("file:", "");
            Files.createDirectories(Paths.get(path));
            Files.createDirectories(Paths.get(path, "profile"));
            log.info("Storage directories initialized at: {}", path);
        } catch (IOException e) {
            log.error("Failed to create directories", e);
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    public int saveProfileImage(MultipartFile file) throws IOException {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            String datePath = createDateBasedPath();
            String filePath = String.format("profile/%s%s", datePath, uniqueFileName);
            
            // DB에 저장할 이미지 정보 설정
            Images image = new Images();
            image.setFileName(uniqueFileName);
            image.setFilePath(filePath);
            image.setFileType(file.getContentType());
            
            // 파일 저장 경로 설정
            String basePath = uploadDir.replace("file:", "");
            Path fullPath = Paths.get(basePath, "profile", datePath);
            Files.createDirectories(fullPath);
            
            // 파일 저장
            Path destinationPath = fullPath.resolve(uniqueFileName);
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            // DB 저장
            imageMapper.save(image);
            log.info("Image saved successfully. ID: {}, Path: {}", image.getId(), filePath);
            
            return image.getId();
            
        } catch (IOException e) {
            log.error("Failed to save image file", e);
            throw e;
        }
    }
    
    @Override
    public byte[] getImageBytes(int imageId) throws IOException {
        Images image = imageMapper.findById(imageId);
        if (image == null) {
            log.warn("Image not found with ID: {}", imageId);
            return null;
        }
        
        String basePath = uploadDir.replace("file:", "");
        Path imagePath = Paths.get(basePath, image.getFilePath());
        
        if (!Files.exists(imagePath)) {
            log.warn("Image file not found at path: {}", imagePath);
            return null;
        }
        
        return Files.readAllBytes(imagePath);
    }

    private String createDateBasedPath() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%d/%02d/%02d/", 
            now.getYear(), 
            now.getMonthValue(), 
            now.getDayOfMonth()
        );
    }
    
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public Images findById(int imageId) {
        return imageMapper.findById(imageId);
    }
}