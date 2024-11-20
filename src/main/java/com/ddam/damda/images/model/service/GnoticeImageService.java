package com.ddam.damda.images.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.BoardImage;
import com.ddam.damda.images.model.GnoticeImage;

public interface GnoticeImageService {
	
	   void init();
	   GnoticeImage saveGnoticeImage(MultipartFile file, int gnoticeId) throws IOException;
	   List<GnoticeImage> findByGnoticeId(int gnoticeId);
	   GnoticeImage findById(int id);  // 이 메서드가 누락되어 있었네요
	   void deleteImage(int id) throws IOException;
	   void deleteAllByGnoticeId(int gnoticeId) throws IOException;
	   byte[] getImageBytes(int id) throws IOException;

}
