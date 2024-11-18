package com.ddam.damda.board.model.service;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.common.util.PageRequest;
import com.github.pagehelper.PageInfo;

public interface BoardService {
	
	PageInfo<Board> selectAllBoards(PageRequest preq);
	
	Board selectBoard(int id);
	
	int insertBoard(Board board);
	
	int deleteBoard(int id);
	
	int updateBoard(Board board);
	
	int updateViewCount(int id);
	
	int increaseLikesCount(int id);
	
	int decreaseLikesCount(int id);
	
	int increaseCommentsCount(int id);
	
	int decreaseCommentsCount(int id);

}
