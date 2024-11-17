package com.ddam.damda.images.model.mapper;

import java.util.List;

import com.ddam.damda.images.model.GnoticeImage;

public interface GnoticeImageMapper {
	
	int insertGnoticeImage(GnoticeImage gnoticeImage);
	
	List<GnoticeImage> selectByGnoticeId(int gnoticeId);
	
	GnoticeImage selectById(int id);
	
	int deleteById(int id);
	
	int deleteByGnoticeId(int gnoticeId);

}
