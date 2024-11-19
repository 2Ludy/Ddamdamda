package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.GPageRequest;
import com.ddam.damda.group.model.GroupInfo;
import com.ddam.damda.group.model.mapper.GroupInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GroupInfoServiceImpl implements GroupInfoService {
	
	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Override
	@Transactional
	public PageInfo<GroupInfo> selectAllGroupInfos(GPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<GroupInfo> list = groupInfoMapper.selectAllGroupInfos(preq);
		PageInfo<GroupInfo> page = new PageInfo<GroupInfo>(list);
		return page;
	}

	@Override
	@Transactional
	public GroupInfo selectGroupInfo(int groupId) {
		// 여기는 일단 불러오고 이미지 후에 id로 처리 하면 될듯
		return groupInfoMapper.selectGroupInfo(groupId);
	}

	@Override
	@Transactional
	public int insertGroupInfo(GroupInfo groupInfo) {
		// 이미지 처리 따로 필요 이미지 처리 후 반환되는 id를 group_img에 넣어야 함
		return groupInfoMapper.insertGroupInfo(groupInfo);
	}

	@Override
	@Transactional
	public int deleteGroupInfo(int groupId) {
		return groupInfoMapper.deleteGroupInfo(groupId);
	}

	@Override
	@Transactional
	public int updateGroupInfo(GroupInfo groupInfo) {
		// 여기도 image 처리 따로 하고 넣어야 할듯 insert랑 비슷
		int currentMembers = groupInfo.getCurrentMembers();
		int memberCount = groupInfo.getMemberCount();
		if(memberCount < currentMembers) return 0;
		if(memberCount == currentMembers) groupInfo.setMateStatus("마감");
		return groupInfoMapper.updateGroupInfo(groupInfo);
	}

	@Override
	@Transactional
	public int increaseCurrentMembers(int groupId) {
		return groupInfoMapper.increaseCurrentMembers(groupId);
	}

	@Override
	@Transactional
	public int decreaseCurrentMembers(int groupId) {
		return groupInfoMapper.decreaseCurrentMembers(groupId);
	}

}
