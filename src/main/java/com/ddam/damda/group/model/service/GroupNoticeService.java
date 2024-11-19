package com.ddam.damda.group.model.service;

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.github.pagehelper.PageInfo;

public interface GroupNoticeService {
	
	PageInfo<GroupNotice> selectAllGroupNotice(GNPageRequest preq);
	
	GroupNotice selectGroupNotice(int gnoticeId);
	
	int insertGroupNotice(GroupNotice groupNotice); // user_id, category, title, content 사용
	
	int deleteGroupNotice(int gnoticeId);
	
	int updateGroupNotice(GroupNotice groupNotice); // id, title, content 사용

}
