package com.ddam.damda.images.model.mapper;

import java.util.List;

import com.ddam.damda.images.model.BoardImage;

public interface BoardImageMapper {
	
	int insertBoardImage(BoardImage boardImage);
	
	List<BoardImage> selectByBoardId(int boardId);
	
	BoardImage selectById(int id);
	
	int deleteById(int id);
	
	int deleteByBoardId(int boardId);

}
