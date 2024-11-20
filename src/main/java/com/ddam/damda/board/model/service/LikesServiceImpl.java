package com.ddam.damda.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ddam.damda.board.model.Board;
import com.ddam.damda.board.model.Likes;
import com.ddam.damda.board.model.mapper.LikesMapper;
import com.ddam.damda.user.model.Notice;
import com.ddam.damda.user.model.service.NoticeService;

@Service
public class LikesServiceImpl implements LikesService {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private LikesMapper likesMapper;
	
	@Autowired
	private NoticeService noticeService;

	@Override
	@Transactional
	public Likes selectLikes(int id) {
		return likesMapper.selectLikes(id);
	}

	@Override
	@Transactional
	public Likes haveLikes(Likes likes) {
		return likesMapper.haveLikes(likes);
	}

	@Override
	@Transactional
	public int insertLikes(Likes likes) {
		int isS = likesMapper.insertLikes(likes);
		if(isS <= 0) return isS;
		int boardId = likes.getBoardId();
		boardService.increaseLikesCount(boardId);
		Board board = boardService.selectBoard(boardId);
		int userId = board.getUserId();
		String title = board.getTitle();
		Notice notice = new Notice();
		notice.setUserId(userId);
		notice.setReferenceId(boardId);
		notice.setReferenceType("like");
		notice.setContent("\"" + title + "\" 글에 좋아요가 추가되었습니다.");
		noticeService.insertNotice(notice);
		return isS;
	}

	@Override
	@Transactional
	public int deleteLikes(int id) {
		int isS = likesMapper.deleteLikes(id);
		if(isS <= 0) return isS;
		Likes likes = selectLikes(id);
		int boardId = likes.getBoardId();
		boardService.decreaseLikesCount(boardId);
		return isS;
	}


}
