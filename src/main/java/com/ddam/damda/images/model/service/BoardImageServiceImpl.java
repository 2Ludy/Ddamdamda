package com.ddam.damda.images.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.BoardImage;
import com.ddam.damda.images.model.mapper.BoardImageMapper;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardImageServiceImpl implements BoardImageService {
    
    private final BoardImageMapper boardImageMapper;
    
    @Value("${file.upload.directory}")
    private String uploadDir;
    
    public BoardImageServiceImpl(BoardImageMapper boardImageMapper) {
        this.boardImageMapper = boardImageMapper;
    }
    
    @PostConstruct
    public void init() {
        try {
            String path = uploadDir.replace("file:", "");
            Files.createDirectories(Paths.get(path));
            Files.createDirectories(Paths.get(path, "board"));
            log.info("Board image directory initialized");
        } catch (Exception e) {
            log.error("Failed to create board image directory", e);
            throw new RuntimeException("Could not initialize storage", e);
        }
    }

    @Override
    @Transactional
    public BoardImage saveBoardImage(MultipartFile file, int boardId) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        
        String datePath = createDateBasedPath();
        String filePath = "board/" + datePath + uniqueFileName;
        
        BoardImage boardImage = new BoardImage();
        boardImage.setBoardId(boardId);
        boardImage.setFileName(uniqueFileName);
        boardImage.setFilePath(filePath);
        boardImage.setFileType(file.getContentType());
        
        boardImageMapper.insertBoardImage(boardImage);
        
        Path fullPath = Paths.get(uploadDir.replace("file:", ""), "board", datePath);
        Files.createDirectories(fullPath);
        
        Path destinationPath = fullPath.resolve(uniqueFileName);
        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        return boardImage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardImage> findByBoardId(int boardId) {
        return boardImageMapper.selectByBoardId(boardId);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardImage findById(int id) {
        return boardImageMapper.selectById(id);
    }

    @Override
    @Transactional
    public void deleteImage(int id) throws IOException {
        BoardImage image = boardImageMapper.selectById(id);
        if (image != null) {
            boardImageMapper.deleteById(id);
            
            Path imagePath = Paths.get(uploadDir.replace("file:", ""), image.getFilePath());
            Files.deleteIfExists(imagePath);
        }
    }

    @Override
    @Transactional
    public void deleteAllByBoardId(int boardId) throws IOException {
        List<BoardImage> images = boardImageMapper.selectByBoardId(boardId);
        for (BoardImage image : images) {
            deleteImage(image.getId());
        }
    }

    @Override
    public byte[] getImageBytes(int id) throws IOException {
        BoardImage image = boardImageMapper.selectById(id);
        if (image == null) return null;

        Path imagePath = Paths.get(uploadDir.replace("file:", ""), image.getFilePath());
        if (!Files.exists(imagePath)) return null;

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
}
