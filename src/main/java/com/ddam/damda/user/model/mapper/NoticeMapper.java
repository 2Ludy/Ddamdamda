package com.ddam.damda.user.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.Likes;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.user.model.Notice;

public interface NoticeMapper {
	
	List<Notice> selectAllNotice(int userId);
	
	Notice selectNoticeById(int id);
	
	int insertNotice(Notice notice);
	
	int updateNotice(int id);
	
	int deleteNotice(int id);
	
	Likes selectLikesNotice(int referenceId);
	
	Comment selectCommentNotice(int referenceId);
	
	GroupNotice selectGroupNotice(int referenceId);
	
	
	
}
