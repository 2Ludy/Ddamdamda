package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.GNPageRequest;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.group.model.mapper.GroupNoticeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GroupNoticeServiceImpl implements GroupNoticeService {
	
	@Autowired
	private GroupNoticeMapper groupNoticeMapper;

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
		return groupNoticeMapper.insertGroupNotice(groupNotice);
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
