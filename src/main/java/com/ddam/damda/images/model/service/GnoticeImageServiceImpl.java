package com.ddam.damda.images.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.GnoticeImage;
import com.ddam.damda.images.model.mapper.GnoticeImageMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GnoticeImageServiceImpl implements GnoticeImageService {
    
    @Autowired
    private GnoticeImageMapper gnoticeImageMapper;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    @PostConstruct  // 서버 시작시 자동 실행
    @Override
    public void init() {
        try {
            String path = uploadDir.replace("file:", "");
            Files.createDirectories(Paths.get(path));
            Files.createDirectories(Paths.get(path, "gnotice"));
            log.info("Gnotice image directory initialized");
        } catch (Exception e) {
            log.error("Failed to create gnotice image directory", e);
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public GnoticeImage findById(int id) {
        return gnoticeImageMapper.selectById(id);
    }

    // 이미지 저장
    @Transactional
    @Override
    public GnoticeImage saveGnoticeImage(MultipartFile file, int gnoticeId) throws IOException {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            
            String datePath = createDateBasedPath();
            String filePath = "gnotice/" + datePath + uniqueFileName;
            
            GnoticeImage gnoticeImage = new GnoticeImage();
            gnoticeImage.setGnoticeId(gnoticeId);
            gnoticeImage.setFileName(uniqueFileName);
            gnoticeImage.setFilePath(filePath);
            gnoticeImage.setFileType(file.getContentType());
            
            // DB 저장
            gnoticeImageMapper.insertGnoticeImage(gnoticeImage);
            
            // 파일 저장
            Path uploadPath = Paths.get(uploadDir.replace("file:", ""), "gnotice", datePath);
            Files.createDirectories(uploadPath);
            
            Path destinationPath = uploadPath.resolve(uniqueFileName);
            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            
            return gnoticeImage;
        } catch (Exception e) {
            log.error("Failed to save gnotice image", e);
            throw e;
        }
    }

    // 게시글의 모든 이미지 조회
    @Transactional
    @Override
    public List<GnoticeImage> findByGnoticeId(int gnoticeId) {
        return gnoticeImageMapper.selectByGnoticeId(gnoticeId);
    }

    // 이미지 삭제
    @Override
    @Transactional
    public void deleteImage(int id) throws IOException {
    	GnoticeImage image = gnoticeImageMapper.selectById(id);
        if (image != null) {
            // DB에서 삭제
        	gnoticeImageMapper.deleteById(id);
            
            // 실제 파일 삭제
            String absolutePath;
            if (uploadDir.startsWith("file:")) {
                absolutePath = uploadDir.substring(5) + File.separator + image.getFilePath();
            } else {
                absolutePath = uploadDir + File.separator + image.getFilePath();
            }
            
            Files.deleteIfExists(Paths.get(absolutePath));
        }
    }

    // 게시글의 모든 이미지 삭제
    @Transactional
    @Override
    public void deleteAllByGnoticeId(int gnoticeId) throws IOException {
        List<GnoticeImage> images = gnoticeImageMapper.selectByGnoticeId(gnoticeId);
        for (GnoticeImage image : images) {
            deleteImage(image.getId());
        }
    }

    // 이미지 바이트 데이터 조회
    @Transactional
    @Override
    public byte[] getImageBytes(int id) throws IOException {
    	GnoticeImage image = gnoticeImageMapper.selectById(id);
        if (image == null) return null;

        String absolutePath;
        if (uploadDir.startsWith("file:")) {
            absolutePath = uploadDir.substring(5) + File.separator + image.getFilePath();
        } else {
            absolutePath = uploadDir + File.separator + image.getFilePath();
        }

        File file = new File(absolutePath);
        if (!file.exists()) return null;

        return Files.readAllBytes(file.toPath());
    }

    private String createDateBasedPath() {
        LocalDateTime now = LocalDateTime.now();
        return String.format("%d/%02d/%02d/", 
            now.getYear(), 
            now.getMonthValue(), 
            now.getDayOfMonth());
    }
    
    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
