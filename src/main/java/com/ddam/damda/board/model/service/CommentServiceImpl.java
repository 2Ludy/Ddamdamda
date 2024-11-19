package com.ddam.damda.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.Comment;
import com.ddam.damda.board.model.mapper.CommentMapper;
import com.ddam.damda.common.util.CPageRequest;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.service.NoticeService;
import com.ddam.damda.user.model.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public PageInfo<Comment> selectAllComments(CPageRequest preq) {
		PageHelper.startPage(preq.getPageNum(), preq.getPageSize());
		List<Comment> list = commentMapper.selectAllComments(preq);
		for(Comment c : list) {
			int userId = c.getUserId();
			c.setUsername(userService.findUserNameById(userId));
					
		}
		PageInfo<Comment> page = new PageInfo<Comment>(list);
		return page;
	}

	@Override
	@Transactional
	public Comment selectComment(int id) {
		return commentMapper.selectComment(id);
	}

	@Override
	@Transactional
	public int insertComment(Comment comment) {
		int boardId = comment.getBoardId();
		boardService.increaseCommentsCount(boardId);
		Board board = boardService.selectBoard(boardId);
		String title = board.getTitle();
		int userId = board.getUserId();
		Notice notice = new Notice();
		notice.setUserId(userId);
		notice.setReferenceId(boardId);
		notice.setReferenceType("comment");
		notice.setContent("\"" + title + "\" 글에 새로운 댓글이 달렸습니다.");
		noticeService.insertNotice(notice);
		return commentMapper.insertComment(comment);
	}

	@Override
	@Transactional
	public int deleteComment(int id) {
		Comment com = selectComment(id);
		int boardId = com.getBoardId();
		boardService.decreaseCommentsCount(boardId);
		return commentMapper.deleteComment(boardId);
	}

	@Override
	@Transactional
	public int updateComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}


}
