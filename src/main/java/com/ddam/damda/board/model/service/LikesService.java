package com.ddam.damda.board.model.service;

import com.ddam.damda.board.model.Likes;

public interface LikesService {
	
	Likes selectLikes(int id);
	
	Likes haveLikes(Likes likes); // user_id, board_id
	
	int insertLikes(Likes likes); // user_id, board_id
	
	int deleteLikes(Likes likes);

}
