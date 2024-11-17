package com.ddam.damda.board.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Board;

public interface BoardMapper {
	
	List<Board> SelectAllBoard();
	
	Board SelectBoard(int id);
	
	int insertBoard(int board);
	
	

}
