package com.ddam.damda.images.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.BoardImage;
import com.ddam.damda.images.model.GnoticeImage;

public interface GnoticeImageService {
	
    // 초기화
    void init();

    // 이미지 저장
    GnoticeImage saveGnoticeImage(MultipartFile file, int gnoticeId) throws IOException;

    // 게시글의 모든 이미지 조회
    List<GnoticeImage> findByGnoticeId(int gnoticeId);

    // 단일 이미지 삭제
    void deleteImage(int id) throws IOException;

    // 게시글의 모든 이미지 삭제
    void deleteAllByGnoticeId(int gnoticeId) throws IOException;

    // 이미지 바이트 데이터 조회
    byte[] getImageBytes(int id) throws IOException;


}
