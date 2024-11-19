package com.ddam.damda.group.model.service;

import java.util.List;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.github.pagehelper.PageInfo;

public interface GroupInfoService {
	
	PageInfo<GroupInfo> selectAllGroupInfos(GPageRequest preq);
	
	List<GroupInfo> selectUserGroupInfos(int userId);
	
	GroupInfo selectGroupInfo(int groupId);
	
	int insertGroupInfo(GroupInfo groupInfo); // group_name, description, admin_id, group_img, region, exercise_type, member_count
	
	int deleteGroupInfo(int groupId);
	
	int updateGroupInfo(GroupInfo groupInfo); // group_id, group_name, description, group_img, mate_status, region, exercise_type, member_count 사용
	
	int increaseCurrentMembers(int groupId);
	
	int decreaseCurrentMembers(int groupId);

}
