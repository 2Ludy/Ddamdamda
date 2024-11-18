package com.ddam.damda.board.model.service;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.common.util.CPageRequest;
import com.github.pagehelper.PageInfo;

public interface CommentService {
	
	PageInfo<Comment> selectAllComments(CPageRequest preq);
	
	Comment selectComment(int id);
	
	int insertComment(Comment comment); // user_id, content, board_id 사용
	
	int deleteComment(int id);
	
	int updateComment(Comment comment); // id, content   사용

}
