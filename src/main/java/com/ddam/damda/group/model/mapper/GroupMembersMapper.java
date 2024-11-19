package com.ddam.damda.group.model.mapper;

import java.util.List;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;

public interface GroupMembersMapper {
	
	List<GroupInfo> selectAllGroupInfos(GPageRequest preq);
	
	GroupInfo selectGroupInfo(int groupId);
	
	int insertGroupInfo(GroupInfo groupInfo); // group_name, description, admin_id, group_img, region, exercise_type, member_count
	
	int deleteGroupInfo(int groupId);
	
	int updateGroupInfo(GroupInfo groupInfo); // group_id, group_name, description, group_img, mate_status, region, exercise_type, member_count 사용
	
	int increaseCurrentMembers(int groupId);
	
	int decreaseCurrentMembers(int groupId);
	
}