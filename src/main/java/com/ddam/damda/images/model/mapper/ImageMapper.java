package com.ddam.damda.images.model.mapper;

import com.ddam.damda.images.model.Image;

public interface ImageMapper {
	
	void save(Image image);
	
	void updateFileInfo(Image image);
	
	Image findById(int id);

}
