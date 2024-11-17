package com.ddam.damda.images.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.images.model.Images;

public interface ImagesService {
    void init() throws IOException;
    int saveProfileImage(MultipartFile file) throws IOException;
    byte[] getImageBytes(int imageId) throws IOException;
    Images findById(int imageId);
}