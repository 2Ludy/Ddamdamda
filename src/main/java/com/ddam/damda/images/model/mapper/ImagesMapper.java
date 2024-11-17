package com.ddam.damda.images.model.mapper;

import com.ddam.damda.images.model.Images;

public interface ImagesMapper {
	
	void save(Images image);
	
	void updateFileInfo(Images image);
	
	Images findById(int id);

}
