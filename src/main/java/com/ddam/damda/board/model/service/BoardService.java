package com.ddam.damda.board.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.common.util.PageRequest;
import com.github.pagehelper.PageInfo;

public interface BoardService {
	
	PageInfo<Board> selectAllBoards(PageRequest preq);
	
	Board selectBoard(int id);
	
	int insertBoard(Board board, List<MultipartFile> images) throws IOException;
	
	int deleteBoard(int id) throws IOException;
	
	int updateBoard(Board board, List<MultipartFile> newImages, List<Integer> deleteImageIds) throws IOException;
	
	int updateViewCount(int id);
	
	int increaseLikesCount(int id);
	
	int decreaseLikesCount(int id);
	
	int increaseCommentsCount(int id);
	
	int decreaseCommentsCount(int id);

}
