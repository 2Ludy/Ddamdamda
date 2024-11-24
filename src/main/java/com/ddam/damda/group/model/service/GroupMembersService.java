package com.ddam.damda.group.model.service;

import java.util.List;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.GroupMembers;
import com.ddam.damda.user.model.User;
import com.github.pagehelper.PageInfo;

public interface GroupMembersService {
	
	List<User> selectAllGroupMembers(int groupId);
	
	int insertGroupMembers(GroupMembers groupMembers);
	
	int deleteGroupMembers(GroupMembers groupMembers);
	
	int deleteGroupMembersById(int id);

	String selectAllGroupMembers(GroupMembers groupMembers);

}
