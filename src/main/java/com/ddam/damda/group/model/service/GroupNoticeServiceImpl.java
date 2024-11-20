package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.group.model.mapper.GroupNoticeMapper;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.User;
import com.ddam.damda.user.model.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GroupNoticeServiceImpl implements GroupNoticeService {
	
	@Autowired
	private GroupNoticeMapper groupNoticeMapper;
	
	@Autowired
	private GroupMembersService groupMembersService;
	
	@Autowired
	private GroupInfoService groupInfoService;
	
	@Autowired
	private NoticeService noticeService;

	@Override
	@Transactional
	public PageInfo<GroupNotice> selectAllGroupNotice(GNPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<GroupNotice> list = groupNoticeMapper.selectAllGroupNotice(preq);
		PageInfo<GroupNotice> page = new PageInfo<GroupNotice>(list);
		return page;
	}

	@Override
	@Transactional
	public GroupNotice selectGroupNotice(int gnoticeId) {
		return groupNoticeMapper.selectGroupNotice(gnoticeId);
	}

	@Override
	@Transactional
	public int insertGroupNotice(GroupNotice groupNotice) {
		int isS =  groupNoticeMapper.insertGroupNotice(groupNotice);
		if(isS <= 0) return isS;
		
		Notice notice = new Notice();
		int groupId = groupNotice.getGroupId();
		String groupName = groupInfoService.selectGroupInfo(groupId).getGroupName();
		notice.setReferenceId(groupNotice.getGnoticeId());
		notice.setReferenceType("group_notice");
		notice.setContent("\"" + groupName + "\" 그룹에 새 공지사항이 등록되었습니다.");
		List<User> users = groupMembersService.selectAllGroupMembers(groupId);
		for(User user : users) {
			int userId = user.getId();
			notice.setUserId(userId);
			noticeService.insertNotice(notice);
		}
		return isS;
	}

	@Override
	@Transactional
	public int deleteGroupNotice(int gnoticeId) {
		return groupNoticeMapper.deleteGroupNotice(gnoticeId);
	}

	@Override
	@Transactional
	public int updateGroupNotice(GroupNotice groupNotice) {
		return groupNoticeMapper.updateGroupNotice(groupNotice);
	}
	

}
