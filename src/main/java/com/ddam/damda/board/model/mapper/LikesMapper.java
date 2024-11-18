package com.ddam.damda.board.model.mapper;

import com.ddam.damda.board.model.Likes;

public interface LikesMapper {
	
	Likes selectLikes(int id);
	
	Likes haveLikes(Likes likes); // user_id, board_id
	
	int insertLikes(Likes likes); // user_id, board_id
	
	int deleteLikes(int id);
	
	
}