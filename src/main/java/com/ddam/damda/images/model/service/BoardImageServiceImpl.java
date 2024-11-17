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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.BoardImage;
import com.ddam.damda.images.model.mapper.BoardImageMapper;

@Service
public class BoardImageServiceImpl implements BoardImageService {
    
    @Autowired
    private BoardImageMapper boardImageMapper;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    @Autowired
    private ResourceLoader resourceLoader;
    
    // 초기화 (디렉토리 생성)
    @Override
    public void init() {
        try {
            String path = uploadDir.replace("file:", "");
            File directory = new File(path);
            
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // board 이미지용 디렉토리 생성
            File boardDir = new File(directory, "board");
            if (!boardDir.exists()) {
                boardDir.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Error creating directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 이미지 저장
    @Transactional
    @Override
    public BoardImage saveBoardImage(MultipartFile file, int boardId) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        // 날짜 기반 경로 생성
        String datePath = createDateBasedPath();
        String filePath = "board/" + datePath + uniqueFileName;
        
        // BoardImage 엔티티 생성
        BoardImage boardImage = new BoardImage();
        boardImage.setBoardId(boardId);
        boardImage.setFileName(uniqueFileName);
        boardImage.setFilePath(filePath);
        boardImage.setFileType(file.getContentType());
        
        // DB 저장
        boardImageMapper.insertBoardImage(boardImage);
        
        // 실제 파일 저장
        Resource resource = resourceLoader.getResource(uploadDir);
        String basePath = resource.getFile().getAbsolutePath();
        File boardDir = new File(basePath + "/board/" + datePath);
        if (!boardDir.exists()) {
            boardDir.mkdirs();
        }
        
        Path path = Paths.get(boardDir.getAbsolutePath(), uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        
        return boardImage;
    }

    // 게시글의 모든 이미지 조회
    @Transactional
    @Override
    public List<BoardImage> findByBoardId(int boardId) {
        return boardImageMapper.selectByBoardId(boardId);
    }

    // 이미지 삭제
    @Override
    @Transactional
    public void deleteImage(int id) throws IOException {
        BoardImage image = boardImageMapper.selectById(id);
        if (image != null) {
            // DB에서 삭제
            boardImageMapper.deleteById(id);
            
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
    public void deleteAllByBoardId(int boardId) throws IOException {
        List<BoardImage> images = boardImageMapper.selectByBoardId(boardId);
        for (BoardImage image : images) {
            deleteImage(image.getId());
        }
    }

    // 이미지 바이트 데이터 조회
    @Transactional
    @Override
    public byte[] getImageBytes(int id) throws IOException {
        BoardImage image = boardImageMapper.selectById(id);
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
