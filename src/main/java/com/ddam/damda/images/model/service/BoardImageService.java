package com.ddam.damda.images.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.BoardImage;

public interface BoardImageService {
	
    BoardImage saveBoardImage(MultipartFile file, int boardId) throws IOException;
    List<BoardImage> findByBoardId(int boardId);
    BoardImage findById(int id);
    void deleteImage(int id) throws IOException;
    void deleteAllByBoardId(int boardId) throws IOException;
    byte[] getImageBytes(int id) throws IOException;

}
