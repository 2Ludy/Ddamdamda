package com.ddam.damda.group.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.common.util.GCPageRequest;
import com.ddam.damda.group.model.GComment;
import com.ddam.damda.group.model.mapper.GCommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GCommentServiceImpl implements GCommentService {
	
	@Autowired
	private GCommentMapper gCommentMapper;

	@Override
	@Transactional
	public PageInfo<GComment> selectAllGComments(GCPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<GComment> list = gCommentMapper.selectAllGComments(preq);
		PageInfo<GComment> page = new PageInfo<GComment>(list);
		return page;
	}

	@Override
	@Transactional
	public GComment selectGComment(int id) {
		return gCommentMapper.selectGComment(id);
	}

	@Override
	@Transactional
	public int insertGComment(GComment gComment) {
		return gCommentMapper.insertGComment(gComment);
	}

	@Override
	@Transactional
	public int deleteGComment(int id) {
		return gCommentMapper.deleteGComment(id);
	}

	@Override
	@Transactional
	public int updateGComment(GComment gComment) {
		return gCommentMapper.updateGComment(gComment);
	}

}
