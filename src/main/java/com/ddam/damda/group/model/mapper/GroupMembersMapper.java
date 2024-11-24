package com.ddam.damda.group.model.mapper;

import java.util.List;

import com.ddam.damda.group.model.GroupMembers;
import com.ddam.damda.user.model.User;

public interface GroupMembersMapper {
	
	List<User> selectAllGroupMembers(int groupId);
	
	GroupMembers selectGroupMembersById(int id);
	
	int insertGroupMembers(GroupMembers groupMembers);
	
	int deleteGroupMembers(GroupMembers groupMembers);
	
	int deleteGroupMembersById(int id);
	
	String selectGroupMembersCreated(GroupMembers groupMembers);
	
}