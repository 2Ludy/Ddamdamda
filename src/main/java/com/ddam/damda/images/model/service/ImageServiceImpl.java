package com.ddam.damda.images.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.Images;
import com.ddam.damda.images.model.mapper.ImagesMapper;

@Service
public class ImageServiceImpl implements ImagesService {
    private final ImagesMapper imageMapper;
    private final ResourceLoader resourceLoader;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    public ImageServiceImpl(ImagesMapper imageMapper, ResourceLoader resourceLoader) {
        this.imageMapper = imageMapper;
        this.resourceLoader = resourceLoader;
    }
    
    public void init() {
        try {
            // uploadDir에서 "file:" 접두사 제거
            String path = uploadDir.replace("file:", "");
            File directory = new File(path);
            
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    System.out.println("Directory created: " + directory.getAbsolutePath());
                } else {
                    System.out.println("Failed to create directory: " + directory.getAbsolutePath());
                }
            }
            
            // profile 디렉토리도 생성
            File profileDir = new File(directory, "profile");
            if (!profileDir.exists()) {
                profileDir.mkdirs();
            }
            
        } catch (Exception e) {
            System.err.println("Error creating directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int saveProfileImage(MultipartFile file) throws IOException {
        // 파일 정보 생성
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        
        // UUID를 사용하여 고유한 파일명 생성
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // 연/월/일 기반으로 디렉토리 구조 생성
        String datePath = createDateBasedPath();
        
        // 최종 파일 경로 설정
        String filePath = "profile/" + datePath + uniqueFileName;
        
        // Image 엔티티 생성 및 설정
        Images image = new Images();
        image.setFileName(uniqueFileName);
        image.setFilePath(filePath);
        image.setFileType(file.getContentType());
        
        // DB에 저장
        imageMapper.save(image);
        
        // 파일 저장을 위한 디렉토리 생성
        Resource resource = resourceLoader.getResource(uploadDir);
        String basePath = resource.getFile().getAbsolutePath();
        File profileDir = new File(basePath + "/profile/" + datePath);
        if (!profileDir.exists()) {
            profileDir.mkdirs();
        }
        
        // 파일 저장
        Path path = Paths.get(profileDir.getAbsolutePath(), uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
        return image.getId();
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
    
    public byte[] getImageBytes(int imageId) throws IOException {
        try {
            Images image = imageMapper.findById(imageId);
            
            // 이미지 객체 자체가 null인지 확인
            if (image == null) {
                System.out.println("Image object is null for ID: " + imageId);
                return null;
            }
            
            System.out.println("===== Path Information =====");
            System.out.println("Upload Dir: " + uploadDir);
            System.out.println("DB File Path: " + image.getFilePath());
            
            // 절대 경로 생성
            String absolutePath;
            if (uploadDir.startsWith("file:")) {
                absolutePath = uploadDir.substring(5) + File.separator + image.getFilePath();
            } else {
                absolutePath = uploadDir + File.separator + image.getFilePath();
            }
            
            // 경로 구분자 변환
            absolutePath = absolutePath.replace('/', File.separatorChar);
            System.out.println("Absolute Path: " + absolutePath);
            
            File file = new File(absolutePath);
            System.out.println("File exists: " + file.exists());
            System.out.println("File absolute path: " + file.getAbsolutePath());
            System.out.println("========================");
            
            if (!file.exists()) {
                System.out.println("File not found at: " + file.getAbsolutePath());
                return null;
            }
            
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            System.err.println("Error reading image: " + e.getMessage());
            e.printStackTrace();
            throw new IOException("Failed to read image file", e);
        }
    }

	@Override
	public Images findById(int imageId) {
		return imageMapper.findById(imageId);
	}
}