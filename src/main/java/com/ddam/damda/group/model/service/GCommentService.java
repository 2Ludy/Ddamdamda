package com.ddam.damda.group.model.service;

import com.ddam.damda.common.util.GCPageRequest;
import com.ddam.damda.group.model.GComment;
import com.github.pagehelper.PageInfo;

public interface GCommentService {
	
	PageInfo<GComment> selectAllGComments(GCPageRequest preq);
	
	GComment selectGComment(int id);
	
	int insertGComment(GComment gComment); // user_id, content, gnotice_id 사용
	
	int deleteGComment(int id);
	
	int updateGComment(GComment gComment); // id, content   사용

}
