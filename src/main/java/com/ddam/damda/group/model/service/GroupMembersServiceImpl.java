package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.GroupMembers;
import com.ddam.damda.group.model.mapper.GroupInfoMapper;
import com.ddam.damda.group.model.mapper.GroupMembersMapper;
import com.ddam.damda.user.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GroupMembersServiceImpl implements GroupMembersService {
	
	@Autowired
	private GroupMembersMapper groupMembersMapper;
	
	@Autowired
	private GroupInfoService groupInfoService;

	@Override
	@Transactional
	public List<User> selectAllGroupMembers(int groupId) {
		return groupMembersMapper.selectAllGroupMembers(groupId);
	}

	@Override
	@Transactional
	public int insertGroupMembers(GroupMembers groupMembers) {
		int groupId = groupMembers.getGroupId();
		GroupInfo groupInfo = groupInfoService.selectGroupInfo(groupId);
		int cM = groupInfo.getCurrentMembers(); // 현재 인원
		int mC = groupInfo.getMemberCount(); // 최대 인원
		
		if(cM >= mC) return 0; // 만약 현재 인원이 최대 인원에 도달했다면 로직 실행 불가
		
		groupInfoService.increaseCurrentMembers(groupId);
		return groupMembersMapper.insertGroupMembers(groupMembers);
	}

	@Override
	@Transactional
	public int deleteGroupMembers(GroupMembers groupMembers) {
		int groupId = groupMembers.getGroupId();
		groupInfoService.decreaseCurrentMembers(groupId);
		return groupMembersMapper.deleteGroupMembers(groupMembers);
	}

	@Override
	@Transactional
	public int deleteGroupMembersById(int id) {
		GroupMembers groupMembers = groupMembersMapper.selectGroupMembersById(id);
		int groupId = groupMembers.getGroupId();
		groupInfoService.decreaseCurrentMembers(groupId);
		return groupMembersMapper.deleteGroupMembers(groupMembers);
	}
	
	

}
