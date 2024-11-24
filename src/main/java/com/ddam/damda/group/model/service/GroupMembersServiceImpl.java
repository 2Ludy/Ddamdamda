package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.GroupMembers;
import com.ddam.damda.group.model.mapper.GroupMembersMapper;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.service.NoticeService;

@Service
public class GroupMembersServiceImpl implements GroupMembersService {
	
	@Autowired
	private GroupMembersMapper groupMembersMapper;
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	@Autowired
	private NoticeService noticeService;

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
		
		int isS = groupMembersMapper.insertGroupMembers(groupMembers);
		if(isS <= 0) return isS;
		
		groupInfoService.increaseCurrentMembers(groupId);
		
		if(cM+1 == mC) {
			groupInfoService.updateMateStatus(groupId);
		}
		
		String groupName = groupInfo.getGroupName();
		Notice notice = new Notice();
		notice.setReferenceType("group_member");
		notice.setReferenceId(groupId);
		notice.setContent("\"" + groupName + "\" 그룹에 새 멤버가 추가되었습니다.");
		List<User> users = selectAllGroupMembers(groupId);
		for(User user : users) {
			int userId = user.getId();
			notice.setUserId(userId);
			noticeService.insertNotice(notice);
		}
		return isS;
	}

	@Override
	@Transactional
	public int deleteGroupMembers(GroupMembers groupMembers) {
		int isS = groupMembersMapper.deleteGroupMembers(groupMembers);
		if(isS <= 0) return isS;
		int groupId = groupMembers.getGroupId();
		groupInfoService.decreaseCurrentMembers(groupId);
		return isS;
	}

	@Override
	@Transactional
	public int deleteGroupMembersById(int id) {
		GroupMembers groupMembers = groupMembersMapper.selectGroupMembersById(id);
		int groupId = groupMembers.getGroupId();
		int isS = groupMembersMapper.deleteGroupMembers(groupMembers);
		if(isS <= 0) return isS;
		groupInfoService.decreaseCurrentMembers(groupId);
		return isS;
	}

	@Override
	public String selectAllGroupMembers(GroupMembers groupMembers) {
		return groupMembersMapper.selectGroupMembersCreated(groupMembers);
	}
	
	

}
