package com.ddam.damda.images.model.mapper;

import java.util.List;

import com.ddam.damda.images.model.GnoticeImage;

public interface GnoticeImageMapper {
    void insertGnoticeImage(GnoticeImage gnoticeImage);
    List<GnoticeImage> selectByGnoticeId(int gnoticeId);
    GnoticeImage selectById(int id);
    void deleteById(int id);
    void deleteByGnoticeId(int gnoticeId);
}
