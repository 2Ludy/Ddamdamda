package com.ddam.damda.group.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.github.pagehelper.PageInfo;

public interface GroupNoticeService {
	
	PageInfo<GroupNotice> selectAllGroupNotice(GNPageRequest preq);
	
	GroupNotice selectGroupNotice(int gnoticeId);
	
	int insertGroupNotice(GroupNotice groupNotice, List<MultipartFile> images) throws IOException; // user_id, category, title, content 사용
	
	int deleteGroupNotice(int gnoticeId) throws IOException;
	
	int updateGroupNotice(GroupNotice groupNotice, List<MultipartFile> newImages, List<Integer> deleteImageIds) throws IOException;
	
	String selectLatestGroupNotice(int groupId);
}
