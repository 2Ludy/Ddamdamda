package com.ddam.damda.user.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.Likes;
import com.ddam.damda.board.model.service.CommentService;
import com.ddam.damda.board.model.service.LikesService;
import com.ddam.damda.group.model.GroupNotice;
import com.ddam.damda.group.model.service.GroupNoticeService;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Autowired
	private LikesService likesService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private GroupNoticeService groupNoticeService;
	
	@Override
	@Transactional
	public List<Notice> selectAllNotice(int userId) {
		return noticeMapper.selectAllNotice(userId);
	}

	@Override
	@Transactional
	public Notice selectNoticeById(int id) {
		return noticeMapper.selectNoticeById(id);
	}

	@Override
	@Transactional
	public int insertNotice(Notice notice) {
		return noticeMapper.insertNotice(notice);
	}

	@Override
	@Transactional
	public int updateNoticeRead(int id) {
		Notice notice = selectNoticeById(id);
		int isRead = notice.getIsRead();
		if(isRead == 1) return 0;
		return noticeMapper.updateNotice(id);
	}

	@Override
	@Transactional
	public int deleteNotice(int id) {
		return noticeMapper.deleteNotice(id);
	}

	@Override
	@Transactional
	public Likes selectLikesNotice(int referenceId) {
		return likesService.selectLikes(referenceId);
	}

	@Override
	@Transactional
	public Comment selectCommentNotice(int referenceId) {
		return commentService.selectComment(referenceId);
	}

	@Override
	@Transactional
	public GroupNotice selectGroupNotice(int referenceId) {
		return groupNoticeService.selectGroupNotice(referenceId);
	}

}
