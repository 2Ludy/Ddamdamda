package com.ddam.damda.group.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.common.util.GCPageRequest;
import com.ddam.damda.group.model.GComment;

public interface GCommentMapper {
	
	List<GComment> selectAllGComments(GCPageRequest preq);
	
	GComment selectGComment(int id);
	
	int insertGComment(GComment gComment); // user_id, content, gnotice_id 사용
	
	int deleteGComment(int id);
	
	int updateGComment(GComment gComment); // id, content   사용
	
}